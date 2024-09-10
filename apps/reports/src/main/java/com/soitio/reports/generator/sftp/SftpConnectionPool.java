package com.soitio.reports.generator.sftp;

import com.jcraft.jsch.ChannelSftp;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SftpConnectionPool {

    private static final Logger log = LoggerFactory.getLogger(SftpConnectionPool.class);

    private final KeyedObjectPool<SftpConnectionDetails, ChannelSftp> pooledConnections;

    public SftpConnectionPool(SftpConnectionFactory sftpConnectionFactory) {
        this.pooledConnections = new GenericKeyedObjectPool<>(sftpConnectionFactory);
    }

    public ChannelSftp getChannelForCreds(SftpConnectionDetails sftpConnectionDetails) {
        try {
            ChannelSftp channelSftp = this.pooledConnections.borrowObject(sftpConnectionDetails);
            if (channelSftp != null) {
                return channelSftp;
            }
            this.pooledConnections.addObject(sftpConnectionDetails);
            return this.pooledConnections.borrowObject(sftpConnectionDetails);
        } catch (Exception e) {
            // issue
            log.warn("Could not get SFTP connection, reason: {}", e.getMessage());
        }
        throw new IllegalStateException("Could not get a pooled SFTP connection");
    }

    public void returnToPool(SftpConnectionDetails sftpConnectionDetails, ChannelSftp channelSftp) {
        try {
            this.pooledConnections.returnObject(sftpConnectionDetails, channelSftp);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}

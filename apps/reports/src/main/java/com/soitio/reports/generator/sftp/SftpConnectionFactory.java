package com.soitio.reports.generator.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SftpConnectionFactory extends BaseKeyedPooledObjectFactory<SftpConnectionDetails, ChannelSftp> {

    private static final Logger log = LoggerFactory.getLogger(SftpConnectionFactory.class);

    @Override
    public ChannelSftp create(SftpConnectionDetails sftpConnectionDetails) {
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(sftpConnectionDetails.username(),
                    sftpConnectionDetails.hostname(),
                    sftpConnectionDetails.port());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sftpConnectionDetails.password());
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            return  (ChannelSftp) channel;
        } catch (JSchException e) {
            // ERROR
            log.warn("Could not create SFTP session, reason: {}", e.getMessage());
        }
        throw new IllegalStateException("Could not create SFTP session");
    }

    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject<>(channelSftp);
    }
}

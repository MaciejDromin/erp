package com.soitio.reports.sftp;

import com.jcraft.jsch.ChannelSftp;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

@ApplicationScoped
public class SftpConnectionFactory extends BaseKeyedPooledObjectFactory<SftpConnectionDetails, ChannelSftp> {

    @Override
    public ChannelSftp create(SftpConnectionDetails sftpConnectionDetails) throws Exception {
        return null;
    }

    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject<>(channelSftp);
    }
}

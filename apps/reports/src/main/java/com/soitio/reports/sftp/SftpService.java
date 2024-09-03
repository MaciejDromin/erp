package com.soitio.reports.sftp;

import com.jcraft.jsch.ChannelSftp;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SftpService {

    private final SftpConnectionPool sftpConnectionPool;

    public SftpService(SftpConnectionPool sftpConnectionPool) {
        this.sftpConnectionPool = sftpConnectionPool;
    }

    public void archiveFile(String filePath, SftpConnectionDetails sftpConnectionDetails) {
        ChannelSftp channelSftp = sftpConnectionPool.getChannelForCreds(sftpConnectionDetails);

        // TODO: Here archive file

        sftpConnectionPool.returnToPool(sftpConnectionDetails, channelSftp);
    }

}

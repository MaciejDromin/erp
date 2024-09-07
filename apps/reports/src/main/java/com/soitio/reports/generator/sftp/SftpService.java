package com.soitio.reports.generator.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.FileInputStream;
import java.io.IOException;

@ApplicationScoped
public class SftpService {

    private final SftpConnectionPool sftpConnectionPool;

    public SftpService(SftpConnectionPool sftpConnectionPool) {
        this.sftpConnectionPool = sftpConnectionPool;
    }

    public void archiveFile(String filePath, SftpConnectionDetails sftpConnectionDetails) {
        ChannelSftp channelSftp = sftpConnectionPool.getChannelForCreds(sftpConnectionDetails);

        cd("data/generated", channelSftp);

        try (FileInputStream is = new FileInputStream(filePath)){
            put(is, filePath, channelSftp);
        } catch (IOException e) {
            throw new IllegalStateException("Could not find file to archive");
        }


        sftpConnectionPool.returnToPool(sftpConnectionDetails, channelSftp);
    }

    private void cd(String path, ChannelSftp sftp) {
        goHome(sftp);
        for (String s : path.split("/")) {
            try {
                sftp.cd(s);
            } catch (SftpException e) {
                mkdirAndCd(s, sftp);
            }
        }
    }

    private void mkdirAndCd(String dir, ChannelSftp channelSftp) {
        try {
            channelSftp.mkdir(dir);
            channelSftp.cd(dir);
        } catch (SftpException ex) {
            throw new IllegalStateException("Could not create directory");
        }
    }

    private void goHome(ChannelSftp sftp) {
        try {
            sftp.cd(sftp.getHome());
        } catch (SftpException e) {
            // Issue
        }
    }

    private void put(FileInputStream is, String name, ChannelSftp sftp) {
        try {
            sftp.put(is, name);
        } catch (SftpException e) {
            throw new IllegalStateException("Could not find file to archive");
        }
    }

}

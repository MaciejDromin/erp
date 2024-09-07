package com.soitio.reports.generator.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.FileInputStream;
import java.io.IOException;

@ApplicationScoped
public class SftpService {

    private static final String GENERATED_DIR = "data/generated";
    private static final String DELIMITER = "/";

    private final SftpConnectionPool sftpConnectionPool;

    public SftpService(SftpConnectionPool sftpConnectionPool) {
        this.sftpConnectionPool = sftpConnectionPool;
    }

    public String archiveFile(String filePath, SftpConnectionDetails sftpConnectionDetails) throws Exception {
        ChannelSftp channelSftp = sftpConnectionPool.getChannelForCreds(sftpConnectionDetails);

        cd(GENERATED_DIR, channelSftp);

        String filename = extractFilename(filePath);

        try (FileInputStream is = new FileInputStream(filePath)){
            put(is, filename, channelSftp);
        } catch (IOException e) {
            throw new IllegalStateException("Could not find file to archive");
        }

        sftpConnectionPool.returnToPool(sftpConnectionDetails, channelSftp);
        return GENERATED_DIR + DELIMITER + filename;
    }

    private String extractFilename(String filePath) {
        int lio = filePath.lastIndexOf(DELIMITER);
        if (lio == -1) return filePath;
        return filePath.substring(lio + 1);
    }

    private void cd(String path, ChannelSftp sftp) {
        goHome(sftp);
        for (String s : path.split(DELIMITER)) {
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

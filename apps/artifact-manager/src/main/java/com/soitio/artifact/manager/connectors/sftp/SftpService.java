package com.soitio.artifact.manager.connectors.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.soitio.artifact.manager.ArtifactService;
import com.soitio.artifact.manager.domain.FileDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.io.InputStream;

@ApplicationScoped
public class SftpService implements ArtifactService {

    private static final String ROOT_DIR = "data";
    private static final String DELIMITER = "/";

    private final SftpConnectionPool sftpConnectionPool;
    private final SftpConnectionDetails connectionDetails;

    public SftpService(SftpConnectionPool sftpConnectionPool,
                       @Named("defaultSftp") SftpConnectionDetails sftpConnectionDetails) {
        this.sftpConnectionPool = sftpConnectionPool;
        this.connectionDetails = sftpConnectionDetails;
    }

    @Override
    public String uploadArtifact(String filename, String directory, String orgId, InputStream artifact) {
        return archiveFile(filename, directory, this.connectionDetails, artifact, orgId);
    }

    @Override
    public FileDto getArtifact(String filePath, String orgId) {
        return getFile(filePath, this.connectionDetails, orgId);
    }

    @Override
    public void deleteArtifact(String filePath, String orgId) {
        deleteFile(filePath, this.connectionDetails, orgId);
    }

    private FileDto getFile(String filePath, SftpConnectionDetails connectionDetails, String orgId) {
        ChannelSftp channelSftp = sftpConnectionPool.getChannelForCreds(connectionDetails);

        String fullPath = ROOT_DIR + DELIMITER + orgId + DELIMITER + filePath;

        goHome(channelSftp);

        InputStream artifact;

        try {
            artifact = channelSftp.get(fullPath);
        } catch (Exception e) {
            throw new IllegalStateException("Could not find file to fetch");
        } finally {
            sftpConnectionPool.returnToPool(connectionDetails, channelSftp);
        }
        return new FileDto(artifact, fullPath.substring(fullPath.lastIndexOf("/") + 1));
    }

    private void deleteFile(String filePath, SftpConnectionDetails connectionDetails, String orgId) {
        ChannelSftp channelSftp = sftpConnectionPool.getChannelForCreds(connectionDetails);

        String fullPath = ROOT_DIR + DELIMITER + orgId + DELIMITER + filePath;

        goHome(channelSftp);

        try {
            channelSftp.rm(fullPath);
        } catch (Exception e) {
            throw new IllegalStateException("Could not find file to delete");
        } finally {
            sftpConnectionPool.returnToPool(connectionDetails, channelSftp);
        }
    }

    private String archiveFile(String filename,
                               String directory,
                               SftpConnectionDetails sftpConnectionDetails,
                               InputStream artifact,
                               String orgId) {
        ChannelSftp channelSftp = sftpConnectionPool.getChannelForCreds(sftpConnectionDetails);

        String basePath = ROOT_DIR + DELIMITER + orgId + DELIMITER + directory;
        cd(basePath, channelSftp);

        try {
            channelSftp.put(artifact, filename);
        } catch (Exception e) {
            throw new IllegalStateException("Could not find file to archive");
        } finally {
            sftpConnectionPool.returnToPool(sftpConnectionDetails, channelSftp);
        }

        return generateLocationPath(basePath, filename);
    }

    private String generateLocationPath(String basePath, String filename) {
        String fullPath = basePath + DELIMITER + filename;
        return fullPath.substring(fullPath.indexOf(DELIMITER, fullPath.indexOf(DELIMITER) + 1) + 1);
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

}

package com.soitio.reports.config;

import com.soitio.reports.sftp.SftpConnectionDetails;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SftpConfig {

    @ConfigProperty(name = "sftp.host")
    String host;

    @ConfigProperty(name = "sftp.port")
    Integer port;

    @ConfigProperty(name = "sftp.username")
    String username;

    @ConfigProperty(name = "sftp.password")
    String password;

    @Produces
    @Named("defaultSftp")
    @ApplicationScoped
    public SftpConnectionDetails sftpConnectionDetails() {
        return new SftpConnectionDetails(host, port, username, password);
    }

}

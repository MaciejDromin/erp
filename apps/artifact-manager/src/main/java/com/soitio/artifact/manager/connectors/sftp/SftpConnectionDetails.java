package com.soitio.artifact.manager.connectors.sftp;

public record SftpConnectionDetails(String hostname, int port, String username, String password) {
}

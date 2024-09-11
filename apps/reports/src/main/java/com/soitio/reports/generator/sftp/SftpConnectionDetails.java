package com.soitio.reports.generator.sftp;

public record SftpConnectionDetails(String hostname, int port, String username, String password) {
}

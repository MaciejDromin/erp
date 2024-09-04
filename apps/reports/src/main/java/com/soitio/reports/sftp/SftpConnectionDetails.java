package com.soitio.reports.sftp;

public record SftpConnectionDetails(String hostname, int port, String username, String password) {
}

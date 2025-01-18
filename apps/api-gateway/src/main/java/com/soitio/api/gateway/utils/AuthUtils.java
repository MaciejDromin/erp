package com.soitio.api.gateway.utils;

import jakarta.ws.rs.core.HttpHeaders;

public final class AuthUtils {

    private static final int BEARER_END = 7;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_HEADER = "Refresh-Token";

    private AuthUtils() {
        throw new AssertionError("Utility class");
    }

    public static String extractToken(String token) {
        return token.substring(BEARER_END);
    }

    public static String extractAuthorizationHeader(HttpHeaders headers) {
        return headers.getHeaderString(AUTHORIZATION_HEADER);
    }

    public static String extractRefreshTokenHeader(HttpHeaders headers) {
        return headers.getHeaderString(REFRESH_TOKEN_HEADER);
    }

}

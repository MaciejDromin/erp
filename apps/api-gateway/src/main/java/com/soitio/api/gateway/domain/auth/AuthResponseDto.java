package com.soitio.api.gateway.domain.auth;

public record AuthResponseDto(String authToken, String refreshToken, String orgId, long expiresIn) {
}

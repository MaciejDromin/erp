package com.soitio.api.gateway.domain.auth;

import java.util.List;

public record UserOrgResponseDto(List<OrgDetailsDto> orgs) {
}

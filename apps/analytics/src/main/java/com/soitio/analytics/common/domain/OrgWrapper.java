package com.soitio.analytics.common.domain;

public record OrgWrapper<T>(String orgId, T data) {
}

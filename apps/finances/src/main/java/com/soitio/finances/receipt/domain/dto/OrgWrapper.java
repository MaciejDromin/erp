package com.soitio.finances.receipt.domain.dto;

public record OrgWrapper<T>(String orgId, T data) {
}

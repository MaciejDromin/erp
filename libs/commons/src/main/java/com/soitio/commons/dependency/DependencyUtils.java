package com.soitio.commons.dependency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.model.DependencyCheckResult;

import java.util.Base64;
import java.util.Set;

public final class DependencyUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private DependencyUtils() {
        throw new AssertionError("Utility class");
    }

    public static DependencyCheckResult mergeResults(DependencyCheckResult o, DependencyCheckResult n) {
        if (!o.isFailed() && !n.isFailed()) return n;
        return new DependencyCheckResult(o.getId(), true, o.getReason() + ", " + n.getReason());
    }

    public static Set<String> decodeBase64(String base64Encoded) {
        byte[] bytes = Base64.getDecoder().decode(base64Encoded);
        try {
            return objectMapper.readValue(bytes, new TypeReference<>() {});
        } catch (Exception e) {
            throw new IllegalStateException("Could not map into set");
        }
    }

}

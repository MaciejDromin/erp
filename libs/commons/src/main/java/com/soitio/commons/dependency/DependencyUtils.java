package com.soitio.commons.dependency;

import com.soitio.commons.dependency.model.DependencyCheckResult;

public final class DependencyUtils {

    private DependencyUtils() {
        throw new AssertionError("Utility class");
    }

    public static DependencyCheckResult mergeResults(DependencyCheckResult o, DependencyCheckResult n) {
        if (!o.isFailed() && !n.isFailed()) return n;
        return new DependencyCheckResult(o.getId(), true, o.getReason() + ", " + n.getReason());
    }

}

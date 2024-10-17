package com.soitio.commons.dependency.exception;

import java.io.Serial;

public class DependencyLockException extends Exception {

    @Serial
    private static final long serialVersionUID = -621052022471574951L;

    public DependencyLockException(String message) {
        super(message);
    }


}

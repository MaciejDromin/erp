package com.soitio.artifact.manager.domain;

import java.io.InputStream;

public record FileDto(InputStream artifact, String filename) {
}

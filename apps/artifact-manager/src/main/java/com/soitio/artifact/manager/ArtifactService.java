package com.soitio.artifact.manager;

import com.soitio.artifact.manager.domain.FileDto;
import java.io.InputStream;

public interface ArtifactService {

    String uploadArtifact(String filename, String directory, String orgId, InputStream artifact);

    FileDto getArtifact(String filePath, String orgId);

    void deleteArtifact(String filePath, String orgId);

}

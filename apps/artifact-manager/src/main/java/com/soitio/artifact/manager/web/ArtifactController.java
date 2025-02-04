package com.soitio.artifact.manager.web;

import com.soitio.artifact.manager.ArtifactService;
import com.soitio.artifact.manager.domain.FileDto;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.io.InputStream;

@Path("/")
public class ArtifactController {

    private final ArtifactService artifactService;

    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    @POST
    public String uploadArtifact(@HeaderParam("X-OrgId") String orgId,
                                 @QueryParam("filename") String filename,
                                 @QueryParam("directory") String directory,
                                 InputStream is) {
        return artifactService.uploadArtifact(filename, directory, orgId, is);
    }

    @GET
    public RestResponse<InputStream> getArtifact(@HeaderParam("X-OrgId") String orgId,
                                                 @QueryParam("filePath") String filePath) {
        FileDto ret = artifactService.getArtifact(filePath, orgId);
        return RestResponse.ResponseBuilder.ok(ret.artifact(), MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=" + ret.filename())
                .build();
    }

    @DELETE
    public void deleteArtifact(@HeaderParam("X-OrgId") String orgId,
                               @QueryParam("filePath") String filePath) {
        artifactService.deleteArtifact(filePath, orgId);
    }

}

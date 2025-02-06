package com.soitio.reports.generator.client;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.io.InputStream;

@Path("/")
@RegisterRestClient(configKey = "artifact-api")
public interface ArtifactClient {

    @POST
    String uploadArtifact(@HeaderParam("X-OrgId") String orgId,
                          @QueryParam("filename") String filename,
                          @QueryParam("directory") String directory,
                          InputStream is);

}

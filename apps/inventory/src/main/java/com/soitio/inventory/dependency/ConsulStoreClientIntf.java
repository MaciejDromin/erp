package com.soitio.inventory.dependency;

import com.soitio.commons.dependency.client.ConsulStoreClient;
import com.soitio.commons.dependency.model.KeyValue;
import com.soitio.commons.dependency.model.Session;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;
import java.util.Set;

@Path("")
@RegisterRestClient(configKey = "consul-api")
public interface ConsulStoreClientIntf extends ConsulStoreClient {

    @Override
    @PUT
    @Path("/v1/kv/{key}")
    boolean updateKey(Set<String> set,
                      @PathParam("key") String s,
                      @QueryParam("acquire") String s1);

    @Override
    @PUT
    @Path("/v1/kv/{key}")
    void relaeseKey(Set<String> set,
                    @PathParam("key") String s,
                    @QueryParam("release") String s1);

    @Override
    @GET
    @Path("/v1/kv/{key}")
    List<KeyValue> getCurrentValue(@PathParam("key") String s);

    @Override
    @PUT
    @Path("/v1/session/create")
    Session createSession();
}

package pl.mlisowski.planner.material.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.planner.common.dto.PageDto;
import pl.mlisowski.planner.material.application.MaterialRepository;
import pl.mlisowski.planner.material.domain.dto.MaterialCreationDto;
import pl.mlisowski.planner.material.domain.dto.MaterialDto;

@Path("/planner/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialRepository materialRepository;

    @GET
    public PageDto<MaterialDto> getAllMaterials(@Context UriInfo uriInfo) {
        return materialRepository.findAll(uriInfo);
    }

    @POST
    public void addMaterial(MaterialCreationDto creation) {
        materialRepository.create(creation);
    }

}

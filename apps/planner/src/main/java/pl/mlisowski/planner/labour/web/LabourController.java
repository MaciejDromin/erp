package pl.mlisowski.planner.labour.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.planner.common.dto.PageDto;
import pl.mlisowski.planner.labour.application.LabourRepository;
import pl.mlisowski.planner.labour.domain.dto.LabourCreationDto;
import pl.mlisowski.planner.labour.domain.dto.LabourDto;

@Path("/planner/labours")
@RequiredArgsConstructor
public class LabourController {

    private final LabourRepository labourRepository;

    @GET
    public PageDto<LabourDto> getAllLabours(@Context UriInfo uriInfo) {
        return labourRepository.findAll(uriInfo);
    }

    @POST
    public void addLabour(LabourCreationDto creation) {
        labourRepository.create(creation);
    }

}

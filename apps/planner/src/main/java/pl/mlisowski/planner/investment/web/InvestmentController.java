package pl.mlisowski.planner.investment.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import pl.mlisowski.planner.common.dto.PageDto;
import pl.mlisowski.planner.investment.application.InvestmentRepository;
import pl.mlisowski.planner.investment.domain.dto.InvestmentCreationDto;
import pl.mlisowski.planner.investment.domain.dto.InvestmentShortDto;

@Path("/planner/investments")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentRepository investmentRepository;

    @GET
    public PageDto<InvestmentShortDto> getAllInvestments(@Context UriInfo uriInfo) {
        return investmentRepository.findAll(uriInfo);
    }

    @POST
    public void addInvestment(InvestmentCreationDto creation) {
        investmentRepository.create(creation);
    }

}

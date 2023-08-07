package pl.mlisowski.planner.investment.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import pl.mlisowski.planner.common.dto.AmountDto;
import pl.mlisowski.planner.common.dto.PageDto;
import pl.mlisowski.planner.investment.domain.Investment;
import pl.mlisowski.planner.investment.domain.InvestmentPhase;
import pl.mlisowski.planner.investment.domain.dto.InvestmentCreationDto;
import pl.mlisowski.planner.investment.domain.dto.InvestmentDto;
import pl.mlisowski.planner.investment.domain.dto.InvestmentPhaseCreationDto;
import pl.mlisowski.planner.investment.domain.dto.InvestmentPhaseDto;
import pl.mlisowski.planner.investment.domain.dto.InvestmentShortDto;
import pl.mlisowski.planner.investment.domain.projection.InvestmentShortProjection;
import pl.mlisowski.planner.labour.application.LabourRepository;
import pl.mlisowski.planner.labour.domain.LabourWithQuantity;
import pl.mlisowski.planner.labour.domain.dto.LabourWithQuantityDto;
import pl.mlisowski.planner.material.application.MaterialRepository;
import pl.mlisowski.planner.material.domain.MaterialWithQuantity;
import pl.mlisowski.planner.material.domain.dto.MaterialWithQuantityDto;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class InvestmentRepository implements PanacheMongoRepository<Investment> {

    private final MaterialRepository materialRepository;
    private final LabourRepository labourRepository;
    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public PageDto<InvestmentShortDto> findAll(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var investments = findAll();
        var investmentsList = investments.page(pageNum, DEFAULT_PAGE_SIZE)
                .project(InvestmentShortProjection.class)
                .list();
        return PageDto.of(investmentsList.stream()
                .map(this::convert)
                .toList(), investments.pageCount());
    }

    public InvestmentDto convert(Investment investment) {
        return InvestmentDto.builder()
                .id(investment.getId().toString())
                .name(investment.getName())
                .totalCost(AmountDto.builder()
                        .value(investment.getTotalCostMoney().getAmount())
                        .currencyCode(investment.getCurrency())
                        .build())
                .totalEstimatedTime(investment.getTotalEstimatedTime())
                .investmentPhases(investment.getInvestmentPhases().stream()
                        .map(this::convertPhase)
                        .toList())
                .build();
    }

    private InvestmentShortDto convert(InvestmentShortProjection proj) {
        return InvestmentShortDto.builder()
                .id(proj._id().toString())
                .name(proj.name())
                .totalEstimatedTime(proj.totalEstimatedTime())
                .totalCost(AmountDto.builder()
                        .value(proj.totalCost())
                        .currencyCode(proj.currency())
                        .build())
                .build();
    }

    private InvestmentPhaseDto convertPhase(InvestmentPhase phase) {
        return InvestmentPhaseDto.builder()
                .id(phase.getId().toString())
                .name(phase.getName())
                .subPhases(phase.getSubPhases().stream()
                        .map(this::convertPhase)
                        .toList())
                .materials(phase.getMaterials().stream()
                        .map(material -> MaterialWithQuantityDto.builder()
                                .material(materialRepository.convert(materialRepository.getById(material.getMaterial())))
                                .quantity(material.getRequiredQuantity())
                                .build())
                        .toList())
                .labours(phase.getLabour().stream()
                        .map(labour -> LabourWithQuantityDto.builder()
                                .labour(labourRepository.convert(labourRepository.getById(labour.getLabour())))
                                .requiredQuantity(labour.getRequiredQuantity())
                                .build())
                        .toList())
                .phaseTime(phase.getPhaseTime())
                .totalCost(AmountDto.builder()
                        .value(phase.getTotalCostMoney().getAmount())
                        .currencyCode(phase.getCurrency())
                        .build())
                .build();
    }

    public void create(InvestmentCreationDto creation) {
        persist(convert(creation));
    }

    private Investment convert(InvestmentCreationDto creation) {
        return Investment.builder()
                .name(creation.getName())
                .currency(creation.getCurrency())
                .investmentPhases(creation.getInvestmentPhases().stream()
                        .map(this::createInvestmentPhase)
                        .toList())
                .build();
    }

    private InvestmentPhase createInvestmentPhase(InvestmentPhaseCreationDto creation) {
        return InvestmentPhase.builder()
                .name(creation.getName())
                .currency(creation.getCurrency())
                .subPhases(creation.getSubPhases().stream()
                        .map(this::createInvestmentPhase)
                        .toList())
                .materials(creation.getMaterials().stream()
                        .map(material -> MaterialWithQuantity.builder()
                                .material(new ObjectId(material.getMaterialId()))
                                .requiredQuantity(material.getRequiredQuantity())
                                .build())
                        .toList())
                .labour(creation.getLabours().stream()
                        .map(labour -> LabourWithQuantity.builder()
                                .labour(new ObjectId(labour.getLabourId()))
                                .requiredQuantity(labour.getRequiredQuantity())
                                .build())
                        .toList())
                .build();
    }

}

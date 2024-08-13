package com.soitio.planner.investment.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import com.soitio.planner.common.dto.AmountDto;
import com.soitio.planner.common.dto.PageDto;
import com.soitio.planner.investment.domain.Investment;
import com.soitio.planner.investment.domain.InvestmentPhase;
import com.soitio.planner.investment.domain.dto.InvestmentCreationDto;
import com.soitio.planner.investment.domain.dto.InvestmentDto;
import com.soitio.planner.investment.domain.dto.InvestmentPhaseCreationDto;
import com.soitio.planner.investment.domain.dto.InvestmentPhaseDto;
import com.soitio.planner.investment.domain.dto.InvestmentShortDto;
import com.soitio.planner.investment.domain.projection.InvestmentShortProjection;
import com.soitio.planner.labour.application.LabourRepository;
import com.soitio.planner.labour.domain.LabourWithQuantity;
import com.soitio.planner.labour.domain.dto.LabourWithQuantityDto;
import com.soitio.planner.material.application.MaterialRepository;
import com.soitio.planner.material.domain.MaterialWithQuantity;
import com.soitio.planner.material.domain.dto.MaterialWithQuantityDto;

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

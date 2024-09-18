package com.soitio.planner.labour.application;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import com.soitio.planner.common.dto.AmountDto;
import com.soitio.commons.models.dto.PageDto;
import com.soitio.planner.labour.domain.Labour;
import com.soitio.planner.labour.domain.dto.LabourCreationDto;
import com.soitio.planner.labour.domain.dto.LabourDto;
import java.time.LocalDateTime;

@ApplicationScoped
public class LabourRepository implements PanacheMongoRepository<Labour> {

    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public PageDto<LabourDto> findAll(UriInfo uriInfo) {
        var params = uriInfo.getQueryParameters();
        var requestedPage = params.getFirst("page");
        var pageNum = requestedPage == null ? 1 : Integer.parseInt(requestedPage);
        var labour = findAll();
        var labourList = labour.page(pageNum, DEFAULT_PAGE_SIZE).list();
        return PageDto.of(labourList.stream()
                .map(this::convert)
                .toList(), labour.pageCount());
    }

    public LabourDto convert(Labour labour) {
        return LabourDto.builder()
                .id(labour.getId().toString())
                .name(labour.getName())
                .rateAmount(AmountDto.builder()
                        .value(labour.getRateAmountMoney().getAmount())
                        .currencyCode(labour.getCurrency())
                        .build())
                .updatedTime(labour.getUpdatedTime())
                .contractorName(labour.getContractorName())
                .contractorContact(labour.getContractorContact())
                .unit(labour.getUnit())
                .build();
    }

    public void create(LabourCreationDto creation) {
        persist(convert(creation));
    }

    private Labour convert(LabourCreationDto creation) {
        var time = LocalDateTime.now();
        return Labour.builder()
                .name(creation.getName())
                .rateAmount(creation.getRateAmount().getValue())
                .currency(creation.getRateAmount().getCurrencyCode())
                .contractorName(creation.getContractorName())
                .contractorContact(creation.getContractorContact())
                .updatedTime(time)
                .unit(creation.getUnit())
                .build();
    }

    public Labour getById(ObjectId labourId) {
        return findById(labourId);
    }
}

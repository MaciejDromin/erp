package com.soitio.finances.objectvalues.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soitio.commons.dependency.DependencyCheckRequester;
import com.soitio.commons.dependency.DependencyCheckService;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckResult;
import com.soitio.commons.models.commons.MergePatch;
import com.soitio.commons.models.dto.finances.AmountDto;
import com.soitio.commons.models.dto.finances.ObjectType;
import com.soitio.commons.models.dto.finances.ObjectValueDto;
import com.soitio.commons.models.dto.finances.TopItemByCategoryDto;
import com.soitio.finances.common.AbstractDependencyCheckService;
import com.soitio.finances.currency.application.CurrencyService;
import com.soitio.finances.objectvalues.application.port.ObjectValueRepository;
import com.soitio.finances.objectvalues.domain.ObjectValue;
import com.soitio.finances.objectvalues.domain.dto.ObjectValueCreationDto;
import com.soitio.finances.objectvalues.domain.dto.TotalObjectsValueDto;
import com.soitio.finances.objectvalues.domain.proj.ObjectIdProj;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ObjectValueService extends AbstractDependencyCheckService<ObjectValue> implements DependencyCheckService {

    private static final String SERVICE_NAME = "ObjectValue";

    private final ObjectValueRepository objectValueRepository;
    private final CurrencyService currencyService;

    public ObjectValueService(ObjectMapper mapper,
                              DependencyCheckRequester dependencyCheckRequester,
                              ObjectValueRepository objectValueRepository,
                              CurrencyService currencyService) {
        super(mapper, dependencyCheckRequester);
        this.objectValueRepository = objectValueRepository;
        this.currencyService = currencyService;
    }

    public void create(ObjectValueCreationDto creation) {
        objectValueRepository.save(createObject(creation));
    }

    private ObjectValue createObject(ObjectValueCreationDto creation) {
        return ObjectValue.builder()
                .objectId(creation.getObjectId())
                .amount(new BigDecimal(creation.getAmount()))
                .currency(creation.getCurrencyCode())
                .objectType(creation.getObjectType())
                .build();
    }

    public Page<ObjectValueDto> getPage(Pageable pageable, ObjectType objectType, Set<String> objectIds) {
        return Optional.ofNullable(objectIds)
                .map(oi -> objectValueRepository.findAllPageableByObjectTypeAndObjectIdIn(objectType, oi, pageable))
                .orElseGet(() -> objectValueRepository.findAllPageableByObjectType(objectType, pageable))
                .map(this::from);
    }

    private ObjectValueDto from(ObjectValue objectValue) {
        var amount = objectValue.getAmount();
        return ObjectValueDto.builder()
                .id(objectValue.getId())
                .objectId(objectValue.getObjectId())
                .amount(AmountDto.of(amount.getAmount(), amount.getCurrencyUnit().getCode()))
                .objectType(objectValue.getObjectType())
                .build();
    }

    public TotalObjectsValueDto totalValue(Map<String, Integer> quantityByObjectMap, ObjectType objectType) {
        List<ObjectValue> values = objectValueRepository.findAllByObjectType(objectType);
        List<String> currencies = values.stream()
                .map(ObjectValue::getCurrency)
                .filter(curr -> !curr.equalsIgnoreCase("PLN"))
                .toList();
        Map<String, BigDecimal> ratePerCurrencyCodeMap = currencyService.getRatesForCurrencies(currencies);
        Money totalAmount = Money.of(CurrencyUnit.of("PLN"), BigDecimal.ZERO);
        int objectsCount = 0;
        for (ObjectValue obj : values) {
            var curr = obj.getCurrency();
            var objectQuantity = quantityByObjectMap.get(obj.getObjectId());
            var baseAmountToAdd = obj.getAmount().getAmount()
                    .multiply(BigDecimal.valueOf(objectQuantity));
            if (curr.equalsIgnoreCase("PLN")) {
                totalAmount = totalAmount.plus(Money.of(CurrencyUnit.of(curr), baseAmountToAdd, RoundingMode.DOWN));
            } else {
                totalAmount = totalAmount.plus(Money.of(CurrencyUnit.of("PLN"), baseAmountToAdd
                        .multiply(ratePerCurrencyCodeMap.get(curr)), RoundingMode.DOWN));
            }
            objectsCount += objectQuantity;
        }
        return TotalObjectsValueDto.builder()
                .totalAmount(AmountDto.of(totalAmount.getAmount(), totalAmount.getCurrencyUnit().getCode()))
                .objectsCount(objectsCount)
                .build();
    }

    public List<String> allObjectIds(ObjectType objectType) {
        return objectValueRepository.findAllProjectedByObjectType(objectType).stream()
                .map(ObjectIdProj::getObjectId)
                .toList();
    }

    public TopItemByCategoryDto findTopByObjectIdsIn(Set<String> value) {
        ObjectValue ov = objectValueRepository.findFirstByObjectIdInOrderByAmountDesc(value);
        var amount = ov.getAmount();
        return TopItemByCategoryDto.builder()
                .amount(AmountDto.of(amount.getAmount(), amount.getCurrencyUnit().getCode()))
                .objectId(ov.getObjectId())
                .build();
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public Set<DependencyCheckResult> checkForEdit(Set<DependencyCheckContext> set) {
        return Set.of();
    }

    @Override
    public Set<DependencyCheckResult> checkForDelete(Set<DependencyCheckContext> set) {
        return objectValueRepository.findAllByObjectIdIn(set.stream()
                        .map(DependencyCheckContext::id)
                        .collect(Collectors.toSet())).stream()
                .map(ObjectValue::getObjectId)
                .map(id -> new DependencyCheckResult(id, true, "Object with id '%s' is in use".formatted(id)))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteByIds(Set<String> collect) {
        objectValueRepository.deleteAllById(collect);
    }

    @Override
    protected ObjectValue findById(String id) {
        return objectValueRepository.getReferenceById(id);
    }

    @Override
    protected ObjectValue mapToEntity(MergePatch object) {
        var fields = object.getObjectValue();
        var amount = fields.get("amount").getObjectValue();
        BigDecimal value;
        try {
            value = new BigDecimal(amount.get("value").getStrValue());
        } catch (Exception e) {
            throw new IllegalStateException("Incorrect value " + amount.get("value").getStrValue());
        }
        return ObjectValue.builder()
                .id(fields.get("id").getStrValue())
                .amount(value)
                .currency(amount.get("currencyCode").getStrValue())
                .objectId(fields.get("objectId").getStrValue())
                .objectType(ObjectType.valueOf(fields.get("objectType").getStrValue()))
                .build();
    }

    @Override
    protected void updateEntity(ObjectValue entity) {
        objectValueRepository.save(entity);
    }

    @Override
    protected Object mapToDto(ObjectValue entity) {
        return from(entity);
    }

    public ObjectValueDto getObjectValue(String id) {
        return from(findById(id));
    }
}

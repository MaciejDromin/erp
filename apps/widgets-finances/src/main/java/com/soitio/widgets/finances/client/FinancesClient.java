package com.soitio.widgets.finances.client;

import com.soitio.commons.models.dto.PageDto;
import com.soitio.commons.models.dto.finances.ObjectValueDto;
import com.soitio.commons.models.dto.finances.PlannedExpensesDto;
import com.soitio.commons.models.dto.finances.TopItemByCategoryDto;
import com.soitio.widgets.finances.domain.MoneyOperationBalanceDto;
import com.soitio.widgets.finances.domain.ObjectType;
import com.soitio.widgets.finances.domain.TotalObjectsValueDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/")
@RegisterRestClient
public interface FinancesClient {

    @GET
    @Path("/object-value/object-ids")
    List<String> allObjectIds(@QueryParam("objectType") ObjectType objectType);

    @POST
    @Path("/object-value/total-value")
    TotalObjectsValueDto totalValue(Map<String, Integer> quantityByObjectMap,
                                    @QueryParam("objectType") ObjectType objectType);

    @GET
    @Path("/money-operation/balance")
    List<MoneyOperationBalanceDto> getOperationsForBalance(@QueryParam("balanceYear") int balanceYear,
                                                           @QueryParam("balanceMonth")Month balanceMonth);

    @POST
    @Path("/object-value/top")
    TopItemByCategoryDto findTopByObjectIdsIn(Set<String> value);

    @GET
    @Path("/object-value")
    PageDto<ObjectValueDto> getObjectValues(@QueryParam("size") int size,
                                            @QueryParam("objectType") ObjectType objectType,
                                            @QueryParam("objectIds") Set<String> objectIds);

    @GET
    @Path("/planned-expenses")
    PageDto<PlannedExpensesDto> getPlannedExpenses(@RestQuery Map<String, String> params);

}

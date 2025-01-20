package com.soitio.widgets.finances.application;

import com.soitio.widgets.finances.client.FiltersDataClient;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Month;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class FiltersDataService {

    private final FiltersDataClient filtersDataClient;

    public FiltersDataService(@RestClient FiltersDataClient filtersDataClient) {
        this.filtersDataClient = filtersDataClient;
    }

    public List<Integer> getBalanceYears(String orgId) {
        return filtersDataClient.getDistinctYears(orgId);
    }

    public List<Month> getBalanceMonths(int year, String orgId) {
        return filtersDataClient.getDistinctMonths(orgId, year);
    }

}

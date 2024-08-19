package com.soitio.widgets.finances.application;

import com.soitio.widgets.finances.client.FiltersDataClient;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Month;
import java.util.List;

@ApplicationScoped
public class FiltersDataService {

    private final FiltersDataClient filtersDataClient;

    public FiltersDataService(@RestClient FiltersDataClient filtersDataClient) {
        this.filtersDataClient = filtersDataClient;
    }

    public List<Integer> getBalanceYears() {
        return filtersDataClient.getDistinctYears();
    }

    public List<Month> getBalanceMonths(int year) {
        return filtersDataClient.getDistinctMonths(year);
    }

}

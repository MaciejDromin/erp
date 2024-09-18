package com.soitio.commons.utils;

import com.soitio.commons.models.dto.PageDto;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public final class PageableDataFetcher {

    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_SIZE = 1;
    private static final int SIZE = 50;

    private PageableDataFetcher() {
        throw new AssertionError("Utility class");
    }

    public static <T> List<T> fetchData(BiFunction<Integer, Integer, PageDto<T>> fetchFunction) {
        int totalItems = fetchFunction.apply(INITIAL_PAGE, INITIAL_SIZE).getTotalPages();
        return IntStream.range(INITIAL_PAGE, calculateNumOfPages(totalItems) + 1).parallel()
                .mapToObj(i -> fetchFunction.apply(i, SIZE))
                .map(PageDto::getContent)
                .flatMap(Collection::stream)
                .toList();
    }

    private static int calculateNumOfPages(int totalItems) {
        return (int) Math.ceil((double) totalItems / (double) SIZE);
    }

}

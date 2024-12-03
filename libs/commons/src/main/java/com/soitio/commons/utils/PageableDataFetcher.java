package com.soitio.commons.utils;

import com.soitio.commons.models.dto.PageDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public final class PageableDataFetcher {

    private static final Map<String, String> EMPTY_PARAMS = Map.of();
    private static final int INITIAL_PAGE = 0;
    private static final int SIZE = 50;
    private static final String INITIAL_PAGE_STR = "0";
    private static final String INITIAL_SIZE_STR = "1";
    private static final String SIZE_STR = "50";

    private PageableDataFetcher() {
        throw new AssertionError("Utility class");
    }

    public static <T> List<T> fetchData(Function<Map<String, String>, PageDto<T>> fetchFunction,
                                        Map<String, String> params) {
        int totalItems = fetchFunction.apply(createInitialParams(params)).getTotalPages();
        return IntStream.range(INITIAL_PAGE, calculateNumOfPages(totalItems) + 1).parallel()
                .mapToObj(i -> fetchFunction.apply(createCopyWithNewPageNo(params, i)))
                .map(PageDto::getContent)
                .flatMap(Collection::stream)
                .toList();
    }

    public static <T> List<T> fetchDataWithNoParams(Function<Map<String, String>, PageDto<T>> fetchFunction) {
        int totalItems = fetchFunction.apply(createInitialParams(EMPTY_PARAMS)).getTotalPages();
        return IntStream.range(INITIAL_PAGE, calculateNumOfPages(totalItems) + 1).parallel()
                .mapToObj(i -> fetchFunction.apply(createCopyWithNewPageNo(EMPTY_PARAMS, i)))
                .map(PageDto::getContent)
                .flatMap(Collection::stream)
                .toList();
    }

    private static int calculateNumOfPages(int totalItems) {
        return (int) Math.ceil((double) totalItems / (double) SIZE);
    }

    private static Map<String, String> createInitialParams(Map<String, String> params) {
        Map<String, String> ret = new HashMap<>(params);
        ret.put("page", INITIAL_PAGE_STR);
        ret.put("size", INITIAL_SIZE_STR);
        return ret;
    }

    private static Map<String, String> createCopyWithNewPageNo(Map<String, String> params, int pageNum) {
        Map<String, String> ret = new HashMap<>(params);
        ret.put("page", String.valueOf(pageNum));
        ret.put("size", SIZE_STR);
        return ret;
    }

}

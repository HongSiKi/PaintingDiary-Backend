package com.paintingdiary.backend.model.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PageDTO<T>(
        List<? extends T> result,
        int page,
        int size,
        boolean isFinish
) {
    public static <T, U> PageDTO<T> of(Page<U> queryResult, int page, int size, Function<? super U, ? extends T> converter) {
        List<? extends T> content = queryResult.map(converter).getContent();

        return new PageDTO<>(content, page, size, queryResult.hasNext());
    }

    public static <T> PageDTO<T> of(Page<T> queryResult, int page, int size) {
        return new PageDTO<>(queryResult.getContent(), page, size, queryResult.hasNext());
    }
}

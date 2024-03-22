package com.miniware.blog.api.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PagingDto {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 2000;

    private final int page;
    private final int size;

    @Builder
    public PagingDto(Integer page, Integer size) {
        this.page = (page == null ? DEFAULT_PAGE : Math.max(1, page)) - 1;
        this.size = size == null ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
    }
}

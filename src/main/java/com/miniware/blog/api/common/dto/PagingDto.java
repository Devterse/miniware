package com.miniware.blog.api.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Getter
public class PagingDto {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 2000;

    private final int page;
    private final int size;

    @Builder
    public PagingDto(Integer page, Integer size) {
        this.page = (page == null || page < 1) ? DEFAULT_PAGE : page;
        this.size = (size == null || size < 1) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
    }

    public Pageable toPageable() {
        return PageRequest.of(page - 1, size);
    }
}

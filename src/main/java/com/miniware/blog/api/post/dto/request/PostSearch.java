package com.miniware.blog.api.post.dto.request;

import com.miniware.blog.api.post.constant.PostSearchType;
import com.miniware.blog.api.post.constant.PostSortType;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class PostSearch  {

    private final PostSearchType searchType;
    private final String keyValue;
    private final PostSortType sortType;

    @Builder
    public PostSearch(PostSearchType searchType, String keyValue, PostSortType sortType) {
        this.searchType = searchType;
        this.keyValue = keyValue;
        this.sortType = Optional.ofNullable(sortType).orElse(PostSortType.RECENT);
    }
}

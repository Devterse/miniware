package com.miniware.blog.api.post.constant;

import com.miniware.blog.api.common.mapper.SearchType;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum PostSearchType implements SearchType {

    T("제목", "title"),
    C("내용", "content");

    private final String desc;
    private final String field;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String getField() {
        return this.field;
    }


}

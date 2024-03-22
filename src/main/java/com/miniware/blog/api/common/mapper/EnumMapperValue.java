package com.miniware.blog.api.common.mapper;

import lombok.Getter;

@Getter
public class EnumMapperValue {

    private String code;
    private String title;

    public EnumMapperValue(EnumMapperType enumMapperType) {

        this.code = enumMapperType.getCode();
        this.title = enumMapperType.getTitle();
    }
}

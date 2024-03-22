package com.miniware.blog.api.post.constant;

import com.miniware.blog.api.common.mapper.EnumMapperType;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import java.util.function.Function;
import static com.miniware.blog.api.post.entity.QPost.post;

@Getter
@RequiredArgsConstructor
public enum PostSearchType implements EnumMapperType {

    TITLE("제목", post.title::contains),
    CONTENT("내용", post.content::contains);

    private final String desc;
    private final Function<String, BooleanExpression> expression;

    public BooleanExpression getEq(String searchValue) {
        return StringUtils.hasText(searchValue) ? expression.apply(searchValue) : null;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return desc;
    }
}

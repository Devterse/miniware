package com.miniware.blog.api.post.constant;

import com.miniware.blog.api.common.mapper.SearchType;
import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static com.miniware.blog.api.post.entity.QPost.post;

@RequiredArgsConstructor
public enum PostSortType implements SearchType {

    RECENT("최근순", "createAt"),
    GREAT("좋아요순","likeCnt"),
    COMMENT("댓글순","commentCnt"),
    HITS("조회순","viewCnt");

    private final String desc;
    private final String field;

    @Override
    public String getName() {
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

package com.miniware.blog.api.post.constant;

import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static com.miniware.blog.api.post.entity.QPost.post;

@Getter
@RequiredArgsConstructor
public enum PostSortType  {

    RECENT("최근순", post.createAt.desc()),
    GREAT("좋아요순",null),
    COMMENT("댓글순",null),
    HITS("조회순",null);

    private final String desc;
    private final OrderSpecifier<?> orderSpecifier;

}

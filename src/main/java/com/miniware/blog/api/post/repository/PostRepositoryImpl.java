package com.miniware.blog.api.post.repository;

import com.miniware.blog.api.post.dto.request.PostSearch;
import com.miniware.blog.api.post.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import java.util.List;
import static com.miniware.blog.api.post.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> getList(PostSearch searchDto, Pageable pageable) {
        List<Post> content = jpaQueryFactory
                .selectFrom(post)
                .where(
                        searchEq(searchDto)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(searchDto.getSortType().getOrderSpecifier())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(post.count())
                .from(post);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression searchEq(PostSearch search) {
        return search.getSearchType() != null ? search.getSearchType().getEq(search.getKeyValue()) : null;
    }

}

package com.miniware.blog.api.post.repository;

import com.miniware.blog.api.post.dto.request.PostSearch;
import com.miniware.blog.api.post.entity.Post;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import java.util.List;
import java.util.Optional;

import static com.miniware.blog.api.board.entity.QBoard.board;
import static com.miniware.blog.api.post.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Post> findPostById(Long id) {
        Post result = jpaQueryFactory.selectFrom(post)
                .join(post.board, board).fetchJoin()
                .where(post.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Page<Post> getList(PostSearch searchDto, Pageable pageable) {
        List<Post> content = jpaQueryFactory
                .selectFrom(post)
                .where(
                    searchCondition(searchDto)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(dynamicOrder(post, searchDto.getSortType().getField()))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(post.count())
                .from(post);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression searchCondition(PostSearch postSearch) {
        return Optional.ofNullable(postSearch.getSearchType())
                .map(searchType -> Expressions.stringPath(post, searchType.getField()).containsIgnoreCase(postSearch.getKeyValue()))
                .orElse(null);
    }

    private OrderSpecifier<?> dynamicOrder(PostSearch postSearch) {
        StringPath fieldPath = Expressions.stringPath(post, postSearch.getSortType().getField());
        return new OrderSpecifier<>(Order.DESC, fieldPath);
    }
    public static <T> OrderSpecifier<?> dynamicOrder(EntityPathBase<T> entityPath, String fieldName) {
        StringPath fieldPath = Expressions.stringPath(entityPath, fieldName);
        return new OrderSpecifier<>(Order.DESC, fieldPath);
    }
}

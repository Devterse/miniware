package com.miniware.blog.api.post.repository;

import com.miniware.blog.api.post.constant.PostSearchType;
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
import java.util.stream.Stream;

import static com.miniware.blog.api.board.entity.QBoard.board;
import static com.miniware.blog.api.post.entity.QPost.post;
import static com.miniware.blog.api.user.entity.QUser.user;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Post> findPostById(Long id) {
        Post result = jpaQueryFactory.selectFrom(post)
                .join(post.board, board).fetchJoin()
                .join(post.user, user).fetchJoin()
                .where(post.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<Post> getList(Long boardId, PostSearch searchDto, Pageable pageable) {
        List<Post> content = jpaQueryFactory
            .selectFrom(post)
            .join(post.board, board).fetchJoin()
            .where(
                post.board.id.eq(boardId).and(searchCondition(searchDto))
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(dynamicOrder(post, searchDto.getSortType().getField()))
            .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(
                    post.board.id.eq(boardId).and(searchCondition(searchDto))
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression searchCondition(PostSearch postSearch) {
        String keyValue = postSearch.getKeyValue();
        PostSearchType searchType = postSearch.getSearchType();

        // 검색어가 없을 경우 null 반환
        if (keyValue == null || keyValue.isEmpty()) {
            return null;
        }

        // 특정 searchType이 지정된 경우 해당 필드만 검색
        if (searchType != null) {
            StringPath path = Expressions.stringPath(post, searchType.getField());
            return path.containsIgnoreCase(keyValue);
        }

        // searchType이 없을 경우 모든 필드에 대해 OR 조건 생성
        List<BooleanExpression> conditions = Stream.of(PostSearchType.values())
                .map(type -> Expressions.stringPath(post, type.getField())
                        .containsIgnoreCase(keyValue))
                .toList();

        // OR 조건을 조합
        return conditions.stream()
                .reduce(BooleanExpression::or)
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

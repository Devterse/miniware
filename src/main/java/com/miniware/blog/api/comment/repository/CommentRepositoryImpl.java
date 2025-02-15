package com.miniware.blog.api.comment.repository;

import com.miniware.blog.api.comment.entity.Comment;
import com.miniware.blog.api.comment.entity.QComment;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.miniware.blog.api.comment.entity.QComment.*;
import static com.miniware.blog.api.user.entity.QUser.*;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Comment> findParentComments(Long postId, Pageable pageable) {
        List<Comment> comments = jpaQueryFactory
                .selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.post.id.eq(postId)
                        .and(comment.parent.isNull()))   //부모 댓글만 조회
                .orderBy(comment.createAt.desc())   //최신순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.post.id.eq(postId).and(comment.parent.isNull()));

        return PageableExecutionUtils.getPage(comments, pageable, count::fetchOne);
    }

    @Override
    public Page<Comment> findRepliesByParentId(Long parentId, Pageable pageable) {
        List<Comment> replies = jpaQueryFactory
                .selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.parent.id.eq(parentId)
                        .or(comment.parent.parent.id.eq(parentId))
                        .or(comment.parent.parent.parent.id.eq(parentId))) // 3-depth 이상의 댓글도 포함

                .orderBy(comment.createAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.parent.id.eq(parentId));

        return PageableExecutionUtils.getPage(replies, pageable, count::fetchOne);
    }

    @Override
    public Optional<Comment> findCommentById(Long id) {
        Comment result = jpaQueryFactory.selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}

package com.miniware.blog.api.comment.repository;

import com.miniware.blog.api.comment.entity.Comment;
import com.miniware.blog.api.comment.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findCommentsWithReplies(Long postId) {
        QComment comment = QComment.comment;  // 부모 댓글
        QComment reply = new QComment("reply");  // 대댓글에 대한 별칭

        return jpaQueryFactory.selectFrom(comment) //대댓글 fetch join으로 한번에 조회
                .leftJoin(comment.replies, reply).fetchJoin()
                .where(comment.post.id.eq(postId)
                ).fetch();
    }
}

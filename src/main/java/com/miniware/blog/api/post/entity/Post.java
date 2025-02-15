package com.miniware.blog.api.post.entity;

import com.miniware.blog.api.board.entity.Board;
import com.miniware.blog.api.comment.entity.Comment;
import com.miniware.blog.api.common.entity.BaseEntity;
import com.miniware.blog.api.post.dto.request.PostEdit;
import com.miniware.blog.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    //    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private int likeCnt;
    private int viewCnt;
    private int commentCnt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Post(String title, String content, Board board, User user) {
        this.title = title;
        this.content = content;
        this.board = board;
        this.user = user;
    }

    //post 수정 메서드
    public void edit(PostEdit postEdit, Board board) {
        this.title = postEdit.getTitle();
        this.content = postEdit.getContent();
        this.board = board;
    }

    //조회수 증가
    public void incrementViewCount() {
        this.viewCnt++;
    }

    //좋아요 증가
    public void incrementLikeCount() {
        this.likeCnt++;
    }

    //좋아요 감소
    public void decrementLikeCount() {
        if (this.likeCnt > 0) {
            this.likeCnt--;
        }
    }

    //댓글 수 증가
    public void incrementCommentCount() {
        this.commentCnt++;
    }

    //댓글수 감소
    public void decrementCommentCount() {
        if (this.commentCnt > 0) {
            this.commentCnt--;
        }
    }

    public boolean isAuthor(Long userId) {
        return this.user.getId().equals(userId);
    }
}

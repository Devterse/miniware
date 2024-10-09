package com.miniware.blog.api.post.entity;

import com.miniware.blog.api.board.entity.Board;
import com.miniware.blog.api.comment.entity.Comment;
import com.miniware.blog.api.common.entity.BaseEntity;
import com.miniware.blog.api.post.dto.request.PostEdit;
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

    private String title;
//    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    public Post(String title, String content, Board board) {
        this.title = title;
        this.content = content;
        this.board = board;
    }

    //post 수정 메서드
    public void edit(PostEdit postEdit, Board board) {
        this.title = postEdit.getTitle();
        this.content = postEdit.getContent();
        this.board = board;
    }
}

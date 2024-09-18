package com.miniware.blog.api.comment.entity;

import com.miniware.blog.api.comment.dto.request.CommentEdit;
import com.miniware.blog.api.common.entity.BaseEntity;
import com.miniware.blog.api.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    @Builder
    public Comment(Comment parent, Post post, String content, String password, String author) {
        this.parent = parent;
        this.post = post;
        this.content = content;
        this.password = password;
        this.author = author;
    }

    public void edit(CommentEdit commentEdit) {
        this.content = commentEdit.getContent();
    }
}
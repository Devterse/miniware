package com.miniware.blog.api.comment.entity;

import com.miniware.blog.api.comment.dto.request.CommentEdit;
import com.miniware.blog.api.common.entity.BaseEntity;
import com.miniware.blog.api.post.entity.Post;
import com.miniware.blog.api.user.entity.User;
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
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  //댓글이 속한 게시글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent; //부모 댓글

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();  //대댓글(자식 댓글들)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Comment(Comment parent, Post post, String content, String password, User user) {
        this.parent = parent;
        this.post = post;
        this.content = content;
        this.password = password;
        this.user = user;
    }

    public void edit(CommentEdit commentEdit) {
        this.content = commentEdit.getContent();
    }

    public boolean isAuthor(Long userId) {
        return this.user.getId().equals(userId);
    }

}

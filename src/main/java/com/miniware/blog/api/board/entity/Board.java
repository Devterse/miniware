package com.miniware.blog.api.board.entity;

import com.miniware.blog.api.board.dto.request.BoardEdit;
import com.miniware.blog.api.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Post> posts;

    @Builder

    public Board(String name) {
        this.name = name;
    }

    public void addPost(Post post) {
        this.posts.add(post);
        post.addBoard(this);
    }

    public void edit(BoardEdit boardEdit) {
        this.name = boardEdit.getName();
    }

}

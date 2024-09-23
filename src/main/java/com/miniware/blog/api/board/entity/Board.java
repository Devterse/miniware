package com.miniware.blog.api.board.entity;

import com.miniware.blog.api.board.dto.request.BoardEdit;
import com.miniware.blog.api.common.entity.BaseEntity;
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
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "board",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //Post 추가
    public void addPost(Post post) {
        this.posts.add(post);
        post.setBoard(this);
    }

    //Post 삭제
    public void removePost(Post post) {
        posts.remove(post);
        post.setBoard(null);
    }

    public void edit(BoardEdit boardEdit) {
        this.name = boardEdit.getName();
    }

}

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

    //board 수정 메서드
    public void edit(BoardEdit boardEdit) {
        this.name = boardEdit.getName();
        this.description = boardEdit.getDescription();
    }

    //post 추가 메서드
    public void addPost(Post post) {
        this.posts.add(post);
        post.setBoard(this);
    }

    //post 제거 메서드
    public void removePost(Post post) {
        this.posts.remove(post);
        post.setBoard(null);
    }

}

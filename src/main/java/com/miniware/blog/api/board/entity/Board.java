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

    public void edit(BoardEdit boardEdit) {
        this.name = boardEdit.getName();
        this.description = boardEdit.getDescription();
    }

}

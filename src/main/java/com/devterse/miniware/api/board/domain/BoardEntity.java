package com.devterse.miniware.api.board.domain;

import com.devterse.miniware.api.board.dto.ReqUpdateBoardDto;
import com.devterse.miniware.api.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_boards")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "user_id")
    private String userId;

    @Builder
    public BoardEntity(Long id, String title, String content, String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public void update(ReqUpdateBoardDto dto) {
        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            this.title = dto.getTitle();
        }
        if (dto.getContent()!= null && !dto.getContent().isEmpty() ) {
            this.content = dto.getContent();
        }
    }
}

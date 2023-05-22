package com.devterse.miniware.api.board.dto;

import com.devterse.miniware.api.board.domain.BoardEntity;
import lombok.Builder;
import lombok.Getter;

public class ReqBoardDto {

    @Getter
    public static class SaveBoardDto{
        private final String title;
        private final String content;
        private final String userId;

        @Builder
        public SaveBoardDto(String title, String content, String userId) {
            this.title = title;
            this.content = content;
            this.userId = userId;
        }

        //DTO TO Entity
        public BoardEntity toEntity() {
            return BoardEntity.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();
        }
    }



}

package com.miniware.blog.api.comment.dto.request;

import com.miniware.blog.api.comment.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentCreate {

    @Length(min = 6, max = 18, message = "비밀번호는 6~18글자로 입력해주세요.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @Length(min = 10, max = 1000, message = "내용은 10~1000자까지 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    private final Long parentId;

    @Builder
    public CommentCreate(String password, String content, Long parentId) {
        this.password = password;
        this.content = content;
        this.parentId = parentId;
    }
}

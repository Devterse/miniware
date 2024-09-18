package com.miniware.blog.api.comment.dto.request;

import com.miniware.blog.api.comment.entity.Comment;
import com.miniware.blog.api.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentCreate {

    @Length(min = 1, max = 8, message = "작성자는 1~8글자로 입력해주세요.")
    @NotBlank(message = "작성자를 입력해주세요.")
    private final String author;

    @Length(min = 6, max = 18, message = "비밀번호는 6~18글자로 입력해주세요.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @Length(min = 10, max = 1000, message = "내용은 10~1000자까지 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    @Builder
    public CommentCreate(String author, String password, String content) {
        this.author = author;
        this.password = password;
        this.content = content;
    }

    public Comment toEntity(Post post) {
        return Comment.builder()
                .author(author)
                .password(password)
                .content(content)
                .post(post)
                .build();
    }

    public Comment toEntity(Comment parent) {
        return Comment.builder()
                .author(author)
                .password(password)
                .content(content)
                .post(parent.getPost())
                .parent(parent)
                .build();
    }
}

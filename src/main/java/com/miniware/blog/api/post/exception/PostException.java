package com.miniware.blog.api.post.exception;

import com.miniware.blog.api.post.constant.PostResult;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException  {

    private final PostResult postResult;

    public PostException(PostResult postResult) {
        super(postResult.getMessage());
        this.postResult = postResult;
    }

    public static PostException of(PostResult postResult) {
        return new PostException(postResult);
    }

}

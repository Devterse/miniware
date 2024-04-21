package com.miniware.blog.api.common.constant;

import org.springframework.http.HttpStatus;

public interface CodeData {
    String getCode();
    String getMessage();
    HttpStatus getHttpStatus();
}

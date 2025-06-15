package com.miniware.blog.api.common.dto;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.constant.ResponseCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@ToString
public class ResponseDto<T> {

    private final boolean success;
    private final int status;
    private final String code;
    private final String message;
    private final T data;

    public ResponseDto(CodeData codeData, T data) {
        this.success = codeData.getHttpStatus().is2xxSuccessful();
        this.status = codeData.getHttpStatus().value();
        this.code = codeData.getCode();
        this.message = codeData.getMessage();
        this.data = data;
    }

    public static <T> ResponseDto<T> of(CodeData codeData, T data) {
        return new ResponseDto<>(codeData, data);
    }

    public static ResponseDto<Void> of(CodeData codeData) {
        return new ResponseDto<>(codeData, null);
    }

    public static ResponseDto<List<ValidationError>> validationError(BindingResult bindingResult) {
        List<ValidationError> errors = bindingResult.getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage())).toList();
        return new ResponseDto<>(ResponseCode.VALIDATION_ERROR, errors);
    }

}

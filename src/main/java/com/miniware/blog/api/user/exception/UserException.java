package com.miniware.blog.api.user.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.exception.CustomException;

import static com.miniware.blog.api.user.constants.UserCode.*;


public class UserException extends CustomException {
    public UserException(CodeData codeData) {
        super(codeData);
    }

    public static UserException notFound() {
        return new UserException(USER_NOT_FOUND);
    }

    public static UserException created() {
        return new UserException(USER_CREATED);
    }

    public static UserException creationFailed() {
        return new UserException(USER_CREATION_FAILED);
    }

    public static UserException updated() {
        return new UserException(USER_UPDATED);
    }

    public static UserException updatedFailed() {
        return new UserException(USER_UPDATE_FAILED);
    }

    public static UserException deleted() {
        return new UserException(USER_DELETED);
    }

    public static UserException deletionFailed() {
        return new UserException(USER_DELETION_FAILED);
    }

    public static UserException duplicate() {
        return new UserException(USER_NAME_DUPLICATE);
    }

}

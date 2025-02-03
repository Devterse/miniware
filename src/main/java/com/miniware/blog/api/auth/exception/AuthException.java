package com.miniware.blog.api.auth.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.exception.CustomException;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import static com.miniware.blog.api.auth.constants.AuthCode.*;

@Getter
public class AuthException extends CustomException {

    public AuthException(CodeData codeData) {
        super(codeData);
    }

    public static AuthException invalidUsernameOrPassword() {
        return new AuthException(LOGIN_FAILURE);
    }

    public static AuthException accountLocked() {
        return new AuthException(ACCOUNT_LOCKED);
    }

    public static AuthException accountDisabled() {
        return new AuthException(ACCOUNT_DISABLED);
    }

    public static AuthException credentialsExpired() {
        return new AuthException(CREDENTIALS_EXPIRED);
    }

    public static AuthException refreshTokenInvalid() {
        return new AuthException(REFRESH_TOKEN_INVALID);
    }

    public static AuthException tokenInvalid() {
        return new AuthException(TOKEN_INVALID);
    }

    public static AuthException unauthorizedAccess() {
        return new AuthException(UNAUTHORIZED_ACCESS);
    }
}

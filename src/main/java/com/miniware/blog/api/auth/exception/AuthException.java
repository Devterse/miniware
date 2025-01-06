package com.miniware.blog.api.auth.exception;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;
import static com.miniware.blog.api.auth.constants.AuthCode.*;

@Getter
public class AuthException extends BadCredentialsException {

    private final CodeData codeData;

    public AuthException(CodeData codeData) {
        super(codeData.getMessage());
        this.codeData = codeData;
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
}

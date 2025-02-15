package com.miniware.blog.api.auth.security;

import com.miniware.blog.api.auth.exception.AuthException;
import com.miniware.blog.api.auth.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getLoginUserId()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw AuthException.unauthorizedAccess();
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }
}

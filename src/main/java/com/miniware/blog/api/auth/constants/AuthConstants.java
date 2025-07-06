package com.miniware.blog.api.auth.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AuthConstants {
    // Cookie names
    public static final String REFRESH_TOKEN = "refreshToken";

    // JWT Claims
    public static final String USER_ID = "userId";
    public static final String ROLES = "roles";
    public static final String USERNAME = "username";
    public static final String TOKEN = "token";

    // Header
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";  // 공백 주의!

    // Redis Key Prefix
    public static final String REDIS_REFRESH_TOKEN_PREFIX = "refresh:";

}

package com.miniware.blog.api.user.dto.request;

import com.miniware.blog.api.user.constants.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserEdit {
    private final String username;
    private final String email;
    private final Set<Role> roles;

    @Builder
    public UserEdit(String username, String email, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}

package com.miniware.blog.api.user.dto.response;

import com.miniware.blog.api.user.constants.Role;
import com.miniware.blog.api.user.entity.User;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserResponse {

    private final Long id;
    private final String username;
    private final Set<Role> roles;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles();
    }

    public static UserResponse of(User user) {
        return new UserResponse(user);
    }
}

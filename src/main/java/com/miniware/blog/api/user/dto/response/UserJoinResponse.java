package com.miniware.blog.api.user.dto.response;

import com.miniware.blog.api.user.constants.Role;
import com.miniware.blog.api.user.entity.User;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserJoinResponse {
    private Long id;
    private String username;
    private Set<Role> roles;

    public UserJoinResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles();
    }

    public static UserJoinResponse of(User user) {
        return new UserJoinResponse(user);
    }
}

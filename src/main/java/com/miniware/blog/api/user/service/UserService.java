package com.miniware.blog.api.user.service;

import com.miniware.blog.api.auth.model.CustomUserDetails;
import com.miniware.blog.api.user.dto.request.ChangePassword;
import com.miniware.blog.api.user.dto.request.UserEdit;
import com.miniware.blog.api.user.dto.request.UserJoin;
import com.miniware.blog.api.user.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse join(UserJoin request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    void deleteUser(Long id);
    UserResponse editUser(Long id, UserEdit request);
    UserResponse changePassword(Long id, ChangePassword request);
    UserResponse getCurrentUser(CustomUserDetails userDetails);
}

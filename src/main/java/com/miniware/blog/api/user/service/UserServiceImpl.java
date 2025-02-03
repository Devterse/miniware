package com.miniware.blog.api.user.service;

import com.miniware.blog.api.auth.exception.AuthException;
import com.miniware.blog.api.auth.model.CustomUserDetails;
import com.miniware.blog.api.user.constants.Role;
import com.miniware.blog.api.user.dto.request.ChangePassword;
import com.miniware.blog.api.user.dto.request.UserEdit;
import com.miniware.blog.api.user.dto.request.UserJoin;
import com.miniware.blog.api.user.dto.response.UserResponse;
import com.miniware.blog.api.user.entity.User;
import com.miniware.blog.api.user.exception.UserException;
import com.miniware.blog.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //사용자 가입
    @Override
    @Transactional
    public UserResponse join(UserJoin request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw UserException.duplicate();
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(Role.USER))
                .build();
        userRepository.save(user);
        return UserResponse.of(user);
    }

    //사용자 조회
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserException::notFound);
        return UserResponse.of(user);
    }

    //사용자 목록 조회
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponse::new).toList();
    }

    //사용자 삭제
    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserException::notFound);
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserResponse editUser(Long id, UserEdit request) {
        User user = userRepository.findById(id).orElseThrow(UserException::notFound);

        if(user.getUsername().equals(request.getUsername())) {
            throw UserException.duplicate();
        }

        user.edit(request);

        return UserResponse.of(user);
    }

    @Override
    @Transactional
    public UserResponse changePassword(Long id, ChangePassword request) {
        User user = userRepository.findById(id).orElseThrow(UserException::notFound);

        String currentPassword = request.getCurrentPassword();
        String newPassword = request.getNewPassword();

        if(!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw UserException.passwordIncorrect();
        }

        user.changePassword(passwordEncoder.encode(newPassword));
        return UserResponse.of(user);
    }

    @Override
    public UserResponse getCurrentUser(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw AuthException.unauthorizedAccess();
        }
        User user = userRepository.findById(userDetails.getUserId()).orElseThrow(UserException::notFound);
        return UserResponse.of(user);
    }
}

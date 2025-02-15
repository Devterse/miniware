package com.miniware.blog.api.user.controller;

import com.miniware.blog.api.auth.model.CustomUserDetails;
import com.miniware.blog.api.common.dto.ResponseDto;
import com.miniware.blog.api.user.dto.request.ChangePassword;
import com.miniware.blog.api.user.dto.request.UserEdit;
import com.miniware.blog.api.user.dto.request.UserJoin;
import com.miniware.blog.api.user.dto.response.UserResponse;
import com.miniware.blog.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.miniware.blog.api.user.constants.UserCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    //사용자 가입
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> join(@Valid @RequestBody UserJoin request) {
        userService.join(request);
        ResponseDto<Void> result = ResponseDto.of(USER_CREATED);
        return ResponseEntity.ok(result);
    }

    //사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse userInfo = userService.getUserById(id);
        ResponseDto<UserResponse> result = ResponseDto.of(USER_RETRIEVED, userInfo);
        return ResponseEntity.ok(result);
    }

    //로그인한 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<UserResponse>> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetail) {
        UserResponse currentUser = userService.getCurrentUser(userDetail);
        ResponseDto<UserResponse> result = ResponseDto.of(USER_RETRIEVED, currentUser);
        return ResponseEntity.ok(result);
    }

    //사용자 목록
    @GetMapping
    public ResponseEntity<ResponseDto<List<UserResponse>>> getAllUsers() {
        List<UserResponse> userList = userService.getAllUsers();
        ResponseDto<List<UserResponse>> result = ResponseDto.of(USER_RETRIEVED, userList);
        return ResponseEntity.ok(result);
    }

    //사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ResponseDto<Void> result = ResponseDto.of(USER_DELETED);
        return ResponseEntity.ok(result);
    }

    //사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateUser(@PathVariable Long id, @RequestBody UserEdit request) {
        userService.editUser(id, request);
        ResponseDto<Void> result = ResponseDto.of(USER_UPDATED);
        return ResponseEntity.ok(result);
    }

    // 비밀번호 변경
    @PatchMapping("/{id}/password")
    public ResponseEntity<ResponseDto<Void>> changePassword(@PathVariable Long id,@Valid @RequestBody ChangePassword request) {
        userService.changePassword(id, request);
        ResponseDto<Void> result = ResponseDto.of(USER_PW_UPDATED);
        return ResponseEntity.ok(result);
    }
}

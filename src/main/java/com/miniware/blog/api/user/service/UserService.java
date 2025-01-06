package com.miniware.blog.api.user.service;

import com.miniware.blog.api.user.constants.Role;
import com.miniware.blog.api.user.dto.request.UserJoin;
import com.miniware.blog.api.user.dto.response.CustomUserDetails;
import com.miniware.blog.api.user.dto.response.UserJoinResponse;
import com.miniware.blog.api.user.entity.User;
import com.miniware.blog.api.user.exception.UserException;
import com.miniware.blog.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserJoinResponse join(UserJoin request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw UserException.duplicate();
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(Role.USER))
                .build();
        userRepository.save(user);
        return UserJoinResponse.of(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return CustomUserDetails.of(user);
    }

    public Set<Role> getRoles(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return user.getRoles();
    }

}

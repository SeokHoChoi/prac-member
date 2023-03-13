package com.board.study.studyboard.service;

import com.board.study.studyboard.domain.Authority;
import com.board.study.studyboard.domain.User;
import com.board.study.studyboard.dto.UserDto;
import com.board.study.studyboard.repository.UserRepository;
import com.board.study.studyboard.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        UUID uuid = UUID.randomUUID();
        User user = User.builder()
                .id(uuid)
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .activated(true)
                .authorities(Collections.singleton(authority))
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public void updateUserNickname(String username, String nickname) {
        userRepository.findOneWithAuthoritiesByUsername(username).ifPresent(user -> {
            user.setNickname(nickname);
            userRepository.save(user);
        });
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    @Transactional
    public void withdraw(String username) {
        User user = userRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 회원이 없습니다."));
        userRepository.delete(user);
    }
}
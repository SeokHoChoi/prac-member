package com.board.study.studyboard.controller;

import com.board.study.studyboard.domain.User;
import com.board.study.studyboard.dto.UserDto;
import com.board.study.studyboard.repository.UserRepository;
import com.board.study.studyboard.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @DeleteMapping("/user")
    public ResponseEntity<Void> withdraw(@RequestParam String username) {
        userService.withdraw(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<Void> updateMember(@PathVariable String username,
                                             @RequestBody UpdateMemberRequest request) {
        userService.updateUserNickname(username, request.getNickname());
        return ResponseEntity.noContent().build();
    }

    @Setter
    @Getter
    static class UpdateMemberRequest {
        private String nickname;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}
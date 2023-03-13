package com.board.study.studyboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    @NotNull
    @Size(min = 36, max = 36)
    private UUID id;
    @NotNull
    private String username;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;
}


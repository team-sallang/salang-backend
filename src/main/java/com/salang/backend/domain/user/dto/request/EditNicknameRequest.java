package com.salang.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EditNicknameRequest {

    @NotBlank(message = "닉네임을 입력해주세요")
    @Length(min = 2, max = 20, message = "닉네임은 2문자 이상 20문자 이하여야 합니다")
    private final String nickname;
}

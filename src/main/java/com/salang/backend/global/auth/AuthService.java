package com.salang.backend.global.auth;

import com.salang.backend.domain.user.entity.User;
import com.salang.backend.domain.user.repository.UserRepository;
import com.salang.backend.global.error.ErrorCode;
import com.salang.backend.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User getLoginUser(){
        // 추후 Spring Security + jwt를 활용한 로그인 구현 되면
        // SecurityContextHolder에서 사용자 정보 꺼내와서 조회하도록 변경
        return userRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}

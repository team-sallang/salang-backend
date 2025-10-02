package com.salang.backend.domain.user.service;

import com.salang.backend.domain.user.dto.response.UserProfileResponse;
import com.salang.backend.domain.user.entity.Hobby;
import com.salang.backend.domain.user.entity.User;
import com.salang.backend.domain.user.entity.UserHobby;
import com.salang.backend.domain.user.repository.HobbyRepository;
import com.salang.backend.domain.user.repository.UserHobbyRepository;
import com.salang.backend.domain.user.repository.UserRepository;
import com.salang.backend.global.auth.AuthService;
import com.salang.backend.global.error.ErrorCode;
import com.salang.backend.global.error.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserHobbyRepository userHobbyRepository;
    private final AuthService authService;

    public UserProfileResponse getProfile() {
        User loginUser = authService.getLoginUser();
        List<Hobby> hobbies = userHobbyRepository.findByUserId(loginUser.getId())
                .stream()
                // 추후 캐싱 적용가능
                .map(UserHobby::getHobby)
                .toList();
        return UserProfileResponse.from(loginUser, hobbies);
    }
}

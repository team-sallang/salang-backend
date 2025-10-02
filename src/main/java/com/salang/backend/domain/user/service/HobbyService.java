package com.salang.backend.domain.user.service;

import com.salang.backend.domain.user.entity.User;
import com.salang.backend.domain.user.entity.UserHobby;
import com.salang.backend.domain.user.repository.HobbyRepository;
import com.salang.backend.domain.user.repository.UserHobbyRepository;
import com.salang.backend.global.error.ErrorCode;
import com.salang.backend.global.error.exception.BusinessException;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HobbyService {

    private final UserHobbyRepository userHobbyRepository;
    private final HobbyRepository hobbyRepository;

    @Transactional
    public void updateUserHobbies(User user, List<Long> hobbyIds) {
        userHobbyRepository.deleteAllByUserId(user.getId());

        if (hobbyIds.isEmpty()) {
            return; // 초기화
        }

        if (hobbyIds.size() > 3) {
            throw new BusinessException(ErrorCode.INVALID_HOBBY_SIZE);
        }

        if (hobbyIds.size() != new HashSet<>(hobbyIds).size()) {
            throw new BusinessException(ErrorCode.DUPLICATE_HOBBY);
        }

        List<UserHobby> newUserHobbies = hobbyIds.stream()
                .map(id -> UserHobby.builder()
                        .user(user)
                        .hobby(hobbyRepository.getReferenceById(id))
                        .build())
                .toList();
        userHobbyRepository.saveAll(newUserHobbies);
    }
}

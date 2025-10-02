package com.salang.backend.domain.user.service;

import com.salang.backend.domain.user.NicknameAlreadyExistException;
import com.salang.backend.domain.user.dto.request.EditNicknameRequest;
import com.salang.backend.domain.user.dto.request.EditRegionAndHobbyRequest;
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
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserHobbyRepository userHobbyRepository;
    private final HobbyRepository hobbyRepository;
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

    @Transactional
    public void editNickname(EditNicknameRequest editNicknameRequest) {
        User loginUser = authService.getLoginUser();
        String updateNickname = editNicknameRequest.getNickname();

        // 닉네임 중복 검사
        if (userRepository.existsByNickname(updateNickname)
                || loginUser.getNickname().equals(updateNickname)) {
            throw new NicknameAlreadyExistException();
        }

        loginUser.updateNickname(updateNickname);
    }

    @Transactional
    public void editRegionAndHobby(@Valid EditRegionAndHobbyRequest editRegionAndHobbyRequest) {
        User loginUser = authService.getLoginUser();

        // 지역 업데이트
        loginUser.updateRegion(editRegionAndHobbyRequest.getRegion());

        // 취미 업데이트
        List<Long> hobbyIds = editRegionAndHobbyRequest.getHobbyIds();

        userHobbyRepository.deleteAllByUserId(loginUser.getId()); // 기존 취미 제거

        if (hobbyIds.isEmpty()) { // 빈 배열이 넘어올 시 바로 마무리
            return;
        }

        if (hobbyIds.size() > 3) { // 취미가 3개 이상 선택된 경우 예외처리
            throw new BusinessException(ErrorCode.INVALID_HOBBY_SIZE);
        }

        if (hobbyIds.size()!=new HashSet<>(hobbyIds).size()) { // 취미 중복된 경우 예외 처리
            throw new BusinessException(ErrorCode.DUPLICATE_HOBBY);
        }

        // 새 취미 추가
        List<UserHobby> newUserHobbies = hobbyIds.stream()
                .map(id -> UserHobby.builder()
                        .user(loginUser)
                        .hobby(hobbyRepository.getReferenceById(id))
                        .build())
                .toList();
        userHobbyRepository.saveAll(newUserHobbies);
    }
}

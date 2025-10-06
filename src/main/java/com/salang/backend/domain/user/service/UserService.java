package com.salang.backend.domain.user.service;

import com.salang.backend.domain.user.NicknameAlreadyExistException;
import com.salang.backend.domain.user.dto.request.EditNicknameRequest;
import com.salang.backend.domain.user.dto.request.EditRegionAndHobbyRequest;
import com.salang.backend.domain.user.dto.response.UserProfileResponse;
import com.salang.backend.domain.user.entity.Hobby;
import com.salang.backend.domain.user.entity.User;
import com.salang.backend.domain.user.entity.UserHobby;
import com.salang.backend.domain.user.repository.UserHobbyRepository;
import com.salang.backend.domain.user.repository.UserRepository;
import com.salang.backend.global.auth.AuthService;
import com.salang.backend.infra.S3Uploader;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserHobbyRepository userHobbyRepository;
    private final HobbyService hobbyService;
    private final AuthService authService;
    private final S3Uploader s3Uploader;

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
        hobbyService.updateUserHobbies(loginUser, editRegionAndHobbyRequest.getHobbyIds());
    }

    @Transactional
    public void deleteUser() {
        User loginUser = authService.getLoginUser();
        userRepository.deleteById(loginUser.getId());
    }

    @Transactional
    public String updateProfileImage(MultipartFile file) {
        User loginUser = authService.getLoginUser();
        String profileImageUrl = s3Uploader.updateProfileImage(file, loginUser.getProfileImage());
        loginUser.updateProfileImage(profileImageUrl);
        return profileImageUrl;
    }
}

package com.salang.backend.domain.user.service;

import com.salang.backend.domain.user.dto.request.EditNicknameRequest;
import com.salang.backend.domain.user.dto.request.EditRegionAndHobbyRequest;
import com.salang.backend.domain.user.dto.response.UserProfileResponse;
import com.salang.backend.domain.user.entity.Gender;
import com.salang.backend.domain.user.entity.Hobby;
import com.salang.backend.domain.user.entity.Region;
import com.salang.backend.domain.user.entity.SocialType;
import com.salang.backend.domain.user.entity.Status;
import com.salang.backend.domain.user.entity.User;
import com.salang.backend.domain.user.entity.UserHobby;
import com.salang.backend.domain.user.entity.UserRole;
import com.salang.backend.domain.user.exception.NicknameAlreadyExistException;
import com.salang.backend.domain.user.repository.UserHobbyRepository;
import com.salang.backend.domain.user.repository.UserRepository;
import com.salang.backend.global.auth.AuthService;
import com.salang.backend.infra.S3Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserHobbyRepository userHobbyRepository;

    @Mock
    private HobbyService hobbyService;

    @Mock
    private AuthService authService;

    @Mock
    private S3Uploader s3Uploader;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private Hobby testHobby;
    private UserHobby testUserHobby;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .nickname("testUser")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .role(UserRole.USER)
                .region(Region.SEOUL)
                .phoneNumber("010-1234-5678")
                .reputation(0)
                .socialType(SocialType.KAKAO)
                .socialId("social123")
                .status(Status.ACTIVE)
                .profileImage("profile.jpg")
                .ciHmac("ci123")
                .build();

        testHobby = Hobby.builder()
                .id(1L)
                .name("독서")
                .build();

        testUserHobby = UserHobby.builder()
                .id(1L)
                .user(testUser)
                .hobby(testHobby)
                .build();
    }

    @Test
    @DisplayName("사용자 프로필을 조회한다")
    void getProfile() {
        // given
        List<UserHobby> userHobbies = List.of(testUserHobby);
        given(authService.getLoginUser()).willReturn(testUser);
        given(userHobbyRepository.findByUserId(testUser.getId())).willReturn(userHobbies);

        // when
        UserProfileResponse result = userService.getProfile();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNickname()).isEqualTo("testUser");
        assertThat(result.getRegion()).isEqualTo("서울특별시");
        assertThat(result.getHobbies()).containsExactly("독서");
        assertThat(result.getProfileImageUrl()).isEqualTo("profile.jpg");
        verify(authService).getLoginUser();
        verify(userHobbyRepository).findByUserId(testUser.getId());
    }

    @Test
    @DisplayName("닉네임을 성공적으로 수정한다")
    void editNickname_Success() {
        // given
        EditNicknameRequest request = new EditNicknameRequest("newNickname");
        given(authService.getLoginUser()).willReturn(testUser);
        given(userRepository.existsByNickname("newNickname")).willReturn(false);

        // when
        userService.editNickname(request);

        // then
        assertThat(testUser.getNickname()).isEqualTo("newNickname");
        verify(authService).getLoginUser();
        verify(userRepository).existsByNickname("newNickname");
    }

    @Test
    @DisplayName("중복된 닉네임으로 수정 시 예외가 발생한다")
    void editNickname_WithDuplicateNickname_ThrowsException() {
        // given
        EditNicknameRequest request = new EditNicknameRequest("duplicateNickname");
        given(authService.getLoginUser()).willReturn(testUser);
        given(userRepository.existsByNickname("duplicateNickname")).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.editNickname(request))
                .isInstanceOf(NicknameAlreadyExistException.class);
        verify(authService).getLoginUser();
        verify(userRepository).existsByNickname("duplicateNickname");
    }

    @Test
    @DisplayName("현재 닉네임과 동일한 닉네임으로 수정 시 예외가 발생한다")
    void editNickname_WithSameNickname_ThrowsException() {
        // given
        EditNicknameRequest request = new EditNicknameRequest("testUser");
        given(authService.getLoginUser()).willReturn(testUser);

        // when & then
        assertThatThrownBy(() -> userService.editNickname(request))
                .isInstanceOf(NicknameAlreadyExistException.class);
        verify(authService).getLoginUser();
    }

    @Test
    @DisplayName("지역과 취미를 수정한다")
    void editRegionAndHobby() {
        // given
        EditRegionAndHobbyRequest request = new EditRegionAndHobbyRequest(Region.BUSAN, List.of(1L, 2L));
        given(authService.getLoginUser()).willReturn(testUser);

        // when
        userService.editRegionAndHobby(request);

        // then
        assertThat(testUser.getRegion()).isEqualTo(Region.BUSAN);
        verify(authService).getLoginUser();
        verify(hobbyService).updateUserHobbies(testUser, List.of(1L, 2L));
    }

    @Test
    @DisplayName("사용자를 삭제한다")
    void deleteUser() {
        // given
        given(authService.getLoginUser()).willReturn(testUser);

        // when
        userService.deleteUser();

        // then
        verify(authService).getLoginUser();
        verify(userRepository).deleteById(testUser.getId());
    }

    @Test
    @DisplayName("프로필 이미지를 업데이트한다")
    void updateProfileImage() {
        // given
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test content".getBytes());
        String expectedFileName = "uuid-generated-filename.jpg"; // S3Uploader는 파일명(키)을 반환
        given(authService.getLoginUser()).willReturn(testUser);
        given(s3Uploader.updateProfileImage(file, "profile.jpg")).willReturn(expectedFileName);

        // when
        String result = userService.updateProfileImage(file);

        // then
        assertThat(result).isEqualTo(expectedFileName);
        assertThat(testUser.getProfileImage()).isEqualTo(expectedFileName);
        verify(authService).getLoginUser();
        verify(s3Uploader).updateProfileImage(file, "profile.jpg");
    }

}
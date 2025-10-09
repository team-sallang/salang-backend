package com.salang.backend.domain.user.service;

import com.salang.backend.domain.user.dto.response.HobbiesResponse;
import com.salang.backend.domain.user.entity.Gender;
import com.salang.backend.domain.user.entity.Hobby;
import com.salang.backend.domain.user.entity.Region;
import com.salang.backend.domain.user.entity.SocialType;
import com.salang.backend.domain.user.entity.Status;
import com.salang.backend.domain.user.entity.User;
import com.salang.backend.domain.user.entity.UserHobby;
import com.salang.backend.domain.user.entity.UserRole;
import com.salang.backend.domain.user.repository.HobbyRepository;
import com.salang.backend.domain.user.repository.UserHobbyRepository;
import com.salang.backend.global.error.ErrorCode;
import com.salang.backend.global.error.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HobbyServiceTest {

    @Mock
    private UserHobbyRepository userHobbyRepository;

    @Mock
    private HobbyRepository hobbyRepository;

    @InjectMocks
    private HobbyService hobbyService;

    private User testUser;
    private Hobby testHobby1;
    private Hobby testHobby2;
    private Hobby testHobby3;
    private Hobby testHobby4;

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

        testHobby1 = Hobby.builder()
                .id(1L)
                .name("독서")
                .build();

        testHobby2 = Hobby.builder()
                .id(2L)
                .name("등산")
                .build();

        testHobby3 = Hobby.builder()
                .id(3L)
                .name("영화감상")
                .build();

        testHobby4 = Hobby.builder()
                .id(4L)
                .name("음악감상")
                .build();
    }

    @Test
    @DisplayName("사용자 취미를 성공적으로 업데이트한다")
    void updateUserHobbies_Success() {
        // given
        List<Long> hobbyIds = List.of(1L, 2L);
        given(hobbyRepository.findById(1L)).willReturn(java.util.Optional.of(testHobby1));
        given(hobbyRepository.findById(2L)).willReturn(java.util.Optional.of(testHobby2));

        // when
        hobbyService.updateUserHobbies(testUser, hobbyIds);

        // then
        verify(userHobbyRepository).deleteAllByUserId(testUser.getId());
        verify(hobbyRepository).findById(1L);
        verify(hobbyRepository).findById(2L);
        verify(userHobbyRepository).saveAll(any());
    }

    @Test
    @DisplayName("존재하지 않는 취미 ID로 업데이트 시 예외가 발생한다")
    void updateUserHobbies_WithNonExistentHobbyId_ThrowsException() {
        // given
        List<Long> hobbyIds = List.of(999L);
        given(hobbyRepository.findById(999L)).willReturn(java.util.Optional.empty());

        // when & then
        assertThatThrownBy(() -> hobbyService.updateUserHobbies(testUser, hobbyIds))
                .isInstanceOf(BusinessException.class);
        verify(userHobbyRepository).deleteAllByUserId(testUser.getId());
        verify(hobbyRepository).findById(999L);
    }

    @Test
    @DisplayName("빈 취미 목록으로 업데이트 시 기존 취미만 삭제한다")
    void updateUserHobbies_WithEmptyList_DeletesOnly() {
        // given
        List<Long> hobbyIds = List.of();

        // when
        hobbyService.updateUserHobbies(testUser, hobbyIds);

        // then
        verify(userHobbyRepository).deleteAllByUserId(testUser.getId());
    }

    @Test
    @DisplayName("취미가 3개를 초과하면 예외가 발생한다")
    void updateUserHobbies_WithMoreThanThreeHobbies_ThrowsException() {
        // given
        List<Long> hobbyIds = List.of(1L, 2L, 3L, 4L);

        // when & then
        assertThatThrownBy(() -> hobbyService.updateUserHobbies(testUser, hobbyIds))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_HOBBY_SIZE);
    }

    @Test
    @DisplayName("중복된 취미 ID가 있으면 예외가 발생한다")
    void updateUserHobbies_WithDuplicateHobbyIds_ThrowsException() {
        // given
        List<Long> hobbyIds = List.of(1L, 2L, 1L);

        // when & then
        assertThatThrownBy(() -> hobbyService.updateUserHobbies(testUser, hobbyIds))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.DUPLICATE_HOBBY);
    }

    @Test
    @DisplayName("모든 취미 목록을 조회한다")
    void getHobbies() {
        // given
        List<Hobby> hobbies = List.of(testHobby1, testHobby2, testHobby3);
        given(hobbyRepository.findAll()).willReturn(hobbies);

        // when
        HobbiesResponse result = hobbyService.getHobbies();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getHobbies()).hasSize(3);
        assertThat(result.getHobbies().get(0).getName()).isEqualTo("독서");
        assertThat(result.getHobbies().get(1).getName()).isEqualTo("등산");
        assertThat(result.getHobbies().get(2).getName()).isEqualTo("영화감상");
        verify(hobbyRepository).findAll();
    }
}

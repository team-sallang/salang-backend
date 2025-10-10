package com.salang.backend.domain.consents.service;

import com.salang.backend.domain.consents.dto.request.UpdateConsentRequest;
import com.salang.backend.domain.consents.dto.response.ConsentsResponse;
import com.salang.backend.domain.consents.entity.Consent;
import com.salang.backend.domain.consents.repository.ConsentRepository;
import com.salang.backend.domain.user.entity.Gender;
import com.salang.backend.domain.user.entity.Region;
import com.salang.backend.domain.user.entity.SocialType;
import com.salang.backend.domain.user.entity.Status;
import com.salang.backend.domain.user.entity.User;
import com.salang.backend.domain.user.entity.UserRole;
import com.salang.backend.global.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsentServiceTest {

    @Mock
    private ConsentRepository consentRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private ConsentService consentService;

    private User testUser;
    private Consent testConsent;

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

        testConsent = Consent.builder()
                .id(1L)
                .user(testUser)
                .type("PRIVACY")
                .version("1.0")
                .agreed(true)
                .agreedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("사용자의 동의 목록을 조회한다")
    void getConsents() {
        // given
        List<Consent> consents = List.of(testConsent);
        given(authService.getLoginUser()).willReturn(testUser);
        given(consentRepository.findByUserId(testUser.getId())).willReturn(consents);

        // when
        ConsentsResponse result = consentService.getConsents();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getConsentResponses()).hasSize(1);
        verify(authService).getLoginUser();
        verify(consentRepository).findByUserId(testUser.getId());
    }

    @Test
    @DisplayName("기존 동의가 있을 때 동의를 업데이트한다")
    void saveOrUpdateConsent_WhenConsentExists_UpdatesConsent() {
        // given
        UpdateConsentRequest request = new UpdateConsentRequest("PRIVACY", "1.0", false);
        given(authService.getLoginUser()).willReturn(testUser);
        given(consentRepository.findByUserIdAndTypeAndVersion(
                testUser.getId(), "PRIVACY", "1.0")).willReturn(Optional.of(testConsent));

        // when
        consentService.saveOrUpdateConsent(request);

        // then
        assertThat(testConsent.isAgreed()).isFalse();
        verify(authService).getLoginUser();
        verify(consentRepository).findByUserIdAndTypeAndVersion(
                testUser.getId(), "PRIVACY", "1.0");
    }

    @Test
    @DisplayName("기존 동의가 없을 때 새로운 동의를 저장한다")
    void saveOrUpdateConsent_WhenConsentNotExists_CreatesNewConsent() {
        // given
        UpdateConsentRequest request = new UpdateConsentRequest("TERMS", "2.0", true);
        given(authService.getLoginUser()).willReturn(testUser);
        given(consentRepository.findByUserIdAndTypeAndVersion(
                testUser.getId(), "TERMS", "2.0")).willReturn(Optional.empty());
        given(consentRepository.save(any(Consent.class))).willReturn(testConsent);

        // when
        consentService.saveOrUpdateConsent(request);

        // then
        verify(authService).getLoginUser();
        verify(consentRepository).findByUserIdAndTypeAndVersion(
                testUser.getId(), "TERMS", "2.0");
        verify(consentRepository).save(any(Consent.class));
    }
}

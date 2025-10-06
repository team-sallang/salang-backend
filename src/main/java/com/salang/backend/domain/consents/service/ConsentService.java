package com.salang.backend.domain.consents.service;

import com.salang.backend.domain.consents.dto.request.UpdateConsentRequest;
import com.salang.backend.domain.consents.dto.response.ConsentsResponse;
import com.salang.backend.domain.consents.entity.Consent;
import com.salang.backend.domain.consents.repository.ConsentRepository;
import com.salang.backend.domain.user.entity.User;
import com.salang.backend.global.auth.AuthService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsentService {

    private final ConsentRepository consentRepository;
    private final AuthService authService;

    public ConsentsResponse getConsents() {
        User loginUser = authService.getLoginUser();
        return ConsentsResponse.of(consentRepository.findByUserId(loginUser.getId()));
    }

    @Transactional
    public void saveOrUpdateConsent(UpdateConsentRequest request) {
        User loginUser = authService.getLoginUser();

        consentRepository.findByUserIdAndTypeAndVersion(loginUser.getId(), request.getType(), request.getVersion())
                .ifPresentOrElse(
                        consent -> {
                            consent.updateAgreed(request.isAgreed());
                            consent.updateAgreedAt();
                        },
                        () -> consentRepository.save(
                                Consent.builder()
                                        .user(loginUser)
                                        .type(request.getType())
                                        .version(request.getVersion())
                                        .agreed(request.isAgreed())
                                        .agreedAt(LocalDateTime.now())
                                        .build()
                        )
                );
    }
}

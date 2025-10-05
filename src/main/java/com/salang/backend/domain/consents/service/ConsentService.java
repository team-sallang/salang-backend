package com.salang.backend.domain.consents.service;

import com.salang.backend.domain.consents.dto.response.ConsentsResponse;
import com.salang.backend.domain.consents.repository.ConsentRepository;
import com.salang.backend.domain.user.entity.User;
import com.salang.backend.global.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsentService {

    private final ConsentRepository consentRepository;
    private final AuthService authService;

    public ConsentsResponse getConsents() {
        User loginUser = authService.getLoginUser();
        return ConsentsResponse.of(consentRepository.findByUserId(loginUser.getId()));
    }
}

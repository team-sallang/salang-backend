package com.salang.backend.domain.consents.controller;

import com.salang.backend.domain.consents.docs.ConsentApiDocs;
import com.salang.backend.domain.consents.dto.request.UpdateConsentRequest;
import com.salang.backend.domain.consents.dto.response.ConsentsResponse;
import com.salang.backend.domain.consents.service.ConsentService;
import com.salang.backend.global.result.ResultCode;
import com.salang.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/consents")
@RequiredArgsConstructor
public class ConsentController implements ConsentApiDocs {

    private final ConsentService consentService;

    @GetMapping
    public ResponseEntity<ResultResponse> getConsents() {
        final ConsentsResponse response = consentService.getConsents();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CONSENTS_SUCCESS, response));
    }

    @PostMapping
    public ResponseEntity<ResultResponse> saveOrUpdateConsent(@Valid @RequestBody UpdateConsentRequest updateConsentRequest) {
        consentService.saveOrUpdateConsent(updateConsentRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_CONSENT_SUCCESS));
    }
}

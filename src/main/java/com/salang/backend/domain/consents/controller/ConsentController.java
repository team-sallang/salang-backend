package com.salang.backend.domain.consents.controller;

import com.salang.backend.domain.consents.dto.response.ConsentsResponse;
import com.salang.backend.domain.consents.service.ConsentService;
import com.salang.backend.global.result.ResultCode;
import com.salang.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/consents")
@RequiredArgsConstructor
public class ConsentController {

    private final ConsentService consentService;

    @Operation(
            summary = "약관 동의 여부 확인",
            description = "내 동의 항목/버전/시각 조회"
    )
    @GetMapping
    public ResponseEntity<ResultResponse> getConsents(){
        final ConsentsResponse response = consentService.getConsents();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CONSENTS_SUCCESS, response));
    }
}

package com.salang.backend.domain.user.controller;

import com.salang.backend.domain.user.dto.response.HobbiesResponse;
import com.salang.backend.domain.user.service.HobbyService;
import com.salang.backend.global.result.ResultCode;
import com.salang.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hobbies")
@RequiredArgsConstructor
public class HobbyController {

    private final HobbyService hobbyService;

    @Operation(
            summary = "취미 카탈로그 조회",
            description = "모든 취미 목록을 반환합니다."
    )
    @GetMapping
    public ResponseEntity<ResultResponse> getHobbies(){
        final HobbiesResponse response = hobbyService.getHobbies();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_HOBBIES_SUCCESS, response));
    }
}

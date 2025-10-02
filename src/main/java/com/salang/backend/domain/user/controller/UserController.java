package com.salang.backend.domain.user.controller;

import com.salang.backend.domain.user.dto.response.UserProfileResponse;
import com.salang.backend.domain.user.service.UserService;
import com.salang.backend.global.result.ResultCode;
import com.salang.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "프로필 조회",
            description = "사용자의 프로필을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<ResultResponse> getProfile(){
        final UserProfileResponse response = userService.getProfile();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_PROFILE_SUCCESS,response));
    }
}

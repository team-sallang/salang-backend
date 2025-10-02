package com.salang.backend.domain.user.controller;

import static com.salang.backend.global.result.ResultCode.*;

import com.salang.backend.domain.user.dto.request.EditNicknameRequest;
import com.salang.backend.domain.user.dto.request.EditRegionAndHobbyRequest;
import com.salang.backend.domain.user.dto.response.UserProfileResponse;
import com.salang.backend.domain.user.service.UserService;
import com.salang.backend.global.result.ResultCode;
import com.salang.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return ResponseEntity.ok(ResultResponse.of(GET_USER_PROFILE_SUCCESS,response));
    }

    @Operation(
            summary = "닉네임 수정",
            description = "사용자의 닉네임을 수정합니다."
    )
    @PatchMapping("/nickname")
    public ResponseEntity<ResultResponse> editNickname(@Valid @RequestBody EditNicknameRequest editNicknameRequest){
        userService.editNickname(editNicknameRequest);
        return ResponseEntity.ok(ResultResponse.of(EDIT_NICKNAME_SUCCESS));
    }

    @Operation(
            summary = "지역/취미 수정",
            description = "지역·취미를 한 번에 부분 수정"
    )
    @PatchMapping
    public ResponseEntity<ResultResponse> editRegionAndHobby(@Valid @RequestBody EditRegionAndHobbyRequest editRegionAndHobbyRequest){
        userService.editRegionAndHobby(editRegionAndHobbyRequest);
        return ResponseEntity.ok(ResultResponse.of(EDIT_REGION_AND_HOBBY_SUCCESS));
    }
}

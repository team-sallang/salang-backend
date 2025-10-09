package com.salang.backend.domain.user.docs;

import com.salang.backend.domain.user.dto.request.EditNicknameRequest;
import com.salang.backend.domain.user.dto.request.EditRegionAndHobbyRequest;
import com.salang.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "유저 API", description = "로그인, 회원가입 외 유저와 관련된 기능을 담당합니다.")
public interface UserApiDocs {

    @Operation(summary = "프로필 조회", description = "사용자의 프로필을 조회합니다.")
    public ResponseEntity<ResultResponse> getProfile();

    @Operation(summary = "닉네임 수정", description = "사용자의 닉네임을 수정합니다.")
    public ResponseEntity<ResultResponse> editNickname(@Valid @RequestBody EditNicknameRequest editNicknameRequest);

    @Operation(summary = "지역/취미 수정", description = "지역·취미를 한 번에 부분 수정")
    public ResponseEntity<ResultResponse> editRegionAndHobby(@Valid @RequestBody EditRegionAndHobbyRequest editRegionAndHobbyRequest);

    @Operation(summary = "프로필 사진 업로드/변경", description = "프로필 사진을 S3에 업로드 후 변경")
    public ResponseEntity<ResultResponse> updateProfileImage(@RequestPart("profileImage") MultipartFile profileImage);

    @Operation(summary = "지역 카탈로그 조회", description = "선택 가능한 지역 목록을 반환합니다.")
    public ResponseEntity<ResultResponse> getRegions();

    @Operation(summary = "회원 탈퇴", description = "회원이 서비스를 탈퇴합니다.")
    public ResponseEntity<ResultResponse> deleteUser();
}

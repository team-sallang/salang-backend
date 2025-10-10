package com.salang.backend.domain.user.docs;

import com.salang.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "취미 API", description = "취미 항목과 관련된 기능을 담당합니다.")
public interface HobbyApiDocs {
    @Operation(summary = "취미 카탈로그 조회", description = "모든 취미 목록을 반환합니다.")
    public ResponseEntity<ResultResponse> getHobbies();
}


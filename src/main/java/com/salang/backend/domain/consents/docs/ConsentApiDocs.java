package com.salang.backend.domain.consents.docs;

import com.salang.backend.domain.consents.dto.request.UpdateConsentRequest;
import com.salang.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "약관 동의 API", description = "약관 동의와 기능을 담당합니다.")
public interface ConsentApiDocs {

    @Operation(summary = "약관 동의 여부 확인", description = "내 동의 항목/버전/시각 조회")
    public ResponseEntity<ResultResponse> getConsents();

    @Operation(summary = "내 약관 동의/철회", description = "내 약관 동의/철회")
    public ResponseEntity<ResultResponse> saveOrUpdateConsent(@Valid @RequestBody UpdateConsentRequest updateConsentRequest);
}

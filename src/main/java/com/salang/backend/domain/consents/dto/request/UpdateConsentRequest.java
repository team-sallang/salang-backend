package com.salang.backend.domain.consents.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class UpdateConsentRequest {

    @NotNull(message = "type은 null일 수 없습니다")
    @Length(max = 50, message = "타입은 50문자 이하여야 합니다")
    private final String type;

    @NotNull(message = "version은 null일 수 없습니다")
    @Length(max = 20, message = "버전은 20문자 이하여야 합니다")
    private final String version;

    private final boolean agreed;
}

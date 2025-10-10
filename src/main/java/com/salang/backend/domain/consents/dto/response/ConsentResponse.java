package com.salang.backend.domain.consents.dto.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsentResponse {

    private final String type;
    private final String version;
    private final boolean agreed;
    private final LocalDateTime agreedAt;

    public static ConsentResponse from(String type, String version, boolean agreed, LocalDateTime agreedAt){
        return new ConsentResponse(type,version,agreed,agreedAt);
    }
}

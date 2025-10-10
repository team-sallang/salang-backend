package com.salang.backend.domain.consents.dto.response;

import com.salang.backend.domain.consents.entity.Consent;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsentsResponse {

    private final List<ConsentResponse> consentResponses;

    public static ConsentsResponse of(List<Consent> consents){
        return new ConsentsResponse(
          consents.stream()
                  .map(c -> ConsentResponse.from(c.getType(), c.getVersion(), c.isAgreed(), c.getAgreedAt())).toList()
        );
    }
}

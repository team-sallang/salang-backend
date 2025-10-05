package com.salang.backend.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HobbyResponse {

    private final Long hobbyId;
    private final String name;

    public static HobbyResponse from(Long hobbyId, String name){
        return new HobbyResponse(hobbyId, name);
    }
}

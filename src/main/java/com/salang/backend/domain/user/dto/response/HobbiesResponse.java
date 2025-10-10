package com.salang.backend.domain.user.dto.response;

import com.salang.backend.domain.user.entity.Hobby;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HobbiesResponse {

    private final List<HobbyResponse> hobbies;

    public static HobbiesResponse of(List<Hobby> hobbies){
        return new HobbiesResponse(
                hobbies.stream()
                        .map(h -> HobbyResponse.from(h.getId(), h.getName()))
                        .toList()
        );
    }
 }

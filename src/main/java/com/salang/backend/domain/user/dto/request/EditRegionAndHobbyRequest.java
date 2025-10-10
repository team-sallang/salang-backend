package com.salang.backend.domain.user.dto.request;

import com.salang.backend.domain.user.entity.Region;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditRegionAndHobbyRequest {
    private final Region region;

    @NotNull(message = "취미 목록은 null일 수 없습니다")
    private final List<Long> hobbyIds;

}

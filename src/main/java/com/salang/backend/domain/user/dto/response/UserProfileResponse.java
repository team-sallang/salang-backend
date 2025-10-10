package com.salang.backend.domain.user.dto.response;


import com.salang.backend.domain.user.entity.Hobby;
import com.salang.backend.domain.user.entity.User;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 현재 반환 상태
 * {
 * "nickname": "chaewon",
 * "regionName": "서울특별시",
 * "hobbies": ["독서", "등산"],
 * "profileImageUrl": "https://cdn.example.com/u1.jpg"
 * }
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserProfileResponse {

    private String nickname;
    private String region;
    private List<String> hobbies;
    private String profileImageUrl;

    public static UserProfileResponse from(User user, List<Hobby> hobbies) {
        return UserProfileResponse.builder()
                .nickname(user.getNickname())
                .region(user.getRegion().getLabel())
                .hobbies(hobbies.stream().map(Hobby::getName).toList())
                .profileImageUrl(
                        (user.getProfileImage()==null || user.getProfileImage().isEmpty())
                                ? null
                                :user.getProfileImage()
                ).build();
    }
}

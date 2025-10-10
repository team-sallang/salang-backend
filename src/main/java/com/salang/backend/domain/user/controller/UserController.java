package com.salang.backend.domain.user.controller;

import static com.salang.backend.global.result.ResultCode.*;

import com.salang.backend.domain.user.docs.UserApiDocs;
import com.salang.backend.domain.user.dto.request.EditNicknameRequest;
import com.salang.backend.domain.user.dto.request.EditRegionAndHobbyRequest;
import com.salang.backend.domain.user.dto.response.UserProfileResponse;
import com.salang.backend.domain.user.entity.Region;
import com.salang.backend.domain.user.service.UserService;
import com.salang.backend.global.result.ResultCode;
import com.salang.backend.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserApiDocs {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResultResponse> getProfile(){
        final UserProfileResponse response = userService.getProfile();
        return ResponseEntity.ok(ResultResponse.of(GET_USER_PROFILE_SUCCESS,response));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<ResultResponse> editNickname(@Valid @RequestBody EditNicknameRequest editNicknameRequest){
        userService.editNickname(editNicknameRequest);
        return ResponseEntity.ok(ResultResponse.of(EDIT_NICKNAME_SUCCESS));
    }

    @PatchMapping
    public ResponseEntity<ResultResponse> editRegionAndHobby(@Valid @RequestBody EditRegionAndHobbyRequest editRegionAndHobbyRequest){
        userService.editRegionAndHobby(editRegionAndHobbyRequest);
        return ResponseEntity.ok(ResultResponse.of(EDIT_REGION_AND_HOBBY_SUCCESS));
    }

    @PostMapping("/photo")
    public ResponseEntity<ResultResponse> updateProfileImage(@RequestPart("profileImage") MultipartFile profileImage) {
        final String profileImageUrl = userService.updateProfileImage(profileImage);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_PROFILE_IMAGE_SUCCESS, profileImageUrl));
    }

    @GetMapping("/regions")
    public ResponseEntity<ResultResponse> getRegions(){
        final List<Region> regionList = Arrays.asList(Region.values());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_REGIONS_SUCCESS, regionList));
    }

    @DeleteMapping
    public ResponseEntity<ResultResponse> deleteUser(){
        userService.deleteUser();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_USER_SUCCESS));
    }
}

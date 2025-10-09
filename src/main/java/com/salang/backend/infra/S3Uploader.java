package com.salang.backend.infra;

import com.salang.backend.global.error.ErrorCode;
import com.salang.backend.global.error.exception.BadExtensionException;
import com.salang.backend.global.error.exception.BadFileNameException;
import com.salang.backend.global.error.exception.S3DeleteException;
import com.salang.backend.global.error.exception.S3UploadException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private static final List<String> ALLOWED_EXTENSIONS = List.of(".png", ".jpg", ".jpeg", ".pdf");
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public String updateProfileImage(MultipartFile multipartFile, String oldFileName) {
        // 기존 파일 삭제
        if (oldFileName != null && !oldFileName.isBlank()) {
            deleteFile(oldFileName);
        }

        // 새 파일 업로드
        return uploadFile(multipartFile);
    }

    private String uploadFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new BadFileNameException();
        }
        String fileName = createFileName(originalFilename);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(multipartFile.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, multipartFile.getSize()));
            return fileName; // S3에서 접근할 키 반환
        } catch (IOException | S3Exception e) {
            throw new S3UploadException();
        }
    }

    private void deleteFile(String fileName) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new S3DeleteException();
        }
    }

    private String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName){
        int idx = fileName.lastIndexOf(".");
        if (idx == -1) { // 파일 확장자가 없는 경우
            throw new BadExtensionException(ErrorCode.EMPTY_EXTENSION);
        }
        String ext = fileName.substring(idx).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new BadExtensionException(ErrorCode.BAD_EXTENSION);
        }
        return ext;
    }
}

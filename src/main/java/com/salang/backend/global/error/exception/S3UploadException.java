package com.salang.backend.global.error.exception;

import com.salang.backend.global.error.ErrorCode;

public class S3UploadException extends BusinessException {
    public S3UploadException() {
        super(ErrorCode.S3_UPLOAD_FAILED);
    }
}

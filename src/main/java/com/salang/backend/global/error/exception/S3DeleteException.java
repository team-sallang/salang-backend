package com.salang.backend.global.error.exception;

import com.salang.backend.global.error.ErrorCode;

public class S3DeleteException extends BusinessException {

    public S3DeleteException() {
        super(ErrorCode.S3_DELETE_FAILED);
    }
}

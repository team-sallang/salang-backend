package com.salang.backend.global.error.exception;

import com.salang.backend.global.error.ErrorCode;

public class BadFileNameException extends BusinessException {
    public BadFileNameException() {
        super(ErrorCode.BAD_FILE_NAME);
    }
}

package com.salang.backend.global.error.exception;

import com.salang.backend.global.error.ErrorCode;

public class BadExtensionException extends BusinessException {
    public BadExtensionException(ErrorCode errorCode) {
        super(errorCode);
    }
}

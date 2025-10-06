package com.salang.backend.domain.user.exception;

import com.salang.backend.global.error.ErrorCode;
import com.salang.backend.global.error.exception.BusinessException;

public class NicknameAlreadyExistException extends BusinessException {
    public NicknameAlreadyExistException() {
        super(ErrorCode.USER_NICKNAME_ALREADY_EXIST);
    }
}

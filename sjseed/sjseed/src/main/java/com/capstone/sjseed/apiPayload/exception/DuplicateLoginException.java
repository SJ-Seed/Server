package com.capstone.sjseed.apiPayload.exception;

public class DuplicateLoginException extends RuntimeException {
    public DuplicateLoginException(String loginId) {
        super("이미 사용 중인 아이디입니다.: " + loginId);
    }
}

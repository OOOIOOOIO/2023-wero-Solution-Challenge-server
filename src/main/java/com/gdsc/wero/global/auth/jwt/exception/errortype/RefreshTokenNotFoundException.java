package com.gdsc.wero.global.auth.jwt.exception.errortype;

public class RefreshTokenNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public RefreshTokenNotFoundException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}

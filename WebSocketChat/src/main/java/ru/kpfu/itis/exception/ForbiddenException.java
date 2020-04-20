package ru.kpfu.itis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Bad token")
@SuppressWarnings("serial")
public class ForbiddenException extends Exception {

    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}

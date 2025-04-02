package com.example.shinsekai.common.exception;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    /**
     * 발생한 예외 처리
     */

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<BaseResponseEntity<Void>> BaseError(BaseException e) {
        BaseResponseEntity<Void> response = new BaseResponseEntity<>(e.getStatus());
        log.error("BaseException -> {}({})", e.getStatus(), e.getStatus().getMessage(), e);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    /**
     * security 인증 에러
     * 아이디가 없거나 비밀번호가 틀린 경우 AuthenticationManager 에서 발생
     *
     * @return FAILED_TO_LOGIN 에러 response
     */
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<BaseResponseEntity<Void>> handleBadCredentialsException(BadCredentialsException e) {
        BaseResponseEntity<Void> response = new BaseResponseEntity<>(BaseResponseStatus.FAILED_TO_LOGIN);
        log.error("BadCredentialsException: ", e);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<BaseResponseEntity<Void>> RuntimeError(RuntimeException e) {
        BaseResponseEntity<Void> response = new BaseResponseEntity<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        log.error("RuntimeException: ", e);
        for (StackTraceElement s : e.getStackTrace()) {
            System.out.println(s);
        }
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponseEntity<Void>> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("ValidationException: {}", e.getMessage());

        BaseResponseEntity<Void> response = new BaseResponseEntity<>(BaseResponseStatus.INVALID_INPUT);
        return new ResponseEntity<>(response, response.httpStatus());
    }

}
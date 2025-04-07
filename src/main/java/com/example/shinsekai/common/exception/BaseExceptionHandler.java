package com.example.shinsekai.common.exception;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

        String errorCode = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        BaseResponseStatus status;

        try {
            status = BaseResponseStatus.valueOf(errorCode);
        } catch (IllegalArgumentException ex) {
            status = BaseResponseStatus.INVALID_INPUT; // 기본 fallback
        }

        BaseResponseEntity<Void> response = new BaseResponseEntity<>(status);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<BaseResponseEntity<Void>> handleJsonParseException(HttpMessageNotReadableException e) {
        log.warn("Json parsing error: {}", e.getMessage());

        // BaseException이 중첩돼 있는지 순회하면서 찾음
        Throwable cause = e.getCause();
        while (cause != null) {
            if (cause instanceof BaseException baseEx) {
                BaseResponseEntity<Void> response = new BaseResponseEntity<>(baseEx.getStatus());
                return new ResponseEntity<>(response, response.httpStatus());
            }

            // 특정 필드인 경우만 INVALID_BIRTH_FORMAT으로 반환
            if (e.getMessage() != null && e.getMessage().contains("LocalDate")) {
                BaseResponseEntity<Void> response = new BaseResponseEntity<>(BaseResponseStatus.INVALID_DATE_FORMAT);
                return new ResponseEntity<>(response, response.httpStatus());
            }else {
                cause = cause.getCause();  // 깊이 파고들기
            }
        }

        // BaseException이 아닌 경우는 일반 INVALID_INPUT으로 처리
        BaseResponseEntity<Void> response = new BaseResponseEntity<>(BaseResponseStatus.INVALID_INPUT);
        return new ResponseEntity<>(response, response.httpStatus());
    }



}
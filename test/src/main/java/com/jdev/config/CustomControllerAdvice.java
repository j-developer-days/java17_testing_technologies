package com.jdev.config;

import com.jdev.dto.response.ErrorType;
import com.jdev.dto.response.ResponseDto;
import com.jdev.utils.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j

@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> exceptionExceptionHandler(Exception e, HttpServletRequest httpServletRequest) {
        log.error("-----CustomControllerAdvice.exceptionExceptionHandler-----", e);
        return new ResponseEntity<>(ResponseDto.error(ErrorType.COMMON_SERVER_ERROR),
                HttpUtils.fillRequiredHeaders(httpServletRequest),
                INTERNAL_SERVER_ERROR);
    }

}

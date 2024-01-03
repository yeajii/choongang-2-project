package com.oracle.projectGo.utils;

import com.oracle.projectGo.utils.error.BusinessException;
import com.oracle.projectGo.utils.error.DatabaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException e) {
        logError(e, "BusinessException");
        String errorMessage = String.format("{\"message\": \"비즈니스 로직 처리 중 오류가 발생했습니다.\", \"error\": \"%s\"}", e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException e) {
        logError(e, "DatabaseException");
        String errorMessage = String.format("{\"message\": \"데이터 액세스 중 오류가 발생했습니다.\", \"error\": \"%s\"}", e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        String error = e.getBindingResult().getFieldError().getDefaultMessage();
        logError(e, "MethodArgumentNotValidException");
        String errorMessage = String.format("{\"message\": \"메서드 인수 오류가 발생했습니다..\", \"error\": \"%s\"}", error);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<String> handleDatabaseException(PersistenceException e) {
        logError(e, "PersistenceException");
        String errorMessage = String.format("{\"message\": \"DB오류 발생.\", \"error\": \"%s\"}", e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logError(e, "Exception");
        String errorMessage = String.format("{\"message\": \"알수 없는 오류가 발생했습니다.\", \"error\": \"%s\"}", e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(Exception e, String errorType) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace.length > 0) {
            StackTraceElement element = trace[0];
            log.error("[{}]{}:{}:{}:{}", getTransactionId(), errorType, element.getClass().getSimpleName(), element.getMethodName(), e.getMessage());
        } else {
            log.error("[{}]{}:{}", getTransactionId(), errorType, e.getMessage());
        }
    }

    private UUID getTransactionId() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (attr.getRequest().getAttribute("transactionId") == null) {
            UUID transactionId = UUID.randomUUID();
            attr.getRequest().setAttribute("transactionId", transactionId);
        }
        return (UUID) attr.getRequest().getAttribute("transactionId");
    }
}
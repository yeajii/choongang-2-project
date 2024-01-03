package com.oracle.projectGo.utils.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.oracle.projectGo.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        UUID transactionId = getTransactionId();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info("[{}]{}:{}:{}", transactionId, className, methodName, "start");

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;
        log.info("[{}]{}:{}:{}ms", transactionId, className, methodName, executionTime);

        return proceed;
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
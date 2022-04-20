package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // 공통적인 작업을 구현하기 위한 용도
    // 패키지가 com.example.controller인 컨트롤러는 모두 수행
    @Around("execution(* com.example.controller.*Controller.*(..)) or execution(* com.example.mapper.*Mapper.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint)
            throws Throwable {

        // 현재시간 보관
        long start = System.currentTimeMillis();

        // 수행되는 클래스명
        String className = joinPoint.getSignature().getDeclaringTypeName();

        String type = "";

        // 파일명이 Controller 포함일 경우
        if (className.contains("Controller") == true) {
            type = "Controller => ";
        }
        // 파일명이 Mapper 포함일 경우
        else if (className.contains("Mapper") == true) {
            type = "Mapper => ";
        }
        // 파일명이 Service 포함일 경우
        else if (className.contains("Service") == true) {
            type = "Service => ";
        }

        // 끝나는 시간
        long end = System.currentTimeMillis();

        // 수행되는 메소드 명
        String methodName = joinPoint.getSignature().getName();
        logger.info(type + className + " - > " + methodName);

        // 소요된 시간
        logger.info("execute time: " + (end - start));

        return joinPoint.proceed();
    }
}

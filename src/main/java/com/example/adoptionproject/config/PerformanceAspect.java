package com.example.adoptionproject.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceAspect {
    @Around("execution(* com.example.adoptionproject.services.*.*(..))")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().getSimpleName();

        long start = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            log.info("{}.{}() executed in {} ms", className, methodName, elapsedTime);
            return result;
        } catch (Exception e) {
            long elapsedTime = System.currentTimeMillis() - start;
            log.error("{}.{}() failed after {} ms", className, methodName, elapsedTime, e);
            throw e;
        }
    }
}
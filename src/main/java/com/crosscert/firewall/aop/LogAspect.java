package com.crosscert.firewall.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
public class LogAspect {

    @Before("@annotation(com.crosscert.firewall.annotation.LogTrace)")
    public void doTrace(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        log.info("[trace] {} args={}", joinPoint.getSignature(), args);
    }

    @Before("execution(* com.crosscert.firewall.controller.*.*(..))")
    public void doRequestTrace(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        log.info("[trace] Request {} args={}", joinPoint.getSignature(), args);
    }

    @AfterReturning("execution(* com.crosscert.firewall.controller.*.*(..))")
    public void deResponseTrace(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        log.info("[trace] Response {} args={}", joinPoint.getSignature(), args);
    }
}

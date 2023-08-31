package org.mjjaen.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class BookServiceAspect {
    @Before("execution(* org.mjjaen.aop.service.impl.*.*(..))")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        log.info("Before advice logic");
        log.info("Method name: " + joinPoint.getSignature().getName());
    }

    @AfterReturning("bookServiceMethods()")
    public void afterReturningMethodExecution(JoinPoint joinPoint) {
        log.info("After returning advice logic");
        log.info("Method name: " + joinPoint.getSignature().getName());
    }

    //This advice has been individually without using the general pointcut definition below
    @AfterThrowing(pointcut = "execution(* org.mjjaen.aop.service.impl.*.*(..))", throwing = "exception")
    public void afterMethodThrowsException(JoinPoint joinPoint, Exception exception) {
        log.info("After throwing advice logic");
        log.info("Method name: " + joinPoint.getSignature().getName());
        log.info("Exception thrown: " + exception.toString());
    }

    @After("bookServiceMethods()")
    public void afterMethodExecution(JoinPoint joinPoint) {
        log.info("After advice logic");
        log.info("Method name: " + joinPoint.getSignature().getName());
    }

    //Define pointcut to be used in different advices.
    @Pointcut("execution(* org.mjjaen.aop.service.impl.*.*(..))")
    public void bookServiceMethods() {}

}

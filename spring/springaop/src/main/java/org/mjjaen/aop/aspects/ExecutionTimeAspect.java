package org.mjjaen.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class ExecutionTimeAspect {
    @Around("@annotation(org.mjjaen.aop.annotation.LogExecutionTime)")
    public Object aroundMethodExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?>[] q = method.getParameterTypes();
        var wrapper = new Object(){ String parameterTypes = ""; };
        Arrays.asList(q).stream().forEach(e -> {
            wrapper.parameterTypes = wrapper.parameterTypes.concat(e.getName()).concat(",");
        });
        // Before logic
        Instant start = Instant.now();
        //Method execution
        Object result = proceedingJoinPoint.proceed(); // Execute the join point
        // After logic
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        log.info("Method " + method.getName() + "(" + wrapper.parameterTypes + ") execution time: " + timeElapsed);
        return result;
    }
}

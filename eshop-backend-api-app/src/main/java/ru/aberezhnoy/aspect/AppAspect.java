package ru.aberezhnoy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppAspect {

    private static final Logger logger = LoggerFactory.getLogger(AppAspect.class);

    @Pointcut("execution(* ru.aberezhnoy.persist..*.*(..))")
    private void findMethods() {
    }

    @Before("findMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Call of {}", joinPoint);
    }

    @Around("@annotation(ru.aberezhnoy.aspect.TrackTime)")
    public Object trackTime(ProceedingJoinPoint jointPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = jointPoint.proceed();

        logger.info("Time taken by {} is {} ms", jointPoint, System.currentTimeMillis() - start);

        return result;
    }
}

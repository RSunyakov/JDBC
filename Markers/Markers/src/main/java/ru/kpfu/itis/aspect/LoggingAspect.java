package ru.kpfu.itis.aspect;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {
    private final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* *(..))", throwing = "ex")
    public void logAllThrowingMethods(Exception ex) throws Throwable {
        logger.warn("EXCEPTION: " + ex);
    }
}

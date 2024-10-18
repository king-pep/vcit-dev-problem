package com.vcitdevproblem.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging the execution of service and controller methods.
 * This logging aspect tracks method entry, successful execution, and exceptions.
 */
@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Logs the entry of any method in the service layer before the method execution.
     *
     * @param joinPoint provides the method signature and arguments
     */
    @Before("execution(* com.vcitdevproblem.service..*(..))")
    public void logBeforeServiceMethodExecution(JoinPoint joinPoint) {
        logger.info("Service - Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    /**
     * Logs the successful execution and return value of any method in the service layer.
     *
     * @param joinPoint provides the method signature
     * @param result    the return value of the method execution
     */
    @AfterReturning(pointcut = "execution(* com.vcitdevproblem.service..*(..))", returning = "result")
    public void logAfterServiceMethodExecution(JoinPoint joinPoint, Object result) {
        logger.info("Service - Method {} executed successfully. Return value: {}", joinPoint.getSignature().toShortString(), result);
    }

    /**
     * Logs any exception thrown by methods in the service layer.
     *
     * @param joinPoint provides the method signature
     * @param ex        the exception thrown by the method
     */
    @AfterThrowing(pointcut = "execution(* com.vcitdevproblem.service..*(..))", throwing = "ex")
    public void logServiceMethodExceptions(JoinPoint joinPoint, Throwable ex) {
        logger.error("Service - Method {} threw an exception. Exception: {}", joinPoint.getSignature().toShortString(), ex.getMessage(), ex);
    }

    /**
     * Logs the entry of any method in the controller layer before the method execution.
     *
     * @param joinPoint provides the method signature and arguments
     */
    @Before("execution(* com.vcitdevproblem.web.rest..*(..))")
    public void logBeforeControllerMethodExecution(JoinPoint joinPoint) {
        logger.info("Controller - Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    /**
     * Logs the successful execution and return value of any method in the controller layer.
     *
     * @param joinPoint provides the method signature
     * @param result    the return value of the method execution
     */
    @AfterReturning(pointcut = "execution(* com.vcitdevproblem.web.rest..*(..))", returning = "result")
    public void logAfterControllerMethodExecution(JoinPoint joinPoint, Object result) {
        logger.info("Controller - Method {} executed successfully. Return value: {}", joinPoint.getSignature().toShortString(), result);
    }

    /**
     * Logs any exception thrown by methods in the controller layer.
     *
     * @param joinPoint provides the method signature
     * @param ex        the exception thrown by the method
     */
    @AfterThrowing(pointcut = "execution(* com.vcitdevproblem.web.rest..*(..))", throwing = "ex")
    public void logControllerMethodExceptions(JoinPoint joinPoint, Throwable ex) {
        logger.error("Controller - Method {} threw an exception. Exception: {}", joinPoint.getSignature().toShortString(), ex.getMessage(), ex);
    }
}

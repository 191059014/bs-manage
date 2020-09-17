package com.hb.bsmanage.web.aspect;

import com.google.common.base.Stopwatch;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * controller日志打印
 *
 * @version v0.1, 2020/9/4 10:34, create by huangbiao.
 */
@Aspect
@Component
public class ControllerLogAspect {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLogAspect.class);

    /**
     * controller层统一日志打印
     *
     * @param joinPoint 参数
     * @return Object
     */
    @Around(value = "execution(* com.hb.bsmanage.web.controller..*.*(..)) && !execution(* com.hb.bsmanage.web.controller.BaseController.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String baseLog = "[" + className + "-" + methodName + "]";
        LOGGER.info("{}入参={}", baseLog, args);
        Object proceed = joinPoint.proceed(args);
        LOGGER.info("{}出参={}, 耗时={}秒", baseLog, proceed, stopwatch.elapsed(TimeUnit.SECONDS));
        return proceed;
    }

}
    
package com.abkm.mall.demo.security.aspect;

import com.abkm.mall.demo.security.annotation.CacheException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * description: RedisCacheAspect <br>
 * date: 2020/9/25 0:46 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
@Aspect
@Component
@Order(2)
public class RedisCacheAspect {
    private static Logger LOGGER = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Pointcut("execution(public * com.abkm.mall.demo.module.ums.service.*CacheService.*(..))")
    public void cacheAspect() {

    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            if (method.isAnnotationPresent(CacheException.class)) {
                throw throwable;
            }else {
                LOGGER.error(throwable.getMessage());
            }
        }
        return result;
    }
}

package com.db.spli.aop;

import com.db.spli.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author lvxuan
 * @description 指定方法所操作数据源的切面类
 */
@Slf4j
@Aspect
@Component
public class DssAspect {

    @Around("@annotation(com.db.spli.aop.Dss)")
    public Object setDataSource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        boolean clear = true;
        try {
            Method method = this.getMethod(proceedingJoinPoint);
            Dss annotation = method.getAnnotation(Dss.class);
            clear = annotation.clear();
            DataSourceContextHolder.set(annotation.value().getDataSourceName());
            log.info("+++++++++++++++当前使用数据源：{}++++++++++++++", annotation.value().getDataSourceName());
            return proceedingJoinPoint.proceed();
        } finally {
            if (clear) {
                DataSourceContextHolder.clear();
            }
        }
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }

}

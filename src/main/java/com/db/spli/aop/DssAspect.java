package com.db.spli.aop;

import cn.hutool.core.util.RandomUtil;
import com.db.spli.config.DataSourceContextHolder;
import com.db.spli.enums.DataSourceEnum;
import com.db.spli.enums.SlaveDataEnum;
import com.db.spli.utils.EnumUtils;
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
            String dataSoure = DataSourceEnum.MASTER.getMessage();
            if (DataSourceEnum.SLAVE.getMessage().equals(annotation.value().getMessage())) {
                int length = SlaveDataEnum.values().length;
                int dataCode = RandomUtil.randomInt(length) + 1;
                dataSoure = EnumUtils.getMessageByCode(SlaveDataEnum.values(), dataCode);
            }
            DataSourceContextHolder.set(dataSoure);
            log.info("+++++++++++++++当前使用数据源：{}++++++++++++++", dataSoure);
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

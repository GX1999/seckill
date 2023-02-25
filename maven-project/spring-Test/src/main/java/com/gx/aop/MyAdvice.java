package com.gx.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component   //设置为bean使得spring可以管理
@Aspect    //告诉spring存在AOP
public class MyAdvice {
    @Pointcut("execution(boolean com.gx.service.*Service.openURL(*,*))")   //设置切入点
    private void pt() {
    }

    @Around("pt()")    //设置切面
    public Object method(ProceedingJoinPoint pjp) throws Throwable {    //通知方法
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i].getClass().equals(String.class)) {
                args[i] = args[i].toString().trim();
            }
        }
        Object ret = pjp.proceed(args);
        return ret;
    }
}

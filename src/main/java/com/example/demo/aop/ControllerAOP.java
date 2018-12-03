package com.example.demo.aop;

import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAOP {

    private static Logger logger = LoggerFactory.getLogger(ControllerAOP.class);

    /**
     * 获取controller方法
     * *(..)
     */
    @Pointcut("execution(public com.example.demo.util.Result com.example.demo.controller.*.*(..))")
    public void log(){

    }

    /**
     * 处理controller方法，切面
     * @param pjp
     * @return
     */
    @Around("log()")
    public Result handleControllerMethods(ProceedingJoinPoint pjp){
        long startTime = System.currentTimeMillis();
        Result result;
        try{
            result = (Result) pjp.proceed();
        } catch (Throwable throwable) {
            result = handleException(pjp, throwable);
        }
        return result;
    }

    /**
     * 处理controller异常
     * @param pjp
     * @param throwable
     * @return
     */
    private Result handleException(ProceedingJoinPoint pjp, Throwable throwable){
        if(throwable instanceof MyException){
            MyException e = (MyException) throwable;
            return new Result(e.getCode(), null, e.getMessage());
        }else{
            logger.error(throwable.toString());
            throwable.printStackTrace();
            return new Result(-1, null, "系统异常");
        }
    }


}

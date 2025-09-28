package com.luv2code.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

    // this is where we add all of our related advices for logging

    // let's start with an @Before advice
    @Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..))")
    public void forDaoPackage() {}

    // pointcut expression (param wildcard - .. match any number of arguments)
    @Before("forDaoPackage()")
    public void beforeAddAccountAdvice() {
        System.out.println("\n======>>> Executing @Before advice on addAccount() ");
    }

    // add another advice ... reuse point declaration
    @Before("forDaoPackage()")
    public void performApiAnalytics() {
        System.out.println("\n======>>> Performing API analytics");
    }

}

package org.aidan.ssm.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {

    @Pointcut(
            "!@annotation(org.aidan.ssm.config.Master) " +
                    "&& " +
                    "(" +
                    "execution(* org.aidan.ssm.service..*.select*(..)) " +
                    "|| execution(* org.aidan.ssm.service..*.get*(..))" +
                    ")"
    )
    public void readPointcut() {

    }

    @Pointcut(
            "@annotation(org.aidan.ssm.config.Master) " +
                    "|| execution(* org.aidan.ssm.service..*.insert*(..)) " +
                    "|| execution(* org.aidan.ssm.service..*.add*(..)) " +
                    "|| execution(* org.aidan.ssm.service..*.save*(..)) " +
                    "|| execution(* com.baomidou.mybatisplus.extension.service..*.save*(..)) " +
                    "|| execution(* org.aidan.ssm.service..*.update*(..)) " +
                    "|| execution(* org.aidan.ssm.service..*.edit*(..)) " +
                    "|| execution(* org.aidan.ssm.service..*.delete*(..)) " +
                    "|| execution(* org.aidan.ssm.service..*.remove*(..))"
    )
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }


    /**
     * 另一种写法：if...else...  判断哪些需要读从数据库，其余的走主数据库
     */
//    @Before("execution(* org.aidan.ssm.service.impl.*.*(..))")
//    public void before(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//
//        if (StringUtils.startsWithAny(methodName, "get", "select", "find")) {
//            DBContextHolder.slave();
//        }else {
//            DBContextHolder.master();
//        }
//    }
}
package com.gx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration   //设置当前类为配置类
@ComponentScan("com.gx")
@EnableAspectJAutoProxy(proxyTargetClass = true)   //设置spring使用注解实现AOP
@EnableTransactionManagement
public class SpringConfig {
}

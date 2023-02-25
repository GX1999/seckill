package com.gx.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"com.gx.controller", "com.gx.config"})
@EnableWebMvc
public class SpringMvcConfig {
}

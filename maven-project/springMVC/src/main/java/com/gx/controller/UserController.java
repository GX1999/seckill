package com.gx.controller;

import com.gx.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    //MVC特定的定义bean
@RequestMapping("/user")   //设置前缀，防止不同类的请求方法名相同
public class UserController {
    @RequestMapping("/save")   //设置访问路径
    @ResponseBody
    public String save(User user) {    //括号内为参数传递(不区分get和Post)
        System.out.println("参数传递  name:" + user.getName() + " 年龄:" + user.getAge());
        return "{'module':'springmvc'}";
    }

    @RequestMapping("/page")   //设置访问路径
    public String page() {    //括号内为参数传递(不区分get和Post)
        return "/page.jsp";   //返回页面
    }

}

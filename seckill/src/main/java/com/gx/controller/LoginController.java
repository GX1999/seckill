package com.gx.controller;

import com.gx.service.IUserService;
import com.gx.vo.LoginVo;
import com.gx.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Slf4j  //输出日志log.XX
public class LoginController {

    @Autowired
    private IUserService iUserService;

    //跳转页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }


    //登录功能
    @RequestMapping("/doLogin")   //设置公共返回对象RespBean
    @ResponseBody  //返回json对象

    //@Valid注解，使得参数会自动校验（validation组件）
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        return iUserService.doLogin(loginVo, request, response);
    }
}

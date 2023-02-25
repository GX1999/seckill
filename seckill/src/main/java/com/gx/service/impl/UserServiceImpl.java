package com.gx.service.impl;

import com.gx.exception.GlobalException;
import com.gx.pojo.User;
import com.gx.mapper.UserMapper;
import com.gx.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gx.utils.CookieUtil;
import com.gx.utils.MD5Util;
import com.gx.utils.UUIDUtil;
import com.gx.utils.ValidatorUtil;
import com.gx.vo.LoginVo;
import com.gx.vo.RespBean;
import com.gx.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * <p>
 *  登录逻辑
 * </p>
 *
 * @author gx
 * @since 2022-12-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo,HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //判断手机号码和密码是否为空
//        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        //判断手机号是否符合正则表达式
//        if (!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //数据库查询比对
        User user = userMapper.selectById(mobile);
        if (user==null){
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR); //抛登录异常，可以显示在页面
        }
        //判断密码是否正确
        if (!MD5Util.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);  //抛登录异常
        }
        //生成Cookie
        String ticket = UUIDUtil.uuid();  //生成UUID
        redisTemplate.opsForValue().set("user:" + ticket, user);  //用户信息存入redis
        //request.getSession().setAttribute(ticket,user);
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        return RespBean.success(ticket);
    }

    //根据cookie获取用户
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response){
        if (StringUtils.isEmpty(userTicket)){
            return null;
        }
        User user = (User)redisTemplate.opsForValue().get("user:" + userTicket);
        if (user!=null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }

    //更新密码（保持redis数据和数据库中的一致：更新数据库时清除缓存）
    @Override
    public RespBean updatePassword(String userTicket,String password, HttpServletRequest request, HttpServletResponse response) {
        User user = getUserByCookie(userTicket,request,response);
        if (user==null){
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password,user.getSalt()));
        int result = userMapper.updateById(user);
        if (result==1){
            redisTemplate.delete("user:"+userTicket);  //更新数据库时清除缓存
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }
}

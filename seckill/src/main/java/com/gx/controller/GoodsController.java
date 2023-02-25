package com.gx.controller;


import com.gx.pojo.User;
import com.gx.service.IGoodsService;
import com.gx.service.IUserService;
import com.gx.vo.DetailVo;
import com.gx.vo.GoodsVo;
import com.gx.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//商品页面
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;   //页面缓存在redis
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    //跳转到商品页面
    public String toList(Model model, User user,
                         HttpServletRequest request, HttpServletResponse response){
        //使用参数解析器替代下面代码
//        if (StringUtils.isEmpty(ticket)){
//            return "login";
//        }
//        //User user = (User)session.getAttribute(ticket);
//        User user = userService.getUserByCookie(ticket,request,response);
//        if (null==user){
//            return "login";
//        }
        //页面缓存
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String)valueOperations.get("goodsList");
        if (StringUtils.hasText(html)){
            return html;
        }
        model.addAttribute("user",user);  //往前端页面传数据
        model.addAttribute("goodsList",goodsService.findGoodsVo());

//        如果为空，则手动渲染页面并存入redis，并返回
//        1. context:上下文,用来保存模型数据，当模板引擎渲染时，可以从Context上下文获取数据用于渲染，当与SpringBoot结合使用时，放入Model的数据会被处理到Context，作为模板渲染的数据使用。
//        2. TemplateResolver :模板解析器：用来读取模板相关的配置
//        3. TemplateEngine :模板引擎：用来解析模板的引擎，需要使用到上下文、模板解析器 templateEngine.process("模板名", context);
        WebContext context = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList",context);
        if (StringUtils.hasText(html)){
            valueOperations.set("goodsList",html,60, TimeUnit.SECONDS);  //失效时间为60s
        }
        return html;
    }





    @RequestMapping(value = "/toDetail2/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail2(Model model, User user, @PathVariable Long goodsId,
                           HttpServletRequest request, HttpServletResponse response){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsDetail:"+goodsId);
        if (StringUtils.hasText(html)){
            return html;
        }

        model.addAttribute("user",user);  //往前台传数据
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        int remainSeconds = 0;
        if (nowDate.before(startDate)){  //秒杀未开始,显示倒计时
            remainSeconds = (int)(startDate.getTime()-nowDate.getTime())/1000;
        }else if (nowDate.after(endDate)){
            secKillStatus = 2;  //秒杀已经结束
            remainSeconds = -1;
        }else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("secKillStatus",secKillStatus);
        model.addAttribute("goods",goodsVo);

        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail",context);
        if (StringUtils.hasText(html)){
            valueOperations.set("goodsDetail:"+goodsId,html,60, TimeUnit.SECONDS);
        }
        return html;
    }



    //页面静态化（前后端分离）处理后的toDetail
    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId){
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        int remainSeconds = 0;
        if (nowDate.before(startDate)){  //秒杀未开始,显示倒计时
            remainSeconds = (int)(startDate.getTime()-nowDate.getTime())/1000;
        }else if (nowDate.after(endDate)){
            secKillStatus = 2;  //秒杀已经结束
            remainSeconds = -1;
        }else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSecKillStatus(secKillStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return RespBean.success(detailVo);
    }
}

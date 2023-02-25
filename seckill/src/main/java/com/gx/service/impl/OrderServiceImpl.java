package com.gx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gx.exception.GlobalException;
import com.gx.mapper.OrderMapper;
import com.gx.pojo.Order;
import com.gx.pojo.SeckillGoods;
import com.gx.pojo.SeckillOrder;
import com.gx.pojo.User;
import com.gx.service.IGoodsService;
import com.gx.service.IOrderService;
import com.gx.service.ISeckillGoodsService;
import com.gx.service.ISeckillOrderService;
import com.gx.utils.MD5Util;
import com.gx.utils.UUIDUtil;
import com.gx.vo.GoodsVo;
import com.gx.vo.OrderDetailVo;
import com.gx.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gx
 * @since 2023-02-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public Order seckill(User user, GoodsVo goods) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //秒杀商品表
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        //seckillGoodsService.updateById(seckillGoods);
        //把商品id为getID同时该商品库存>0的商品进行update(count-1),防止超卖
        boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().setSql("stock_count="+
                "stock_count-1").eq("goods_id",goods.getId()).gt("stock_count",0));
        if (seckillGoods.getStockCount()<1){
            valueOperations.set("isStockEmpty:"+goods.getId(),"0");
            return null;
        }


        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0l);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);

        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        //放入redis中，减少频繁对数据库的请求
        redisTemplate.opsForValue().set("order:"+user.getId()+":"+goods.getId(),seckillOrder);

        return order;

    }

    //订单详情
    @Override
    public OrderDetailVo detail(Long orderId){
        if (orderId==null){
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo detail = new OrderDetailVo();
        detail.setOrder(order);
        detail.setGoodsVo(goodsVo);
        return detail;
    }

    //获取秒杀地址
    @Override
    public String createPath(User user, Long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisTemplate.opsForValue().set("seckillPath:"+user.getId()+":"+goodsId,str,60, TimeUnit.SECONDS);
        return str;
    }

    //校验秒杀地址
    @Override
    public boolean chechPath(User user, Long goodsId,String path) {
        if (user==null||goodsId<0|| !StringUtils.hasText(path)){
            return false;
        }
        String redisPath = (String)redisTemplate.opsForValue().get("seckillPath:"+user.getId()+":"+goodsId);
        return path.equals(redisPath);
    }

    //校验验证码
    @Override
    public boolean checkCaptcha(User user, Long goodsId, String captcha) {
        if (user==null||goodsId<0||!StringUtils.hasText(captcha)){
            return false;
        }
        String redisCaptcha = (String)redisTemplate.opsForValue().get("captcha:"+user.getId()+":"+goodsId);
        return captcha.equals(redisCaptcha);
    }

}

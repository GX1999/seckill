package com.gx.rabbitmq;

import com.gx.pojo.SeckillMessage;
import com.gx.pojo.SeckillOrder;
import com.gx.pojo.User;
import com.gx.service.IGoodsService;
import com.gx.service.IOrderService;
import com.gx.utils.JsonUtil;
import com.gx.vo.GoodsVo;
import com.gx.vo.RespBean;
import com.gx.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

//消息接收者
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;

    //下单操作
    @RabbitListener(queues = "seckillQueue")
    public void receive(String message){
        log.info("接收的消息："+message);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(message,SeckillMessage.class);
        Long goodId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodId);
        if (goodsVo.getStockCount()<1){
            return;
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder)redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodId);
        if (seckillOrder!=null){
            return;
        }
        //下单操作
        orderService.seckill(user,goodsVo);
    }
}

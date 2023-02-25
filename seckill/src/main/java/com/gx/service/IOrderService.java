package com.gx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gx.pojo.Order;
import com.gx.pojo.User;
import com.gx.vo.GoodsVo;
import com.gx.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gx
 * @since 2023-02-14
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goods);

    OrderDetailVo detail(Long orderId);

    String createPath(User user, Long goodsId);

    boolean chechPath(User user, Long goodsId,String path);

    boolean checkCaptcha(User user, Long goodsId, String captcha);
}

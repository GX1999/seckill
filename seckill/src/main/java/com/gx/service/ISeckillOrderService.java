package com.gx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gx.pojo.SeckillOrder;
import com.gx.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gx
 * @since 2023-02-14
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    Long getResult(User user, Long goodsId);
}

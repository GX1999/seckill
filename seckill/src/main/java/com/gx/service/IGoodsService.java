package com.gx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gx.pojo.Goods;
import com.gx.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gx
 * @since 2023-02-14
 */
public interface IGoodsService extends IService<Goods> {
    List<GoodsVo> findGoodsVo();  //获取商品列表

    GoodsVo findGoodsVoByGoodsId(Long goodsId);   //根据ID获取商品
}

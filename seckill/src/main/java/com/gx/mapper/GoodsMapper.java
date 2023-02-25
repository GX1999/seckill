package com.gx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gx.pojo.Goods;
import com.gx.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gx
 * @since 2023-02-14
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();   //使用xml文件实现查询

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}

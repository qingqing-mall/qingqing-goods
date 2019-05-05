package com.liaoyb.qingqing.goods.service.mapper;

import com.liaoyb.qingqing.goods.domain.*;
import com.liaoyb.qingqing.goods.service.dto.GoodsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Goods} and its DTO {@link GoodsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoodsMapper extends EntityMapper<GoodsDTO, Goods> {



    default Goods fromId(Long id) {
        if (id == null) {
            return null;
        }
        Goods goods = new Goods();
        goods.setId(id);
        return goods;
    }
}

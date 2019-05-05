package com.liaoyb.qingqing.goods.repository;

import com.liaoyb.qingqing.goods.domain.GoodsCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GoodsCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long> {

}

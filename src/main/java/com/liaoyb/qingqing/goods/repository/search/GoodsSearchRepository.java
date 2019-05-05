package com.liaoyb.qingqing.goods.repository.search;

import com.liaoyb.qingqing.goods.domain.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Goods} entity.
 */
public interface GoodsSearchRepository extends ElasticsearchRepository<Goods, Long> {
}

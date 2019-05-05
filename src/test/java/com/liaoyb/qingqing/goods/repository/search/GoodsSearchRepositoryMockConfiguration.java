package com.liaoyb.qingqing.goods.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link GoodsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GoodsSearchRepositoryMockConfiguration {

    @MockBean
    private GoodsSearchRepository mockGoodsSearchRepository;

}

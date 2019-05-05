package com.liaoyb.qingqing.goods.service;

import com.liaoyb.qingqing.goods.domain.GoodsCategory;
import com.liaoyb.qingqing.goods.repository.GoodsCategoryRepository;
import com.liaoyb.qingqing.goods.service.dto.GoodsCategoryDTO;
import com.liaoyb.qingqing.goods.service.mapper.GoodsCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link GoodsCategory}.
 */
@Service
@Transactional
public class GoodsCategoryService {

    private final Logger log = LoggerFactory.getLogger(GoodsCategoryService.class);

    private final GoodsCategoryRepository goodsCategoryRepository;

    private final GoodsCategoryMapper goodsCategoryMapper;

    public GoodsCategoryService(GoodsCategoryRepository goodsCategoryRepository, GoodsCategoryMapper goodsCategoryMapper) {
        this.goodsCategoryRepository = goodsCategoryRepository;
        this.goodsCategoryMapper = goodsCategoryMapper;
    }

    /**
     * Save a goodsCategory.
     *
     * @param goodsCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public GoodsCategoryDTO save(GoodsCategoryDTO goodsCategoryDTO) {
        log.debug("Request to save GoodsCategory : {}", goodsCategoryDTO);
        GoodsCategory goodsCategory = goodsCategoryMapper.toEntity(goodsCategoryDTO);
        goodsCategory = goodsCategoryRepository.save(goodsCategory);
        return goodsCategoryMapper.toDto(goodsCategory);
    }

    /**
     * Get all the goodsCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GoodsCategoryDTO> findAll() {
        log.debug("Request to get all GoodsCategories");
        return goodsCategoryRepository.findAll().stream()
            .map(goodsCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one goodsCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GoodsCategoryDTO> findOne(Long id) {
        log.debug("Request to get GoodsCategory : {}", id);
        return goodsCategoryRepository.findById(id)
            .map(goodsCategoryMapper::toDto);
    }

    /**
     * Delete the goodsCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GoodsCategory : {}", id);
        goodsCategoryRepository.deleteById(id);
    }
}

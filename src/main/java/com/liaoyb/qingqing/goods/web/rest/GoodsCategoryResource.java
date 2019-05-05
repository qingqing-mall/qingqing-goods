package com.liaoyb.qingqing.goods.web.rest;

import com.liaoyb.qingqing.goods.service.GoodsCategoryService;
import com.liaoyb.qingqing.goods.web.rest.errors.BadRequestAlertException;
import com.liaoyb.qingqing.goods.service.dto.GoodsCategoryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.liaoyb.qingqing.goods.domain.GoodsCategory}.
 */
@RestController
@RequestMapping("/api")
public class GoodsCategoryResource {

    private final Logger log = LoggerFactory.getLogger(GoodsCategoryResource.class);

    private static final String ENTITY_NAME = "goodsGoodsCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoodsCategoryService goodsCategoryService;

    public GoodsCategoryResource(GoodsCategoryService goodsCategoryService) {
        this.goodsCategoryService = goodsCategoryService;
    }

    /**
     * {@code POST  /goods-categories} : Create a new goodsCategory.
     *
     * @param goodsCategoryDTO the goodsCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goodsCategoryDTO, or with status {@code 400 (Bad Request)} if the goodsCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goods-categories")
    public ResponseEntity<GoodsCategoryDTO> createGoodsCategory(@Valid @RequestBody GoodsCategoryDTO goodsCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save GoodsCategory : {}", goodsCategoryDTO);
        if (goodsCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new goodsCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoodsCategoryDTO result = goodsCategoryService.save(goodsCategoryDTO);
        return ResponseEntity.created(new URI("/api/goods-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goods-categories} : Updates an existing goodsCategory.
     *
     * @param goodsCategoryDTO the goodsCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodsCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the goodsCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goodsCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goods-categories")
    public ResponseEntity<GoodsCategoryDTO> updateGoodsCategory(@Valid @RequestBody GoodsCategoryDTO goodsCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update GoodsCategory : {}", goodsCategoryDTO);
        if (goodsCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoodsCategoryDTO result = goodsCategoryService.save(goodsCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goodsCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /goods-categories} : get all the goodsCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goodsCategories in body.
     */
    @GetMapping("/goods-categories")
    public List<GoodsCategoryDTO> getAllGoodsCategories() {
        log.debug("REST request to get all GoodsCategories");
        return goodsCategoryService.findAll();
    }

    /**
     * {@code GET  /goods-categories/:id} : get the "id" goodsCategory.
     *
     * @param id the id of the goodsCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goodsCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goods-categories/{id}")
    public ResponseEntity<GoodsCategoryDTO> getGoodsCategory(@PathVariable Long id) {
        log.debug("REST request to get GoodsCategory : {}", id);
        Optional<GoodsCategoryDTO> goodsCategoryDTO = goodsCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goodsCategoryDTO);
    }

    /**
     * {@code DELETE  /goods-categories/:id} : delete the "id" goodsCategory.
     *
     * @param id the id of the goodsCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goods-categories/{id}")
    public ResponseEntity<Void> deleteGoodsCategory(@PathVariable Long id) {
        log.debug("REST request to delete GoodsCategory : {}", id);
        goodsCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

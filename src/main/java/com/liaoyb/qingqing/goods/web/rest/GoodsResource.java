package com.liaoyb.qingqing.goods.web.rest;

import com.liaoyb.qingqing.goods.service.GoodsService;
import com.liaoyb.qingqing.goods.web.rest.errors.BadRequestAlertException;
import com.liaoyb.qingqing.goods.service.dto.GoodsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
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
 * REST controller for managing {@link com.liaoyb.qingqing.goods.domain.Goods}.
 */
@RestController
@RequestMapping("/api")
public class GoodsResource {

    private final Logger log = LoggerFactory.getLogger(GoodsResource.class);

    private static final String ENTITY_NAME = "goodsGoods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoodsService goodsService;

    public GoodsResource(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * {@code POST  /goods} : Create a new goods.
     *
     * @param goodsDTO the goodsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goodsDTO, or with status {@code 400 (Bad Request)} if the goods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goods")
    public ResponseEntity<GoodsDTO> createGoods(@Valid @RequestBody GoodsDTO goodsDTO) throws URISyntaxException {
        log.debug("REST request to save Goods : {}", goodsDTO);
        if (goodsDTO.getId() != null) {
            throw new BadRequestAlertException("A new goods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoodsDTO result = goodsService.save(goodsDTO);
        return ResponseEntity.created(new URI("/api/goods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goods} : Updates an existing goods.
     *
     * @param goodsDTO the goodsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodsDTO,
     * or with status {@code 400 (Bad Request)} if the goodsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goodsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goods")
    public ResponseEntity<GoodsDTO> updateGoods(@Valid @RequestBody GoodsDTO goodsDTO) throws URISyntaxException {
        log.debug("REST request to update Goods : {}", goodsDTO);
        if (goodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoodsDTO result = goodsService.save(goodsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /goods} : get all the goods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goods in body.
     */
    @GetMapping("/goods")
    public ResponseEntity<List<GoodsDTO>> getAllGoods(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Goods");
        Page<GoodsDTO> page = goodsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /goods/:id} : get the "id" goods.
     *
     * @param id the id of the goodsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goodsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goods/{id}")
    public ResponseEntity<GoodsDTO> getGoods(@PathVariable Long id) {
        log.debug("REST request to get Goods : {}", id);
        Optional<GoodsDTO> goodsDTO = goodsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goodsDTO);
    }

    /**
     * {@code DELETE  /goods/:id} : delete the "id" goods.
     *
     * @param id the id of the goodsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goods/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long id) {
        log.debug("REST request to delete Goods : {}", id);
        goodsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/goods?query=:query} : search for the goods corresponding
     * to the query.
     *
     * @param query the query of the goods search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/goods")
    public ResponseEntity<List<GoodsDTO>> searchGoods(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Goods for query {}", query);
        Page<GoodsDTO> page = goodsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}

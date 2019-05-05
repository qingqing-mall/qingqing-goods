package com.liaoyb.qingqing.goods.web.rest;

import com.liaoyb.qingqing.goods.GoodsApp;
import com.liaoyb.qingqing.goods.config.SecurityBeanOverrideConfiguration;
import com.liaoyb.qingqing.goods.domain.Goods;
import com.liaoyb.qingqing.goods.repository.GoodsRepository;
import com.liaoyb.qingqing.goods.repository.search.GoodsSearchRepository;
import com.liaoyb.qingqing.goods.service.GoodsService;
import com.liaoyb.qingqing.goods.service.dto.GoodsDTO;
import com.liaoyb.qingqing.goods.service.mapper.GoodsMapper;
import com.liaoyb.qingqing.goods.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.liaoyb.qingqing.goods.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link GoodsResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, GoodsApp.class})
public class GoodsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PICTURE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ORIGINAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORIGINAL_PRICE = new BigDecimal(2);

    private static final String DEFAULT_GOODS_SN = "AAAAAAAAAA";
    private static final String UPDATED_GOODS_SN = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUBLISH_STATUS = 1;
    private static final Integer UPDATED_PUBLISH_STATUS = 2;

    private static final Integer DEFAULT_VERIFY_STATUS = 1;
    private static final Integer UPDATED_VERIFY_STATUS = 2;

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ALBUM_PICTURES = "AAAAAAAAAA";
    private static final String UPDATED_ALBUM_PICTURES = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL_HTML = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_HTML = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL_MOBILE_HTML = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_MOBILE_HTML = "BBBBBBBBBB";

    private static final Integer DEFAULT_GOODS_CATEGORY_ID = 1;
    private static final Integer UPDATED_GOODS_CATEGORY_ID = 2;

    private static final String DEFAULT_GOODS_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GOODS_CATEGORY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_LAST_MODIFIED_BY = 1L;
    private static final Long UPDATED_LAST_MODIFIED_BY = 2L;

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;

    /**
     * This repository is mocked in the com.liaoyb.qingqing.goods.repository.search test package.
     *
     * @see com.liaoyb.qingqing.goods.repository.search.GoodsSearchRepositoryMockConfiguration
     */
    @Autowired
    private GoodsSearchRepository mockGoodsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restGoodsMockMvc;

    private Goods goods;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GoodsResource goodsResource = new GoodsResource(goodsService);
        this.restGoodsMockMvc = MockMvcBuilders.standaloneSetup(goodsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goods createEntity(EntityManager em) {
        Goods goods = new Goods();
        goods.setName(DEFAULT_NAME);
        goods.setPicture(DEFAULT_PICTURE);
        goods.setPrice(DEFAULT_PRICE);
        goods.setOriginalPrice(DEFAULT_ORIGINAL_PRICE);
        goods.setGoodsSn(DEFAULT_GOODS_SN);
        goods.setPublishStatus(DEFAULT_PUBLISH_STATUS);
        goods.setVerifyStatus(DEFAULT_VERIFY_STATUS);
        goods.setStock(DEFAULT_STOCK);
        goods.setUnit(DEFAULT_UNIT);
        goods.setDescription(DEFAULT_DESCRIPTION);
        goods.setAlbumPictures(DEFAULT_ALBUM_PICTURES);
        goods.setDetailHtml(DEFAULT_DETAIL_HTML);
        goods.setDetailMobileHtml(DEFAULT_DETAIL_MOBILE_HTML);
        goods.setGoodsCategoryId(DEFAULT_GOODS_CATEGORY_ID);
        goods.setGoodsCategoryName(DEFAULT_GOODS_CATEGORY_NAME);
        goods.setCreatedBy(DEFAULT_CREATED_BY);
        goods.setCreatedDate(DEFAULT_CREATED_DATE);
        goods.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        goods.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return goods;
    }

    @BeforeEach
    public void initTest() {
        goods = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoods() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isCreated());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate + 1);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoods.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testGoods.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testGoods.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testGoods.getGoodsSn()).isEqualTo(DEFAULT_GOODS_SN);
        assertThat(testGoods.getPublishStatus()).isEqualTo(DEFAULT_PUBLISH_STATUS);
        assertThat(testGoods.getVerifyStatus()).isEqualTo(DEFAULT_VERIFY_STATUS);
        assertThat(testGoods.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testGoods.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testGoods.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGoods.getAlbumPictures()).isEqualTo(DEFAULT_ALBUM_PICTURES);
        assertThat(testGoods.getDetailHtml()).isEqualTo(DEFAULT_DETAIL_HTML);
        assertThat(testGoods.getDetailMobileHtml()).isEqualTo(DEFAULT_DETAIL_MOBILE_HTML);
        assertThat(testGoods.getGoodsCategoryId()).isEqualTo(DEFAULT_GOODS_CATEGORY_ID);
        assertThat(testGoods.getGoodsCategoryName()).isEqualTo(DEFAULT_GOODS_CATEGORY_NAME);
        assertThat(testGoods.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGoods.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testGoods.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testGoods.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);

        // Validate the Goods in Elasticsearch
        verify(mockGoodsSearchRepository, times(1)).save(testGoods);
    }

    @Test
    @Transactional
    public void createGoodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods with an existing ID
        goods.setId(1L);
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Goods in Elasticsearch
        verify(mockGoodsSearchRepository, times(0)).save(goods);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setName(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPictureIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setPicture(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setPrice(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOriginalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setOriginalPrice(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGoodsSnIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setGoodsSn(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublishStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setPublishStatus(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVerifyStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setVerifyStatus(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setStock(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setUnit(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setDescription(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAlbumPicturesIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setAlbumPictures(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetailHtmlIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setDetailHtml(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetailMobileHtmlIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setDetailMobileHtml(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGoodsCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setGoodsCategoryId(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGoodsCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setGoodsCategoryName(null);

        // Create the Goods, which fails.
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get all the goodsList
        restGoodsMockMvc.perform(get("/api/goods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(DEFAULT_PICTURE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].goodsSn").value(hasItem(DEFAULT_GOODS_SN.toString())))
            .andExpect(jsonPath("$.[*].publishStatus").value(hasItem(DEFAULT_PUBLISH_STATUS)))
            .andExpect(jsonPath("$.[*].verifyStatus").value(hasItem(DEFAULT_VERIFY_STATUS)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].albumPictures").value(hasItem(DEFAULT_ALBUM_PICTURES.toString())))
            .andExpect(jsonPath("$.[*].detailHtml").value(hasItem(DEFAULT_DETAIL_HTML.toString())))
            .andExpect(jsonPath("$.[*].detailMobileHtml").value(hasItem(DEFAULT_DETAIL_MOBILE_HTML.toString())))
            .andExpect(jsonPath("$.[*].goodsCategoryId").value(hasItem(DEFAULT_GOODS_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].goodsCategoryName").value(hasItem(DEFAULT_GOODS_CATEGORY_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", goods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goods.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.originalPrice").value(DEFAULT_ORIGINAL_PRICE.intValue()))
            .andExpect(jsonPath("$.goodsSn").value(DEFAULT_GOODS_SN.toString()))
            .andExpect(jsonPath("$.publishStatus").value(DEFAULT_PUBLISH_STATUS))
            .andExpect(jsonPath("$.verifyStatus").value(DEFAULT_VERIFY_STATUS))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.albumPictures").value(DEFAULT_ALBUM_PICTURES.toString()))
            .andExpect(jsonPath("$.detailHtml").value(DEFAULT_DETAIL_HTML.toString()))
            .andExpect(jsonPath("$.detailMobileHtml").value(DEFAULT_DETAIL_MOBILE_HTML.toString()))
            .andExpect(jsonPath("$.goodsCategoryId").value(DEFAULT_GOODS_CATEGORY_ID))
            .andExpect(jsonPath("$.goodsCategoryName").value(DEFAULT_GOODS_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGoods() throws Exception {
        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Update the goods
        Goods updatedGoods = goodsRepository.findById(goods.getId()).get();
        // Disconnect from session so that the updates on updatedGoods are not directly saved in db
        em.detach(updatedGoods);
        updatedGoods.setName(UPDATED_NAME);
        updatedGoods.setPicture(UPDATED_PICTURE);
        updatedGoods.setPrice(UPDATED_PRICE);
        updatedGoods.setOriginalPrice(UPDATED_ORIGINAL_PRICE);
        updatedGoods.setGoodsSn(UPDATED_GOODS_SN);
        updatedGoods.setPublishStatus(UPDATED_PUBLISH_STATUS);
        updatedGoods.setVerifyStatus(UPDATED_VERIFY_STATUS);
        updatedGoods.setStock(UPDATED_STOCK);
        updatedGoods.setUnit(UPDATED_UNIT);
        updatedGoods.setDescription(UPDATED_DESCRIPTION);
        updatedGoods.setAlbumPictures(UPDATED_ALBUM_PICTURES);
        updatedGoods.setDetailHtml(UPDATED_DETAIL_HTML);
        updatedGoods.setDetailMobileHtml(UPDATED_DETAIL_MOBILE_HTML);
        updatedGoods.setGoodsCategoryId(UPDATED_GOODS_CATEGORY_ID);
        updatedGoods.setGoodsCategoryName(UPDATED_GOODS_CATEGORY_NAME);
        updatedGoods.setCreatedBy(UPDATED_CREATED_BY);
        updatedGoods.setCreatedDate(UPDATED_CREATED_DATE);
        updatedGoods.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedGoods.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        GoodsDTO goodsDTO = goodsMapper.toDto(updatedGoods);

        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isOk());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoods.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testGoods.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testGoods.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testGoods.getGoodsSn()).isEqualTo(UPDATED_GOODS_SN);
        assertThat(testGoods.getPublishStatus()).isEqualTo(UPDATED_PUBLISH_STATUS);
        assertThat(testGoods.getVerifyStatus()).isEqualTo(UPDATED_VERIFY_STATUS);
        assertThat(testGoods.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testGoods.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testGoods.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGoods.getAlbumPictures()).isEqualTo(UPDATED_ALBUM_PICTURES);
        assertThat(testGoods.getDetailHtml()).isEqualTo(UPDATED_DETAIL_HTML);
        assertThat(testGoods.getDetailMobileHtml()).isEqualTo(UPDATED_DETAIL_MOBILE_HTML);
        assertThat(testGoods.getGoodsCategoryId()).isEqualTo(UPDATED_GOODS_CATEGORY_ID);
        assertThat(testGoods.getGoodsCategoryName()).isEqualTo(UPDATED_GOODS_CATEGORY_NAME);
        assertThat(testGoods.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGoods.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGoods.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGoods.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);

        // Validate the Goods in Elasticsearch
        verify(mockGoodsSearchRepository, times(1)).save(testGoods);
    }

    @Test
    @Transactional
    public void updateNonExistingGoods() throws Exception {
        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Create the Goods
        GoodsDTO goodsDTO = goodsMapper.toDto(goods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Goods in Elasticsearch
        verify(mockGoodsSearchRepository, times(0)).save(goods);
    }

    @Test
    @Transactional
    public void deleteGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        int databaseSizeBeforeDelete = goodsRepository.findAll().size();

        // Delete the goods
        restGoodsMockMvc.perform(delete("/api/goods/{id}", goods.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Goods in Elasticsearch
        verify(mockGoodsSearchRepository, times(1)).deleteById(goods.getId());
    }

    @Test
    @Transactional
    public void searchGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);
        when(mockGoodsSearchRepository.search(queryStringQuery("id:" + goods.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(goods), PageRequest.of(0, 1), 1));
        // Search the goods
        restGoodsMockMvc.perform(get("/api/_search/goods?query=id:" + goods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].goodsSn").value(hasItem(DEFAULT_GOODS_SN)))
            .andExpect(jsonPath("$.[*].publishStatus").value(hasItem(DEFAULT_PUBLISH_STATUS)))
            .andExpect(jsonPath("$.[*].verifyStatus").value(hasItem(DEFAULT_VERIFY_STATUS)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].albumPictures").value(hasItem(DEFAULT_ALBUM_PICTURES)))
            .andExpect(jsonPath("$.[*].detailHtml").value(hasItem(DEFAULT_DETAIL_HTML)))
            .andExpect(jsonPath("$.[*].detailMobileHtml").value(hasItem(DEFAULT_DETAIL_MOBILE_HTML)))
            .andExpect(jsonPath("$.[*].goodsCategoryId").value(hasItem(DEFAULT_GOODS_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].goodsCategoryName").value(hasItem(DEFAULT_GOODS_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Goods.class);
        Goods goods1 = new Goods();
        goods1.setId(1L);
        Goods goods2 = new Goods();
        goods2.setId(goods1.getId());
        assertThat(goods1).isEqualTo(goods2);
        goods2.setId(2L);
        assertThat(goods1).isNotEqualTo(goods2);
        goods1.setId(null);
        assertThat(goods1).isNotEqualTo(goods2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodsDTO.class);
        GoodsDTO goodsDTO1 = new GoodsDTO();
        goodsDTO1.setId(1L);
        GoodsDTO goodsDTO2 = new GoodsDTO();
        assertThat(goodsDTO1).isNotEqualTo(goodsDTO2);
        goodsDTO2.setId(goodsDTO1.getId());
        assertThat(goodsDTO1).isEqualTo(goodsDTO2);
        goodsDTO2.setId(2L);
        assertThat(goodsDTO1).isNotEqualTo(goodsDTO2);
        goodsDTO1.setId(null);
        assertThat(goodsDTO1).isNotEqualTo(goodsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(goodsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(goodsMapper.fromId(null)).isNull();
    }
}

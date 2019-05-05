package com.liaoyb.qingqing.goods.web.rest;

import com.liaoyb.qingqing.goods.GoodsApp;
import com.liaoyb.qingqing.goods.config.SecurityBeanOverrideConfiguration;
import com.liaoyb.qingqing.goods.domain.GoodsCategory;
import com.liaoyb.qingqing.goods.repository.GoodsCategoryRepository;
import com.liaoyb.qingqing.goods.service.GoodsCategoryService;
import com.liaoyb.qingqing.goods.service.dto.GoodsCategoryDTO;
import com.liaoyb.qingqing.goods.service.mapper.GoodsCategoryMapper;
import com.liaoyb.qingqing.goods.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.liaoyb.qingqing.goods.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@Link GoodsCategoryResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, GoodsApp.class})
public class GoodsCategoryResourceIT {

    private static final Integer DEFAULT_PARENT_ID = 1;
    private static final Integer UPDATED_PARENT_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Integer DEFAULT_SORT = 1;
    private static final Integer UPDATED_SORT = 2;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_LAST_MODIFIED_BY = 1L;
    private static final Long UPDATED_LAST_MODIFIED_BY = 2L;

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

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

    private MockMvc restGoodsCategoryMockMvc;

    private GoodsCategory goodsCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GoodsCategoryResource goodsCategoryResource = new GoodsCategoryResource(goodsCategoryService);
        this.restGoodsCategoryMockMvc = MockMvcBuilders.standaloneSetup(goodsCategoryResource)
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
    public static GoodsCategory createEntity(EntityManager em) {
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setParentId(DEFAULT_PARENT_ID);
        goodsCategory.setName(DEFAULT_NAME);
        goodsCategory.setLevel(DEFAULT_LEVEL);
        goodsCategory.setSort(DEFAULT_SORT);
        goodsCategory.setIcon(DEFAULT_ICON);
        goodsCategory.setDescription(DEFAULT_DESCRIPTION);
        goodsCategory.setCreatedBy(DEFAULT_CREATED_BY);
        goodsCategory.setCreatedDate(DEFAULT_CREATED_DATE);
        goodsCategory.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        goodsCategory.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return goodsCategory;
    }

    @BeforeEach
    public void initTest() {
        goodsCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoodsCategory() throws Exception {
        int databaseSizeBeforeCreate = goodsCategoryRepository.findAll().size();

        // Create the GoodsCategory
        GoodsCategoryDTO goodsCategoryDTO = goodsCategoryMapper.toDto(goodsCategory);
        restGoodsCategoryMockMvc.perform(post("/api/goods-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the GoodsCategory in the database
        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        GoodsCategory testGoodsCategory = goodsCategoryList.get(goodsCategoryList.size() - 1);
        assertThat(testGoodsCategory.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testGoodsCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoodsCategory.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testGoodsCategory.getSort()).isEqualTo(DEFAULT_SORT);
        assertThat(testGoodsCategory.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testGoodsCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGoodsCategory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGoodsCategory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testGoodsCategory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testGoodsCategory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createGoodsCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodsCategoryRepository.findAll().size();

        // Create the GoodsCategory with an existing ID
        goodsCategory.setId(1L);
        GoodsCategoryDTO goodsCategoryDTO = goodsCategoryMapper.toDto(goodsCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodsCategoryMockMvc.perform(post("/api/goods-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoodsCategory in the database
        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkParentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsCategoryRepository.findAll().size();
        // set the field null
        goodsCategory.setParentId(null);

        // Create the GoodsCategory, which fails.
        GoodsCategoryDTO goodsCategoryDTO = goodsCategoryMapper.toDto(goodsCategory);

        restGoodsCategoryMockMvc.perform(post("/api/goods-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsCategoryRepository.findAll().size();
        // set the field null
        goodsCategory.setName(null);

        // Create the GoodsCategory, which fails.
        GoodsCategoryDTO goodsCategoryDTO = goodsCategoryMapper.toDto(goodsCategory);

        restGoodsCategoryMockMvc.perform(post("/api/goods-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsCategoryRepository.findAll().size();
        // set the field null
        goodsCategory.setLevel(null);

        // Create the GoodsCategory, which fails.
        GoodsCategoryDTO goodsCategoryDTO = goodsCategoryMapper.toDto(goodsCategory);

        restGoodsCategoryMockMvc.perform(post("/api/goods-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsCategoryRepository.findAll().size();
        // set the field null
        goodsCategory.setSort(null);

        // Create the GoodsCategory, which fails.
        GoodsCategoryDTO goodsCategoryDTO = goodsCategoryMapper.toDto(goodsCategory);

        restGoodsCategoryMockMvc.perform(post("/api/goods-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIconIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsCategoryRepository.findAll().size();
        // set the field null
        goodsCategory.setIcon(null);

        // Create the GoodsCategory, which fails.
        GoodsCategoryDTO goodsCategoryDTO = goodsCategoryMapper.toDto(goodsCategory);

        restGoodsCategoryMockMvc.perform(post("/api/goods-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGoodsCategories() throws Exception {
        // Initialize the database
        goodsCategoryRepository.saveAndFlush(goodsCategory);

        // Get all the goodsCategoryList
        restGoodsCategoryMockMvc.perform(get("/api/goods-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goodsCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].sort").value(hasItem(DEFAULT_SORT)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getGoodsCategory() throws Exception {
        // Initialize the database
        goodsCategoryRepository.saveAndFlush(goodsCategory);

        // Get the goodsCategory
        restGoodsCategoryMockMvc.perform(get("/api/goods-categories/{id}", goodsCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goodsCategory.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.sort").value(DEFAULT_SORT))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGoodsCategory() throws Exception {
        // Get the goodsCategory
        restGoodsCategoryMockMvc.perform(get("/api/goods-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoodsCategory() throws Exception {
        // Initialize the database
        goodsCategoryRepository.saveAndFlush(goodsCategory);

        int databaseSizeBeforeUpdate = goodsCategoryRepository.findAll().size();

        // Update the goodsCategory
        GoodsCategory updatedGoodsCategory = goodsCategoryRepository.findById(goodsCategory.getId()).get();
        // Disconnect from session so that the updates on updatedGoodsCategory are not directly saved in db
        em.detach(updatedGoodsCategory);
        updatedGoodsCategory.setParentId(UPDATED_PARENT_ID);
        updatedGoodsCategory.setName(UPDATED_NAME);
        updatedGoodsCategory.setLevel(UPDATED_LEVEL);
        updatedGoodsCategory.setSort(UPDATED_SORT);
        updatedGoodsCategory.setIcon(UPDATED_ICON);
        updatedGoodsCategory.setDescription(UPDATED_DESCRIPTION);
        updatedGoodsCategory.setCreatedBy(UPDATED_CREATED_BY);
        updatedGoodsCategory.setCreatedDate(UPDATED_CREATED_DATE);
        updatedGoodsCategory.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedGoodsCategory.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        GoodsCategoryDTO goodsCategoryDTO = goodsCategoryMapper.toDto(updatedGoodsCategory);

        restGoodsCategoryMockMvc.perform(put("/api/goods-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the GoodsCategory in the database
        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeUpdate);
        GoodsCategory testGoodsCategory = goodsCategoryList.get(goodsCategoryList.size() - 1);
        assertThat(testGoodsCategory.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testGoodsCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoodsCategory.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testGoodsCategory.getSort()).isEqualTo(UPDATED_SORT);
        assertThat(testGoodsCategory.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testGoodsCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGoodsCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGoodsCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGoodsCategory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGoodsCategory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingGoodsCategory() throws Exception {
        int databaseSizeBeforeUpdate = goodsCategoryRepository.findAll().size();

        // Create the GoodsCategory
        GoodsCategoryDTO goodsCategoryDTO = goodsCategoryMapper.toDto(goodsCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodsCategoryMockMvc.perform(put("/api/goods-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoodsCategory in the database
        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoodsCategory() throws Exception {
        // Initialize the database
        goodsCategoryRepository.saveAndFlush(goodsCategory);

        int databaseSizeBeforeDelete = goodsCategoryRepository.findAll().size();

        // Delete the goodsCategory
        restGoodsCategoryMockMvc.perform(delete("/api/goods-categories/{id}", goodsCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<GoodsCategory> goodsCategoryList = goodsCategoryRepository.findAll();
        assertThat(goodsCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodsCategory.class);
        GoodsCategory goodsCategory1 = new GoodsCategory();
        goodsCategory1.setId(1L);
        GoodsCategory goodsCategory2 = new GoodsCategory();
        goodsCategory2.setId(goodsCategory1.getId());
        assertThat(goodsCategory1).isEqualTo(goodsCategory2);
        goodsCategory2.setId(2L);
        assertThat(goodsCategory1).isNotEqualTo(goodsCategory2);
        goodsCategory1.setId(null);
        assertThat(goodsCategory1).isNotEqualTo(goodsCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodsCategoryDTO.class);
        GoodsCategoryDTO goodsCategoryDTO1 = new GoodsCategoryDTO();
        goodsCategoryDTO1.setId(1L);
        GoodsCategoryDTO goodsCategoryDTO2 = new GoodsCategoryDTO();
        assertThat(goodsCategoryDTO1).isNotEqualTo(goodsCategoryDTO2);
        goodsCategoryDTO2.setId(goodsCategoryDTO1.getId());
        assertThat(goodsCategoryDTO1).isEqualTo(goodsCategoryDTO2);
        goodsCategoryDTO2.setId(2L);
        assertThat(goodsCategoryDTO1).isNotEqualTo(goodsCategoryDTO2);
        goodsCategoryDTO1.setId(null);
        assertThat(goodsCategoryDTO1).isNotEqualTo(goodsCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(goodsCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(goodsCategoryMapper.fromId(null)).isNull();
    }
}

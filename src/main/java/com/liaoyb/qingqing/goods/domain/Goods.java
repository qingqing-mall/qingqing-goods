package com.liaoyb.qingqing.goods.domain;


import lombok.Data;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * 商品
 */
@Data
@Entity
@Table(name = "qingqing_goods")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "goods")
public class Goods extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    /**
     * 商品名称
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 商品主图
     */
    @NotNull
    @Column(name = "picture", nullable = false)
    private String picture;

    /**
     * 价格
     */
    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    /**
     * 市场价
     */
    @NotNull
    @Column(name = "original_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal originalPrice;

    /**
     * 货号
     */
    @NotNull
    @Column(name = "goods_sn", nullable = false)
    private String goodsSn;

    /**
     * 上架状态：1->上架、2->下架
     */
    @NotNull
    @Column(name = "publish_status", nullable = false)
    private Integer publishStatus;

    /**
     * 审核状态：0->未审核；1->审核通过；2->审核不通过
     */
    @NotNull
    @Column(name = "verify_status", nullable = false)
    private Integer verifyStatus;

    /**
     * 库存
     */
    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    /**
     * 单位
     */
    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    /**
     * 商品描述
     */
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * 画册图片，连产品图片限制为5张，以逗号分割
     */
    @NotNull
    @Column(name = "album_pictures", nullable = false)
    private String albumPictures;

    /**
     * 产品详情网页内容
     */
    @NotNull
    @Column(name = "detail_html", nullable = false)
    private String detailHtml;

    /**
     * 移动端网页详情
     */
    @NotNull
    @Column(name = "detail_mobile_html", nullable = false)
    private String detailMobileHtml;

    /**
     * 商品分类id
     */
    @NotNull
    @Column(name = "goods_category_id", nullable = false)
    private Integer goodsCategoryId;

    /**
     * 商品分类名称
     */
    @NotNull
    @Column(name = "goods_category_name", nullable = false)
    private String goodsCategoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Goods)) {
            return false;
        }
        return id != null && id.equals(((Goods) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

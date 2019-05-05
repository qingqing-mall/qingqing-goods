package com.liaoyb.qingqing.goods.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.liaoyb.qingqing.goods.domain.Goods} entity.
 */
@ApiModel(description = "商品")
public class GoodsDTO implements Serializable {

    private Long id;

    /**
     * 商品名称
     */
    @NotNull
    @ApiModelProperty(value = "商品名称", required = true)
    private String name;

    /**
     * 商品主图
     */
    @NotNull
    @ApiModelProperty(value = "商品主图", required = true)
    private String picture;

    /**
     * 价格
     */
    @NotNull
    @ApiModelProperty(value = "价格", required = true)
    private BigDecimal price;

    /**
     * 市场价
     */
    @NotNull
    @ApiModelProperty(value = "市场价", required = true)
    private BigDecimal originalPrice;

    /**
     * 货号
     */
    @NotNull
    @ApiModelProperty(value = "货号", required = true)
    private String goodsSn;

    /**
     * 上架状态：1->上架、2->下架
     */
    @NotNull
    @ApiModelProperty(value = "上架状态：1->上架、2->下架", required = true)
    private Integer publishStatus;

    /**
     * 审核状态：0->未审核；1->审核通过；2->审核不通过
     */
    @NotNull
    @ApiModelProperty(value = "审核状态：0->未审核；1->审核通过；2->审核不通过", required = true)
    private Integer verifyStatus;

    /**
     * 库存
     */
    @NotNull
    @ApiModelProperty(value = "库存", required = true)
    private Integer stock;

    /**
     * 单位
     */
    @NotNull
    @ApiModelProperty(value = "单位", required = true)
    private String unit;

    /**
     * 商品描述
     */
    @NotNull
    @ApiModelProperty(value = "商品描述", required = true)
    private String description;

    /**
     * 画册图片，连产品图片限制为5张，以逗号分割
     */
    @NotNull
    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割", required = true)
    private String albumPictures;

    /**
     * 产品详情网页内容
     */
    @NotNull
    @ApiModelProperty(value = "产品详情网页内容", required = true)
    private String detailHtml;

    /**
     * 移动端网页详情
     */
    @NotNull
    @ApiModelProperty(value = "移动端网页详情", required = true)
    private String detailMobileHtml;

    /**
     * 商品分类id
     */
    @NotNull
    @ApiModelProperty(value = "商品分类id", required = true)
    private Integer goodsCategoryId;

    /**
     * 商品分类名称
     */
    @NotNull
    @ApiModelProperty(value = "商品分类名称", required = true)
    private String goodsCategoryName;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Instant createdDate;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private Long lastModifiedBy;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    private Instant lastModifiedDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlbumPictures() {
        return albumPictures;
    }

    public void setAlbumPictures(String albumPictures) {
        this.albumPictures = albumPictures;
    }

    public String getDetailHtml() {
        return detailHtml;
    }

    public void setDetailHtml(String detailHtml) {
        this.detailHtml = detailHtml;
    }

    public String getDetailMobileHtml() {
        return detailMobileHtml;
    }

    public void setDetailMobileHtml(String detailMobileHtml) {
        this.detailMobileHtml = detailMobileHtml;
    }

    public Integer getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Integer goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getGoodsCategoryName() {
        return goodsCategoryName;
    }

    public void setGoodsCategoryName(String goodsCategoryName) {
        this.goodsCategoryName = goodsCategoryName;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoodsDTO goodsDTO = (GoodsDTO) o;
        if (goodsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), goodsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GoodsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", picture='" + getPicture() + "'" +
            ", price=" + getPrice() +
            ", originalPrice=" + getOriginalPrice() +
            ", goodsSn='" + getGoodsSn() + "'" +
            ", publishStatus=" + getPublishStatus() +
            ", verifyStatus=" + getVerifyStatus() +
            ", stock=" + getStock() +
            ", unit='" + getUnit() + "'" +
            ", description='" + getDescription() + "'" +
            ", albumPictures='" + getAlbumPictures() + "'" +
            ", detailHtml='" + getDetailHtml() + "'" +
            ", detailMobileHtml='" + getDetailMobileHtml() + "'" +
            ", goodsCategoryId=" + getGoodsCategoryId() +
            ", goodsCategoryName='" + getGoodsCategoryName() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy=" + getLastModifiedBy() +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}

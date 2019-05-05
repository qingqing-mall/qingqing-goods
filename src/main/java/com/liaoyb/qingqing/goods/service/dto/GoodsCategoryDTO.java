package com.liaoyb.qingqing.goods.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.liaoyb.qingqing.goods.domain.GoodsCategory} entity.
 */
@ApiModel(description = "商品分类")
public class GoodsCategoryDTO implements Serializable {

    private Long id;

    /**
     * 上级分类的编号：0表示一级分类
     */
    @NotNull
    @ApiModelProperty(value = "上级分类的编号：0表示一级分类", required = true)
    private Integer parentId;

    /**
     * 分类名称
     */
    @NotNull
    @ApiModelProperty(value = "分类名称", required = true)
    private String name;

    /**
     * 分类级别：1->1级；2->2级
     */
    @NotNull
    @ApiModelProperty(value = "分类级别：1->1级；2->2级", required = true)
    private Integer level;

    /**
     * 排序
     */
    @NotNull
    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;

    /**
     * 图标
     */
    @NotNull
    @ApiModelProperty(value = "图标", required = true)
    private String icon;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        GoodsCategoryDTO goodsCategoryDTO = (GoodsCategoryDTO) o;
        if (goodsCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), goodsCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GoodsCategoryDTO{" +
            "id=" + getId() +
            ", parentId=" + getParentId() +
            ", name='" + getName() + "'" +
            ", level=" + getLevel() +
            ", sort=" + getSort() +
            ", icon='" + getIcon() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy=" + getLastModifiedBy() +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}

package com.liaoyb.qingqing.goods.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 商品分类
 */
@Data
@Entity
@Table(name = "qingqing_goods_category")
public class GoodsCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上级分类的编号：0表示一级分类
     */
    @NotNull
    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    /**
     * 分类名称
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 分类级别：1->1级；2->2级
     */
    @NotNull
    @Column(name = "jhi_level", nullable = false)
    private Integer level;

    /**
     * 排序
     */
    @NotNull
    @Column(name = "jhi_sort", nullable = false)
    private Integer sort;

    /**
     * 图标
     */
    @NotNull
    @Column(name = "icon", nullable = false)
    private String icon;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoodsCategory)) {
            return false;
        }
        return id != null && id.equals(((GoodsCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

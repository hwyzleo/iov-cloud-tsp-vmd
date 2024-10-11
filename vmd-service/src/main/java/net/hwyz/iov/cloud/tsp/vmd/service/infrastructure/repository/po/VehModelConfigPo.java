package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.hwyz.iov.cloud.tsp.framework.mysql.po.BasePo;

/**
 * <p>
 * 车辆车型配置表 数据对象
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-10-11
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_veh_model_config")
public class VehModelConfigPo extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 平台代码
     */
    @TableField("platform_code")
    private String platformCode;

    /**
     * 车系代码
     */
    @TableField("series_code")
    private String seriesCode;

    /**
     * 车型代码
     */
    @TableField("model_code")
    private String modelCode;

    /**
     * 车型配置代码：车型代码+外饰代码(2)+内饰代码(2)+车轮代码(2)+备胎代码(2)+智驾代码(2)
     */
    @TableField("code")
    private String code;

    /**
     * 车型配置名称
     */
    @TableField("name")
    private String name;

    /**
     * 车型配置英文名称
     */
    @TableField("name_en")
    private String nameEn;

    /**
     * 外饰代码
     */
    @TableField("exterior_code")
    private String exteriorCode;

    /**
     * 内饰代码
     */
    @TableField("interior_code")
    private String interiorCode;

    /**
     * 车轮代码
     */
    @TableField("wheel_code")
    private String wheelCode;

    /**
     * 备胎代码
     */
    @TableField("spare_tire_code")
    private String spareTireCode;

    /**
     * 智驾代码
     */
    @TableField("adas_code")
    private String adasCode;

    /**
     * 是否启用
     */
    @TableField("enable")
    private Boolean enable;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
}

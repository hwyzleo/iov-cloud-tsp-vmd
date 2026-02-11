package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import net.hwyz.iov.cloud.framework.mysql.po.BasePo;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 配置项表 数据对象
 * </p>
 *
 * @author hwyz_leo
 * @since 2026-02-11
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_config_item")
public class ConfigItemPo extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配置项编码
     */
    @TableField("code")
    private String code;

    /**
     * 配置项名称
     */
    @TableField("name")
    private String name;

    /**
     * 配置项类型
     */
    @TableField("type")
    private String type;

    /**
     * 配置项单位
     */
    @TableField("unit")
    private String unit;

    /**
     * 配置项枚举值
     */
    @TableField("enum_value")
    private String enumValue;

    /**
     * 是否车辆级配置
     */
    @TableField("vehicle")
    private Boolean vehicle;
}

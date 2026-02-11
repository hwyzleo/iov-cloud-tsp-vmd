package net.hwyz.iov.cloud.tsp.vmd.api.contract;

import lombok.*;
import net.hwyz.iov.cloud.framework.common.web.domain.BaseRequest;

import java.util.Date;

/**
 * 管理后台配置项
 *
 * @author hwyz_leo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConfigItemMpt extends BaseRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 配置项编码
     */
    private String code;

    /**
     * 配置项名称
     */
    private String name;

    /**
     * 配置项类型
     */
    private String type;

    /**
     * 配置项单位
     */
    private String unit;

    /**
     * 配置项枚举值
     */
    private String enumValue;

    /**
     * 是否车辆级配置
     */
    private Boolean vehicle;

    /**
     * 创建时间
     */
    private Date createTime;

}

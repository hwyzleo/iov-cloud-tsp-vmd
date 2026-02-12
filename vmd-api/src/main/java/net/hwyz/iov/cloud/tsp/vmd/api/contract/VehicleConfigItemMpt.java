package net.hwyz.iov.cloud.tsp.vmd.api.contract;

import lombok.*;
import net.hwyz.iov.cloud.framework.common.web.domain.BaseRequest;

import java.util.Date;

/**
 * 管理后台车辆配置项
 *
 * @author hwyz_leo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VehicleConfigItemMpt extends BaseRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 车架号
     */
    private String vin;

    /**
     * 配置版本
     */
    private String version;

    /**
     * 配置项代码
     */
    private String configItemCode;

    /**
     * 配置项值
     */
    private String configItemValue;

    /**
     * 源系统值
     */
    private String sourceValue;

    /**
     * 源系统
     */
    private String sourceSystem;

    /**
     * 创建时间
     */
    private Date createTime;

}

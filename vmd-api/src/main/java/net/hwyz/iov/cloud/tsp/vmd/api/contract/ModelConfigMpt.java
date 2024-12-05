package net.hwyz.iov.cloud.tsp.vmd.api.contract;

import lombok.*;
import net.hwyz.iov.cloud.framework.common.web.domain.BaseRequest;

import java.util.Date;

/**
 * 管理后台车型配置
 *
 * @author hwyz_leo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModelConfigMpt extends BaseRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 车辆平台代码
     */
    private String platformCode;

    /**
     * 车系代码
     */
    private String seriesCode;

    /**
     * 车型代码
     */
    private String modelCode;

    /**
     * 车型配置代码
     */
    private String code;

    /**
     * 车型配置名称
     */
    private String name;

    /**
     * 车系英文名称
     */
    private String nameEn;

    /**
     * 外饰代码
     */
    private String exteriorCode;

    /**
     * 内饰代码
     */
    private String interiorCode;

    /**
     * 车轮代码
     */
    private String wheelCode;

    /**
     * 备胎代码
     */
    private String spareTireCode;

    /**
     * 智驾代码
     */
    private String adasCode;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

}

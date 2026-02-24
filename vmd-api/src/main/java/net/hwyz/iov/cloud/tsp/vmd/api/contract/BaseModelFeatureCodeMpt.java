package net.hwyz.iov.cloud.tsp.vmd.api.contract;

import lombok.*;
import net.hwyz.iov.cloud.framework.common.web.domain.BaseRequest;

import java.util.Date;

/**
 * 管理后台基础车型特征值
 *
 * @author hwyz_leo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseModelFeatureCodeMpt extends BaseRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 基础车型代码
     */
    private String baseModelCode;

    /**
     * 特征族代码
     */
    private String familyCode;

    /**
     * 特征族名称
     */
    private String familyName;

    /**
     * 特征值代码
     */
    private String[] featureCode;

    /**
     * 特征值名称
     */
    private String[] featureName;

    /**
     * 特征值类型
     */
    private String featureType;

    /**
     * 创建时间
     */
    private Date createTime;

}

package net.hwyz.iov.cloud.tsp.vmd.api.contract;

import lombok.*;
import net.hwyz.iov.cloud.framework.common.web.domain.BaseRequest;

import java.util.Date;

/**
 * 管理后台车辆导入数据
 *
 * @author hwyz_leo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VehicleImportDataMpt extends BaseRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 批次号
     */
    private String batchNum;

    /**
     * 数据类型
     */
    private String type;

    /**
     * 数据版本
     */
    private String version;

    /**
     * MES车辆数据
     */
    private String data;

    /**
     * 是否处理
     */
    private Boolean handle;

    /**
     * 创建时间
     */
    private Date createTime;

}

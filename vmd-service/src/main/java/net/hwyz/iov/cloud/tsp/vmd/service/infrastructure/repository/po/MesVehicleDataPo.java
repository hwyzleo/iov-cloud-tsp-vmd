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
 * MES车辆数据表 数据对象
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-12-12
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_mes_vehicle_data")
public class MesVehicleDataPo extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 批次号
     */
    @TableField("batch_num")
    private String batchNum;

    /**
     * 数据类型
     */
    @TableField("type")
    private String type;

    /**
     * 数据版本
     */
    @TableField("version")
    private String version;

    /**
     * MES车辆数据
     */
    @TableField("data")
    private String data;

    /**
     * 是否处理
     */
    @TableField("handle")
    private Boolean handle;
}

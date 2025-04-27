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
 * 车辆零部件表 数据对象
 * </p>
 *
 * @author hwyz_leo
 * @since 2025-04-27
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_veh_part")
public class VehPartPo extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 零部件序列号
     */
    @TableField("sn")
    private String sn;

    /**
     * 零部件编号
     */
    @TableField("no")
    private String no;

    /**
     * 零部件ECU
     */
    @TableField("ecu")
    private String ecu;

    /**
     * ECU配置字
     */
    @TableField("ecu_config_word")
    private String ecuConfigWord;

    /**
     * 供应商编码
     */
    @TableField("supplier_code")
    private String supplierCode;

    /**
     * 硬件版本号
     */
    @TableField("hardware_ver")
    private String hardwareVer;

    /**
     * 软件版本号
     */
    @TableField("software_ver")
    private String softwareVer;

    /**
     * 硬件零件号
     */
    @TableField("hardware_no")
    private String hardwareNo;

    /**
     * 软件零件号
     */
    @TableField("software_no")
    private String softwareNo;

    /**
     * MAC地址
     */
    @TableField("mac")
    private String mac;

    /**
     * 硬件安全模块
     */
    @TableField("hsm")
    private String hsm;

    /**
     * 附加信息
     */
    @TableField("extra")
    private String extra;

    /**
     * 车架号
     */
    @TableField("vin")
    private String vin;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
}

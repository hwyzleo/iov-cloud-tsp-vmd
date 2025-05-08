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
 * 车辆详细信息表 数据对象
 * </p>
 *
 * @author hwyz_leo
 * @since 2025-05-07
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_veh_detail_info")
public class VehDetailInfoPo extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车架号
     */
    @TableField("vin")
    private String vin;

    /**
     * 生产订单
     */
    @TableField("production_order")
    private String productionOrder;

    /**
     * 整车物料编码
     */
    @TableField("matnr")
    private String matnr;

    /**
     * 车型项目
     */
    @TableField("project")
    private String project;

    /**
     * 销售区域
     */
    @TableField("sales_area")
    private String salesArea;

    /**
     * 车身形式
     */
    @TableField("body_form")
    private String bodyForm;

    /**
     * 配置等级
     */
    @TableField("config_level")
    private String configLevel;

    /**
     * 车型年代
     */
    @TableField("model_year")
    private String modelYear;

    /**
     * 方向盘位置
     */
    @TableField("steering_wheel_position")
    private String steeringWheelPosition;

    /**
     * 内饰风格
     */
    @TableField("interior_type")
    private String interiorType;

    /**
     * 外饰颜色
     */
    @TableField("exterior_color")
    private String exteriorColor;

    /**
     * 驱动形式
     */
    @TableField("drive_form")
    private String driveForm;

    /**
     * 车轮
     */
    @TableField("wheel")
    private String wheel;

    /**
     * 车轮颜色
     */
    @TableField("whee_color")
    private String wheeColor;

    /**
     * 座椅类型
     */
    @TableField("seat_type")
    private String seatType;

    /**
     * 辅助驾驶
     */
    @TableField("assisted_driving")
    private String assistedDriving;

    /**
     * ETC系统
     */
    @TableField("etc_system")
    private String etcSystem;

    /**
     * 后牵引杆
     */
    @TableField("rear_tow_bar")
    private String rearTowBar;

    /**
     * 发动机编码
     */
    @TableField("engine_no")
    private String engineNo;

    /**
     * 发动机类型
     */
    @TableField("engine_type")
    private String engineType;

    /**
     * 前驱电机编码
     */
    @TableField("front_drive_motor_no")
    private String frontDriveMotorNo;

    /**
     * 前驱电机类型
     */
    @TableField("front_drive_motor_type")
    private String frontDriveMotorType;

    /**
     * 后驱电机编码
     */
    @TableField("rear_drive_motor_no")
    private String rearDriveMotorNo;

    /**
     * 后驱电机类型
     */
    @TableField("rear_drive_motor_type")
    private String rearDriveMotorType;

    /**
     * 发电机编码
     */
    @TableField("generator_no")
    private String generatorNo;

    /**
     * 发电机类型
     */
    @TableField("generator_type")
    private String generatorType;

    /**
     * 动力电池包编码
     */
    @TableField("power_battery_pack_no")
    private String powerBatteryPackNo;

    /**
     * 动力电池类型
     */
    @TableField("power_battery_type")
    private String powerBatteryType;

    /**
     * 动力电池厂商
     */
    @TableField("power_battery_factory")
    private String powerBatteryFactory;
}

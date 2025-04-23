package net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * 车辆生命周期节点枚举类
 *
 * @author hwyz_leo
 */
@AllArgsConstructor
public enum VehicleLifecycleNode {

    /** 车辆生产 **/
    PRODUCE,
    /** 车辆生成密钥及安全常量 **/
    IMMO_SK,
    /** TBox申请证书 **/
    TBOX_CERT,
    /** TBox申请密钥 **/
    TBOX_SK,
    /** CCU申请证书 **/
    CCU_CERT,
    /** CCU申请密钥 **/
    CCU_SK,
    /** ADCU申请证书 **/
    ADCU_CERT,
    /** ADCU申请密钥 **/
    ADCU_SK,
    /** IDCU申请证书 **/
    IDCU_CERT,
    /** IDCU申请密钥 **/
    IDCU_SK,
    /** 车辆下线 **/
    EOL,
    /** 打印合格证 **/
    CERTIFICATE,
    /** 入前置库 **/
    PDC_INBOUND,
    /** 车辆订单绑定 **/
    ORDER_BIND,
    /** PDI电检 **/
    PDI,
    /** SIM卡绑定 **/
    SIM_BIND,
    /** 车辆发运 **/
    VEHICLE_SHIPPING,
    /** 车辆开票 **/
    VEHICLE_INVOICING,
    /** 车主绑定 **/
    VEHICLE_OWNER_BIND,
    /** 车辆激活 **/
    VEHICLE_ACTIVE,
    /** 车辆交付 **/
    VEHICLE_DELIVERY,
    /** 高精地图激活 **/
    HD_MAP_ACTIVE,
    /** RTK激活 **/
    RTK_ACTIVE;

    public static VehicleLifecycleNode valOf(String val) {
        return Arrays.stream(VehicleLifecycleNode.values())
                .filter(vehicleLifecycleNode -> vehicleLifecycleNode.name().equals(val))
                .findFirst()
                .orElse(null);
    }

}

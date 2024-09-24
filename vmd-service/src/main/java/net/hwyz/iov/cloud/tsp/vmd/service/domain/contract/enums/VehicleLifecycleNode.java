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

    /** 车辆激活 **/
    VEHICLE_ACTIVE;

    public static VehicleLifecycleNode valOf(String val) {
        return Arrays.stream(VehicleLifecycleNode.values())
                .filter(vehicleLifecycleNode -> vehicleLifecycleNode.name().equals(val))
                .findFirst()
                .orElse(null);
    }

}

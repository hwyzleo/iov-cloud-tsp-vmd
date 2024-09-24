package net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * 二维码类型枚举类
 *
 * @author hwyz_leo
 */
@AllArgsConstructor
public enum QrcodeType {

    /** 车辆激活 **/
    VEHICLE_ACTIVE;

    public static QrcodeType valOf(String val) {
        return Arrays.stream(QrcodeType.values())
                .filter(qrcodeType -> qrcodeType.name().equals(val))
                .findFirst()
                .orElse(null);
    }

}

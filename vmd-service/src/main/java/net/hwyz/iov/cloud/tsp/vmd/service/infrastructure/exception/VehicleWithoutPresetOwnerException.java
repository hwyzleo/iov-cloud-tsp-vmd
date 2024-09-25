package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 车辆没有设置预设车主异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class VehicleWithoutPresetOwnerException extends VmdBaseException {

    private static final int ERROR_CODE = 202007;

    public VehicleWithoutPresetOwnerException(String vin) {
        super(ERROR_CODE);
        logger.warn("车辆[{}]没有设置预设车主", vin);
    }

}

package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 车辆不存在异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class VehicleNotExistException extends VmdBaseException {

    public VehicleNotExistException(String vin) {
        super(ERROR_CODE_VEHICLE_NOT_EXIST);
        logger.warn("车辆[{}]不存在", vin);
    }

}

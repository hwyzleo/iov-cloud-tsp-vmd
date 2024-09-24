package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 车辆已激活异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class VehicleHasActivatedException extends VmdBaseException {

    private static final int ERROR_CODE = 202002;

    public VehicleHasActivatedException(String vin) {
        super(ERROR_CODE);
        logger.warn("车辆[{}]已激活", vin);
    }

}

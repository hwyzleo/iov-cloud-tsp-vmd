package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 车辆导入数据异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class VehicleImportDataException extends VmdBaseException {

    private static final int ERROR_CODE = 202010;

    public VehicleImportDataException(String batchNum, String reason) {
        super(ERROR_CODE);
        logger.warn("车辆导入数据[{}]异常[{}]", batchNum, reason);
    }

}

package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.enums.QrcodeType;

/**
 * 车辆对应类型二维码无效异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class QrcodeInvalidException extends VmdBaseException {

    private static final int ERROR_CODE = 202005;

    public QrcodeInvalidException(String vin, QrcodeType type) {
        super(ERROR_CODE);
        logger.warn("车辆[{}]对应类型[{}]二维码无效", vin, type);
    }

}

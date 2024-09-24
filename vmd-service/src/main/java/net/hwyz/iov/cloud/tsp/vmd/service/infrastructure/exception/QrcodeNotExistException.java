package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.QrcodeType;

/**
 * 车辆对应类型二维码不存在异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class QrcodeNotExistException extends VmdBaseException {

    private static final int ERROR_CODE = 202003;

    public QrcodeNotExistException(String vin, QrcodeType type) {
        super(ERROR_CODE);
        logger.warn("车辆[{}]对应类型[{}]二维码不存在", vin, type);
    }

}

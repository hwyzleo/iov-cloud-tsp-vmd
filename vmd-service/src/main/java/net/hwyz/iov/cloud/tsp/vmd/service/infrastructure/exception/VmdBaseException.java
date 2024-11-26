package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception;


import net.hwyz.iov.cloud.framework.common.exception.BaseException;

/**
 * 车辆主数据服务基础异常
 *
 * @author hwyz_leo
 */
public class VmdBaseException extends BaseException {

    private static final int ERROR_CODE = 202000;

    public VmdBaseException(String message) {
        super(ERROR_CODE, message);
    }

    public VmdBaseException(int errorCode) {
        super(errorCode);
    }

    public VmdBaseException(int errorCode, String message) {
        super(errorCode, message);
    }

}

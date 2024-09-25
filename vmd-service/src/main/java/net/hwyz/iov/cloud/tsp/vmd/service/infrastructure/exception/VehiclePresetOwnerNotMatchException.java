package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 车辆预设车主不匹配异常
 *
 * @author hwyz_leo
 */
@Slf4j
public class VehiclePresetOwnerNotMatchException extends VmdBaseException {

    private static final int ERROR_CODE = 202007;

    public VehiclePresetOwnerNotMatchException(String vin, String countryRegionCode, String accountId,
                                               String presetCountryRegionCode, String presetAccountId) {
        super(ERROR_CODE);
        logger.warn("用户[{}:{}]与车辆[{}]预设车主[{}:{}]不匹配", countryRegionCode, accountId, vin, presetCountryRegionCode,
                presetAccountId);
    }

}

package net.hwyz.iov.cloud.tsp.vmd.api.contract.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * ECU类型枚举类
 *
 * @author hwyz_leo
 */
@AllArgsConstructor
public enum EcuType {

    /** TBox **/
    TBOX;

    public static EcuType valOf(String val) {
        return Arrays.stream(EcuType.values())
                .filter(ecuType -> ecuType.name().equals(val))
                .findFirst()
                .orElse(null);
    }

}

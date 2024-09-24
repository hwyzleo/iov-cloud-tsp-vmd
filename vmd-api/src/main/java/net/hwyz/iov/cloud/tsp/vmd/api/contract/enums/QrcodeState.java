package net.hwyz.iov.cloud.tsp.vmd.api.contract.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * 二维码状态枚举类
 *
 * @author hwyz_leo
 */
@AllArgsConstructor
public enum QrcodeState {

    /** 初始化 **/
    INITIALIZED,
    /** 已扫描 **/
    SCANNED,
    /** 已确认 **/
    CONFIRMED,
    /** 已过期 **/
    EXPIRED,
    /** 成功 **/
    SUCCESS,
    /** 失败 **/
    FAILURE;

    public static QrcodeState valOf(String val) {
        return Arrays.stream(QrcodeState.values())
                .filter(qrcodeState -> qrcodeState.name().equals(val))
                .findFirst()
                .orElse(null);
    }

}

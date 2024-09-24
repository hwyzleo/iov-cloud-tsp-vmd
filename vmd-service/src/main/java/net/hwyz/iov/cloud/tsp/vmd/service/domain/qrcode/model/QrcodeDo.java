package net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.model;

import cn.hutool.crypto.digest.MD5;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.BaseDo;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.DomainObj;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.enums.QrcodeState;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.QrcodeType;

/**
 * 二维码领域对象
 *
 * @author hwyz_leo
 */
@Slf4j
@Getter
@SuperBuilder
public class QrcodeDo extends BaseDo<String> implements DomainObj<QrcodeDo> {

    /**
     * 车架号
     */
    private String vin;
    /**
     * 车机序列号
     */
    private String sn;
    /**
     * 二维码
     */
    private String qrcode;
    /**
     * 二维码类型
     */
    private QrcodeType type;
    /**
     * 二维码状态
     */
    private QrcodeState qrcodeState;

    /**
     * 初始化
     *
     * @param vin 车架号
     * @param sn  车机序列号
     */
    public void init(String vin, String sn) {
        stateInit();
        qrcode = generateQrcode(vin, sn);
        qrcodeState = QrcodeState.INITIALIZED;
    }

    /**
     * 生成二维码
     *
     * @return 二维码
     */
    private String generateQrcode(String vin, String sn) {
        // TODO 生成二维码策略
        return MD5.create().digestHex(vin + sn).toUpperCase();
    }

}

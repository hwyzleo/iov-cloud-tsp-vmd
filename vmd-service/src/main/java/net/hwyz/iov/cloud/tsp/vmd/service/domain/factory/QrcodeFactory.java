package net.hwyz.iov.cloud.tsp.vmd.service.domain.factory;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.enums.QrcodeType;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.model.QrcodeDo;
import org.springframework.stereotype.Component;

/**
 * 二维码领域工厂类
 *
 * @author hwyz_leo
 */
@Component
public class QrcodeFactory {

    /**
     * 创建二维码领域对象
     *
     * @param type 二维码类型
     * @param vin  车架号
     * @param sn   车机序列号
     * @return 二维码领域对象
     */
    public QrcodeDo buildQrcode(QrcodeType type, String vin, String sn) {
        QrcodeDo qrcodeDo = QrcodeDo.builder()
                .vin(vin)
                .sn(sn)
                .type(type)
                .build();
        qrcodeDo.init(vin, sn);
        return qrcodeDo;
    }

}

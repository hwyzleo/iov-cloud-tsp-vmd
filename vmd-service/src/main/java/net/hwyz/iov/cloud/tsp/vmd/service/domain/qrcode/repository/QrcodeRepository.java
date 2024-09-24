package net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.repository;

import net.hwyz.iov.cloud.tsp.framework.commons.domain.BaseRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.QrcodeType;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.model.QrcodeDo;

import java.util.Optional;

/**
 * 二维码领域仓库接口
 *
 * @author hwyz_leo
 */
public interface QrcodeRepository extends BaseRepository<String, QrcodeDo> {

    /**
     * 根据车架号和二维码类型获取二维码领域对象
     *
     * @param vin  车架号
     * @param type 二维码类型
     * @return 二维码领域对象
     */
    Optional<QrcodeDo> getByVinAndType(String vin, QrcodeType type);

}

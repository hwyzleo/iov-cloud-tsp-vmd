package net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.repository;

import net.hwyz.iov.cloud.tsp.framework.commons.domain.BaseRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.model.QrcodeDo;

import java.util.Optional;

/**
 * 二维码领域仓库接口
 *
 * @author hwyz_leo
 */
public interface QrcodeRepository extends BaseRepository<String, QrcodeDo> {

    /**
     * 根据二维码获取二维码领域对象
     *
     * @param qrcode 二维码
     * @return 二维码领域对象
     */
    Optional<QrcodeDo> getByQrcode(String qrcode);

}

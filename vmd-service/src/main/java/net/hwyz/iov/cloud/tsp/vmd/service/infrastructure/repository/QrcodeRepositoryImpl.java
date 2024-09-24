package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.AbstractRepository;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.DoState;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.QrcodeType;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.model.QrcodeDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.repository.QrcodeRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.cache.CacheService;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 二维码领域仓库接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class QrcodeRepositoryImpl extends AbstractRepository<String, QrcodeDo> implements QrcodeRepository {

    private final CacheService cacheService;

    @Override
    public Optional<QrcodeDo> getById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean save(QrcodeDo qrcodeDo) {
        if (qrcodeDo.getState() != DoState.UNCHANGED) {
            cacheService.setQrcode(qrcodeDo);
        }
        return true;
    }

    @Override
    public Optional<QrcodeDo> getByVinAndType(String vin, QrcodeType type) {
        return cacheService.getQrcode(vin, type);
    }
}

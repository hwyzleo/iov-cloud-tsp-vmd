package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.response.QrcodeResponse;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.QrcodeType;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.factory.QrcodeFactory;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.model.QrcodeDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.repository.QrcodeRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.repository.VehicleRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.QrcodeNotExistException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleHasActivatedException;
import org.springframework.stereotype.Service;

/**
 * 二维码相关应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QrcodeAppService {

    private final QrcodeFactory qrcodeFactory;
    private final QrcodeRepository qrcodeRepository;
    private final VehicleRepository vehicleRepository;

    /**
     * 生成激活二维码
     *
     * @param vin 车架号
     * @param sn  车机序列号
     * @return 车辆激活二维码
     */
    public QrcodeResponse generateActiveQrcode(String vin, String sn) {
        VehicleDo vehicleDo = vehicleRepository.getByVin(vin);
        if (vehicleDo.isActive()) {
            throw new VehicleHasActivatedException(vin);
        }
        QrcodeDo qrcodeDo = qrcodeFactory.buildQrcode(QrcodeType.VEHICLE_ACTIVE, vin, sn);
        qrcodeRepository.save(qrcodeDo);
        return QrcodeResponse.builder()
                .qrcode(qrcodeDo.getQrcode())
                .state(qrcodeDo.getQrcodeState())
                .build();
    }

    /**
     * 获取激活二维码状态
     *
     * @param vin 车架号
     * @param sn  车机序列号
     * @return 车辆激活二维码状态
     */
    public QrcodeResponse getActiveQrcodeState(String vin, String sn) {
        QrcodeDo qrcodeDo = qrcodeRepository.getByVinAndType(vin, QrcodeType.VEHICLE_ACTIVE)
                .orElseThrow(() -> new QrcodeNotExistException(vin, QrcodeType.VEHICLE_ACTIVE));
        return QrcodeResponse.builder()
                .qrcode(qrcodeDo.getQrcode())
                .state(qrcodeDo.getQrcodeState())
                .build();
    }

}

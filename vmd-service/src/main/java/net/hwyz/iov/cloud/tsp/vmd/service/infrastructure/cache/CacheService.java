package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.cache;

import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.QrcodeType;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.model.QrcodeDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleDo;

import java.util.Optional;

/**
 * 缓存服务接口
 *
 * @author hwyz_leo
 */
public interface CacheService {

    /**
     * 获取车辆领域对象缓存
     *
     * @param vin 车架号
     * @return 车辆领域对象
     */
    Optional<VehicleDo> getVehicle(String vin);

    /**
     * 设置车辆领域对象缓存
     *
     * @param vehicle 车辆领域对象
     */
    void setVehicle(VehicleDo vehicle);

    /**
     * 获取二维码领域对象缓存
     *
     * @param vin  车架号
     * @param type 二维码类型
     * @return 二维码领域对象
     */
    Optional<QrcodeDo> getQrcode(String vin, QrcodeType type);

    /**
     * 设置二维码领域对象缓存
     *
     * @param qrcode 二维码领域对象
     */
    void setQrcode(QrcodeDo qrcode);

}

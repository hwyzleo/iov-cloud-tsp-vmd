package net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.repository;

import net.hwyz.iov.cloud.framework.common.domain.BaseRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleDo;

/**
 * 车辆领域仓库接口
 *
 * @author hwyz_leo
 */
public interface VehicleRepository extends BaseRepository<String, VehicleDo> {

    /**
     * 根据车车架号获取车辆领域对象
     *
     * @param vin 车架号
     * @return 车辆领域对象
     */
    VehicleDo getByVin(String vin);

}

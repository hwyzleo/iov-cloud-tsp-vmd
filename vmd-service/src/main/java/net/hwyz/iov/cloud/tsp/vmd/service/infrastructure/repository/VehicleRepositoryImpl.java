package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.domain.AbstractRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.repository.VehicleLifecycleNodeRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.repository.VehicleRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.cache.CacheService;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleNotExistException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.assembler.VehBasicInfoPoAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehLifecycleDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 车辆领域仓库接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class VehicleRepositoryImpl extends AbstractRepository<String, VehicleDo> implements VehicleRepository {

    private final CacheService cacheService;
    private final VehBasicInfoDao vehBasicInfoDao;
    private final VehLifecycleDao vehLifecycleDao;
    private final VehicleLifecycleNodeRepository vehicleLifecycleNodeRepository;

    @Override
    public Optional<VehicleDo> getById(String vin) {
        return Optional.empty();
    }

    @Override
    public boolean save(VehicleDo vehicleDo) {
        switch (vehicleDo.getState()) {
            case CHANGED -> {
                vehBasicInfoDao.updatePo(VehBasicInfoPoAssembler.INSTANCE.fromDo(vehicleDo));
                cacheService.setVehicle(vehicleDo);
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    @Override
    public VehicleDo getByVin(String vin) {
        VehicleDo vehicleDo = cacheService.getVehicle(vin).orElseGet(() -> {
            logger.info("从数据库加载车辆[{}]领域对象", vin);
            VehBasicInfoPo vehBasicInfoPo = vehBasicInfoDao.selectPoByVin(vin);
            if (vehBasicInfoPo == null) {
                throw new VehicleNotExistException(vin);
            }
            VehicleDo vehicleDoTmp = VehicleDo.builder()
                    .vin(vin)
                    .eolTime(vehBasicInfoPo.getEolTime())
                    .orderNum(vehBasicInfoPo.getOrderNum())
                    .build();
            cacheService.setVehicle(vehicleDoTmp);
            return vehicleDoTmp;
        });
        vehicleDo.stateLoad();
        return vehicleDo;
    }
}

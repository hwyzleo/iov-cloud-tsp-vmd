package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.domain.AbstractRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleLifecycleNodeDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.repository.VehicleLifecycleNodeRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.assembler.VehLifecyclePoAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehLifecycleDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 车辆生命周期节点领域仓库接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class VehicleLifecycleNodeRepositoryImpl extends AbstractRepository<String, VehicleLifecycleNodeDo> implements VehicleLifecycleNodeRepository {

    private final VehLifecycleDao vehLifecycleDao;

    @Override
    public Optional<VehicleLifecycleNodeDo> getById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean save(VehicleLifecycleNodeDo vehicleLifecycleNodeDo) {
        switch (vehicleLifecycleNodeDo.getState()) {
            case NEW -> vehLifecycleDao.insertPo(VehLifecyclePoAssembler.INSTANCE.fromDo(vehicleLifecycleNodeDo));
            case CHANGED -> vehLifecycleDao.updatePo(VehLifecyclePoAssembler.INSTANCE.fromDo(vehicleLifecycleNodeDo));
            default -> {
                return false;
            }
        }
        return true;
    }
}

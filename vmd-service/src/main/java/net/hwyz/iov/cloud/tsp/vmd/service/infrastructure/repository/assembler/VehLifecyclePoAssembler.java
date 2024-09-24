package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.assembler;

import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleLifecycleNodeDo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehLifecyclePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 车辆生命周期数据对象转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehLifecyclePoAssembler {

    VehLifecyclePoAssembler INSTANCE = Mappers.getMapper(VehLifecyclePoAssembler.class);

    /**
     * 数据对象转领域对象
     *
     * @param vehLifecyclePo 数据对象
     * @return 领域对象
     */
    @Mappings({})
    VehicleLifecycleNodeDo toDo(VehLifecyclePo vehLifecyclePo);

}

package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleLifecycleMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehLifecyclePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车辆生命周期转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehicleLifecycleMptAssembler {

    VehicleLifecycleMptAssembler INSTANCE = Mappers.getMapper(VehicleLifecycleMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehLifecyclePo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    VehicleLifecycleMpt fromPo(VehLifecyclePo vehLifecyclePo);

    /**
     * 数据传输对象转数据对象
     *
     * @param vehicleLifecycleMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehLifecyclePo toPo(VehicleLifecycleMpt vehicleLifecycleMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehLifecyclePoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<VehicleLifecycleMpt> fromPoList(List<VehLifecyclePo> vehLifecyclePoList);

}

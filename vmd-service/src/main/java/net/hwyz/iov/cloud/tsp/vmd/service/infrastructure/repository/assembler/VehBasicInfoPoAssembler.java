package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.assembler;

import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleDo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 车辆基础信息数据对象转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehBasicInfoPoAssembler {

    VehBasicInfoPoAssembler INSTANCE = Mappers.getMapper(VehBasicInfoPoAssembler.class);

    /**
     * 数据对象转领域对象
     *
     * @param vehBasicInfoPo 数据对象
     * @return 领域对象
     */
    @Mappings({})
    VehicleDo toDo(VehBasicInfoPo vehBasicInfoPo);

    /**
     * 领域对象转数据对象
     *
     * @param vehicleDo 领域对象
     * @return 数据对象
     */
    @Mappings({})
    VehBasicInfoPo fromDo(VehicleDo vehicleDo);

}

package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleExService;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 对外服务车辆转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehicleExServiceAssembler {

    VehicleExServiceAssembler INSTANCE = Mappers.getMapper(VehicleExServiceAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehBasicInfoPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    VehicleExService fromPo(VehBasicInfoPo vehBasicInfoPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param vehicleExService 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehBasicInfoPo toPo(VehicleExService vehicleExService);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehBasicInfoPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<VehicleExService> fromPoList(List<VehBasicInfoPo> vehBasicInfoPoList);

}

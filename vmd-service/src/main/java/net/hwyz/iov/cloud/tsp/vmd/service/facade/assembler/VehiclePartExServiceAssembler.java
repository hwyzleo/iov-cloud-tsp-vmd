package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehiclePartExService;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPartPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 外部服务车辆零部件转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehiclePartExServiceAssembler {

    VehiclePartExServiceAssembler INSTANCE = Mappers.getMapper(VehiclePartExServiceAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehPartPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    VehiclePartExService fromPo(VehPartPo vehPartPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param vehiclePartExService 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehPartPo toPo(VehiclePartExService vehiclePartExService);

}

package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehiclePartMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehiclePartPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车辆零件转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehiclePartMptAssembler {

    VehiclePartMptAssembler INSTANCE = Mappers.getMapper(VehiclePartMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehiclePartPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    VehiclePartMpt fromPo(VehiclePartPo vehiclePartPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param vehiclePartMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehiclePartPo toPo(VehiclePartMpt vehiclePartMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehiclePartPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<VehiclePartMpt> fromPoList(List<VehiclePartPo> vehiclePartPoList);

}

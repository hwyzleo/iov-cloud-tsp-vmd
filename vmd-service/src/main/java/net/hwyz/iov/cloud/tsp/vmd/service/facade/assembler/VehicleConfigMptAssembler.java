package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleConfigMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehicleConfigPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车辆配置转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehicleConfigMptAssembler {

    VehicleConfigMptAssembler INSTANCE = Mappers.getMapper(VehicleConfigMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehicleConfigPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    VehicleConfigMpt fromPo(VehicleConfigPo vehicleConfigPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param vehicleConfigMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehicleConfigPo toPo(VehicleConfigMpt vehicleConfigMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehicleConfigPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<VehicleConfigMpt> fromPoList(List<VehicleConfigPo> vehicleConfigPoList);

}

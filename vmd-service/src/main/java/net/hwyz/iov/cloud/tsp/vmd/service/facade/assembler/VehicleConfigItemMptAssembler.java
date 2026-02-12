package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleConfigItemMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehicleConfigItemPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车辆配置项转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehicleConfigItemMptAssembler {

    VehicleConfigItemMptAssembler INSTANCE = Mappers.getMapper(VehicleConfigItemMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehicleConfigItemPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    VehicleConfigItemMpt fromPo(VehicleConfigItemPo vehicleConfigItemPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param vehicleConfigItemMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehicleConfigItemPo toPo(VehicleConfigItemMpt vehicleConfigItemMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehicleConfigItemPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<VehicleConfigItemMpt> fromPoList(List<VehicleConfigItemPo> vehicleConfigItemPoList);

}

package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleImportDataMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehImportDataPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车辆导入数据转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehicleImportDataMptAssembler {

    VehicleImportDataMptAssembler INSTANCE = Mappers.getMapper(VehicleImportDataMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehicleImportDataPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    VehicleImportDataMpt fromPo(VehImportDataPo vehicleImportDataPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param vehicleImportDataMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehImportDataPo toPo(VehicleImportDataMpt vehicleImportDataMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehicleImportDataPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<VehicleImportDataMpt> fromPoList(List<VehImportDataPo> vehicleImportDataPoList);

}

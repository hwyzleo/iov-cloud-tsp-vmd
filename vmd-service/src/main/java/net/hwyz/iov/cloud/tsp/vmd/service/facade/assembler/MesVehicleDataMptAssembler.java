package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.MesVehicleDataMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.MesVehicleDataPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台MES车辆数据转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface MesVehicleDataMptAssembler {

    MesVehicleDataMptAssembler INSTANCE = Mappers.getMapper(MesVehicleDataMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param mesVehicleDataPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    MesVehicleDataMpt fromPo(MesVehicleDataPo mesVehicleDataPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param mesVehicleDataMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    MesVehicleDataPo toPo(MesVehicleDataMpt mesVehicleDataMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param mesVehicleDataPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<MesVehicleDataMpt> fromPoList(List<MesVehicleDataPo> mesVehicleDataPoList);

}

package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.ManufacturerMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehManufacturerPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车辆工厂转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface ManufacturerMptAssembler {

    ManufacturerMptAssembler INSTANCE = Mappers.getMapper(ManufacturerMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehManufacturerPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    ManufacturerMpt fromPo(VehManufacturerPo vehManufacturerPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param manufacturerMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehManufacturerPo toPo(ManufacturerMpt manufacturerMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehManufacturerPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<ManufacturerMpt> fromPoList(List<VehManufacturerPo> vehManufacturerPoList);

}

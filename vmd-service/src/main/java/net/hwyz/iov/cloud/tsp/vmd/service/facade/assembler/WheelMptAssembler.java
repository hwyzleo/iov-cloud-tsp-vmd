package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.WheelMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehWheelPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台轮胎轮毂转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface WheelMptAssembler {

    WheelMptAssembler INSTANCE = Mappers.getMapper(WheelMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehWheelPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    WheelMpt fromPo(VehWheelPo vehWheelPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param wheelMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehWheelPo toPo(WheelMpt wheelMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehWheelPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<WheelMpt> fromPoList(List<VehWheelPo> vehWheelPoList);

}

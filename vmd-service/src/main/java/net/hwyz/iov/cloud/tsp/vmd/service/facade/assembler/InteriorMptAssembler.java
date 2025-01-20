package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.InteriorMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehInteriorPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台内饰颜色转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface InteriorMptAssembler {

    InteriorMptAssembler INSTANCE = Mappers.getMapper(InteriorMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehInteriorPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    InteriorMpt fromPo(VehInteriorPo vehInteriorPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param interiorMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehInteriorPo toPo(InteriorMpt interiorMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehInteriorPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<InteriorMpt> fromPoList(List<VehInteriorPo> vehInteriorPoList);

}

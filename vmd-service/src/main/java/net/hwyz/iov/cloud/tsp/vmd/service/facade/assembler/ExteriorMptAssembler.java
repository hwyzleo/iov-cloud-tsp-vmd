package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.ExteriorMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehExteriorPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车身颜色转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface ExteriorMptAssembler {

    ExteriorMptAssembler INSTANCE = Mappers.getMapper(ExteriorMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehExteriorPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    ExteriorMpt fromPo(VehExteriorPo vehExteriorPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param exteriorMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehExteriorPo toPo(ExteriorMpt exteriorMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehExteriorPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<ExteriorMpt> fromPoList(List<VehExteriorPo> vehExteriorPoList);

}

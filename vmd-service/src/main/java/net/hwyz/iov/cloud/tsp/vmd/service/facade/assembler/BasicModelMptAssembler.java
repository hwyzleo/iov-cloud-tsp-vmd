package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.BasicModelMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicModelPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台基础车型转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface BasicModelMptAssembler {

    BasicModelMptAssembler INSTANCE = Mappers.getMapper(BasicModelMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehBasicModelPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    BasicModelMpt fromPo(VehBasicModelPo vehBasicModelPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param basicModelMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehBasicModelPo toPo(BasicModelMpt basicModelMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehBasicModelPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<BasicModelMpt> fromPoList(List<VehBasicModelPo> vehBasicModelPoList);

}

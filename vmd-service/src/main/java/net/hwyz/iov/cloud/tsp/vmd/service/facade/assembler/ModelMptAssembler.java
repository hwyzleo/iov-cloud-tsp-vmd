package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.ModelMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车型转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface ModelMptAssembler {

    ModelMptAssembler INSTANCE = Mappers.getMapper(ModelMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehModelPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    ModelMpt fromPo(VehModelPo vehModelPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param modelMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehModelPo toPo(ModelMpt modelMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehModelPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<ModelMpt> fromPoList(List<VehModelPo> vehModelPoList);

}

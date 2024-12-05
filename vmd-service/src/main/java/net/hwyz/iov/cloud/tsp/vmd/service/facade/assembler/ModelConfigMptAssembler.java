package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.ModelConfigMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelConfigPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车型配置转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface ModelConfigMptAssembler {

    ModelConfigMptAssembler INSTANCE = Mappers.getMapper(ModelConfigMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehModelConfigPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    ModelConfigMpt fromPo(VehModelConfigPo vehModelConfigPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param modelConfigMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehModelConfigPo toPo(ModelConfigMpt modelConfigMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehModelConfigPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<ModelConfigMpt> fromPoList(List<VehModelConfigPo> vehModelConfigPoList);

}

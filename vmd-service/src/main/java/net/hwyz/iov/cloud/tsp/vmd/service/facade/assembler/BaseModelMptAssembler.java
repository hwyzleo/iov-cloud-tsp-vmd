package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.BaseModelMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBaseModelPo;
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
public interface BaseModelMptAssembler {

    BaseModelMptAssembler INSTANCE = Mappers.getMapper(BaseModelMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehBaseModelPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    BaseModelMpt fromPo(VehBaseModelPo vehBaseModelPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param baseModelMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehBaseModelPo toPo(BaseModelMpt baseModelMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehBaseModelPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<BaseModelMpt> fromPoList(List<VehBaseModelPo> vehBaseModelPoList);

}

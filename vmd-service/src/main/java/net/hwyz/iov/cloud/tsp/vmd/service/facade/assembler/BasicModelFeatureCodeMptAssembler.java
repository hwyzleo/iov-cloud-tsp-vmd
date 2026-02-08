package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.BasicModelFeatureCodeMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicModelFeatureCodePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台基础车型特征值转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface BasicModelFeatureCodeMptAssembler {

    BasicModelFeatureCodeMptAssembler INSTANCE = Mappers.getMapper(BasicModelFeatureCodeMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehBasicModelFeatureCodePo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    BasicModelFeatureCodeMpt fromPo(VehBasicModelFeatureCodePo vehBasicModelFeatureCodePo);

    /**
     * 数据传输对象转数据对象
     *
     * @param basicModelFeatureCodeMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehBasicModelFeatureCodePo toPo(BasicModelFeatureCodeMpt basicModelFeatureCodeMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehBasicModelFeatureCodePoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<BasicModelFeatureCodeMpt> fromPoList(List<VehBasicModelFeatureCodePo> vehBasicModelFeatureCodePoList);

}

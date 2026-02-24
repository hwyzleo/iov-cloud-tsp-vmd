package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.BaseModelFeatureCodeMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBaseModelFeatureCodePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台基础车型特征值转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface BaseModelFeatureCodeMptAssembler {

    BaseModelFeatureCodeMptAssembler INSTANCE = Mappers.getMapper(BaseModelFeatureCodeMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehBaseModelFeatureCodePo 数据对象
     * @return 数据传输对象
     */
    @Mappings({
            @Mapping(target = "featureCode", expression = "java(net.hwyz.iov.cloud.framework.common.util.StrUtil.isBlank(vehBaseModelFeatureCodePo.getFeatureCode()) ? null : vehBaseModelFeatureCodePo.getFeatureCode().split(\",\"))")
    })
    BaseModelFeatureCodeMpt fromPo(VehBaseModelFeatureCodePo vehBaseModelFeatureCodePo);

    /**
     * 数据传输对象转数据对象
     *
     * @param baseModelFeatureCodeMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({
            @Mapping(target = "featureCode", expression = "java(baseModelFeatureCodeMpt.getFeatureCode() == null ? null : java.lang.String.join(\",\", baseModelFeatureCodeMpt.getFeatureCode()))")
    })
    VehBaseModelFeatureCodePo toPo(BaseModelFeatureCodeMpt baseModelFeatureCodeMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehBaseModelFeatureCodePoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<BaseModelFeatureCodeMpt> fromPoList(List<VehBaseModelFeatureCodePo> vehBaseModelFeatureCodePoList);

}

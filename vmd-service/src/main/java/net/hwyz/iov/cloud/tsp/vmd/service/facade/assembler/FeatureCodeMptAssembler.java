package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.FeatureCodeMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehFeatureCodePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车辆特征值转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface FeatureCodeMptAssembler {

    FeatureCodeMptAssembler INSTANCE = Mappers.getMapper(FeatureCodeMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehFeatureCodePo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    FeatureCodeMpt fromPo(VehFeatureCodePo vehFeatureCodePo);

    /**
     * 数据传输对象转数据对象
     *
     * @param featureCodeMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehFeatureCodePo toPo(FeatureCodeMpt featureCodeMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehFeatureCodePoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<FeatureCodeMpt> fromPoList(List<VehFeatureCodePo> vehFeatureCodePoList);

}

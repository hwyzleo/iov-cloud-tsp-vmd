package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.FeatureFamilyMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehFeatureFamilyPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车辆特征族转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface FeatureFamilyMptAssembler {

    FeatureFamilyMptAssembler INSTANCE = Mappers.getMapper(FeatureFamilyMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehFeatureFamilyPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    FeatureFamilyMpt fromPo(VehFeatureFamilyPo vehFeatureFamilyPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param featureFamilyMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehFeatureFamilyPo toPo(FeatureFamilyMpt featureFamilyMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehFeatureFamilyPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<FeatureFamilyMpt> fromPoList(List<VehFeatureFamilyPo> vehFeatureFamilyPoList);

}

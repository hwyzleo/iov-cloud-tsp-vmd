package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.BuildConfigMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBuildConfigPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台生产配置转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface BuildConfigMptAssembler {

    BuildConfigMptAssembler INSTANCE = Mappers.getMapper(BuildConfigMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehBuildConfigPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    BuildConfigMpt fromPo(VehBuildConfigPo vehBuildConfigPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param buildConfigMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehBuildConfigPo toPo(BuildConfigMpt buildConfigMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehBuildConfigPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<BuildConfigMpt> fromPoList(List<VehBuildConfigPo> vehBuildConfigPoList);

}

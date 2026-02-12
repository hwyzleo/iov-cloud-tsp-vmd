package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.ConfigItemMappingMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.ConfigItemMappingPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台配置项映射转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface ConfigItemMappingMptAssembler {

    ConfigItemMappingMptAssembler INSTANCE = Mappers.getMapper(ConfigItemMappingMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param configItemMappingPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    ConfigItemMappingMpt fromPo(ConfigItemMappingPo configItemMappingPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param configItemMappingMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    ConfigItemMappingPo toPo(ConfigItemMappingMpt configItemMappingMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param configItemMappingPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<ConfigItemMappingMpt> fromPoList(List<ConfigItemMappingPo> configItemMappingPoList);

}

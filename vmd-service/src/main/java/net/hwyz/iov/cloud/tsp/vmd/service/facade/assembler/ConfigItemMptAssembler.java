package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.ConfigItemMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.ConfigItemPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台配置项转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface ConfigItemMptAssembler {

    ConfigItemMptAssembler INSTANCE = Mappers.getMapper(ConfigItemMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param configItemPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    ConfigItemMpt fromPo(ConfigItemPo configItemPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param configItemMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    ConfigItemPo toPo(ConfigItemMpt configItemMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param configItemPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<ConfigItemMpt> fromPoList(List<ConfigItemPo> configItemPoList);

}

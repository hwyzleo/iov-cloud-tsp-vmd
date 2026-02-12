package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.ConfigItemOptionMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.ConfigItemOptionPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台配置项枚举值转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface ConfigItemOptionMptAssembler {

    ConfigItemOptionMptAssembler INSTANCE = Mappers.getMapper(ConfigItemOptionMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param configItemOptionPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    ConfigItemOptionMpt fromPo(ConfigItemOptionPo configItemOptionPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param configItemOptionMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    ConfigItemOptionPo toPo(ConfigItemOptionMpt configItemOptionMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param configItemOptionPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<ConfigItemOptionMpt> fromPoList(List<ConfigItemOptionPo> configItemOptionPoList);

}

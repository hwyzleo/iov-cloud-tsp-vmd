package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.PlatformMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPlatformPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台销售车型转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface PlatformMptAssembler {

    PlatformMptAssembler INSTANCE = Mappers.getMapper(PlatformMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehPlatformPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    PlatformMpt fromPo(VehPlatformPo vehPlatformPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param platformMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehPlatformPo toPo(PlatformMpt platformMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehPlatformPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<PlatformMpt> fromPoList(List<VehPlatformPo> vehPlatformPoList);

}

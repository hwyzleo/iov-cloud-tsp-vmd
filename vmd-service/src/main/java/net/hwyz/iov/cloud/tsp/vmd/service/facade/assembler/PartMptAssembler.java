package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.PartMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.PartPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台零件信息转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface PartMptAssembler {

    PartMptAssembler INSTANCE = Mappers.getMapper(PartMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param partPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    PartMpt fromPo(PartPo partPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param partMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    PartPo toPo(PartMpt partMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param partPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<PartMpt> fromPoList(List<PartPo> partPoList);

}

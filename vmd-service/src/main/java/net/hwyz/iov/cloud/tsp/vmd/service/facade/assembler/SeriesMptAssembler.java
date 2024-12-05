package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.SeriesMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehSeriesPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车系转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface SeriesMptAssembler {

    SeriesMptAssembler INSTANCE = Mappers.getMapper(SeriesMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehSeriesPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    SeriesMpt fromPo(VehSeriesPo vehSeriesPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param seriesMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehSeriesPo toPo(SeriesMpt seriesMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehSeriesPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<SeriesMpt> fromPoList(List<VehSeriesPo> vehSeriesPoList);

}

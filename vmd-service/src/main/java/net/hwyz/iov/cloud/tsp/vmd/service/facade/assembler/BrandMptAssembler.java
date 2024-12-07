package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.BrandMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBrandPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车辆品牌转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface BrandMptAssembler {

    BrandMptAssembler INSTANCE = Mappers.getMapper(BrandMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehBrandPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    BrandMpt fromPo(VehBrandPo vehBrandPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param brandMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehBrandPo toPo(BrandMpt brandMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehBrandPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<BrandMpt> fromPoList(List<VehBrandPo> vehBrandPoList);

}

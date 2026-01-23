package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.SupplierMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.SupplierPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台供应商转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface SupplierMptAssembler {

    SupplierMptAssembler INSTANCE = Mappers.getMapper(SupplierMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param supplierPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    SupplierMpt fromPo(SupplierPo supplierPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param supplierMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    SupplierPo toPo(SupplierMpt supplierMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param supplierPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<SupplierMpt> fromPoList(List<SupplierPo> supplierPoList);

}

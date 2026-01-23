package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleDomainMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehDomainPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台车载域转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface VehicleDomainMptAssembler {

    VehicleDomainMptAssembler INSTANCE = Mappers.getMapper(VehicleDomainMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param vehDomainPo 数据对象
     * @return 数据传输对象
     */
    @Mappings({})
    VehicleDomainMpt fromPo(VehDomainPo vehDomainPo);

    /**
     * 数据传输对象转数据对象
     *
     * @param vehicleDomainMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({})
    VehDomainPo toPo(VehicleDomainMpt vehicleDomainMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param vehDomainPoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<VehicleDomainMpt> fromPoList(List<VehDomainPo> vehDomainPoList);

}

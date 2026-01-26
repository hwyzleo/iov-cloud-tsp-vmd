package net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler;

import net.hwyz.iov.cloud.tsp.vmd.api.contract.DeviceMpt;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.DevicePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 管理后台设备信息转换类
 *
 * @author hwyz_leo
 */
@Mapper
public interface DeviceMptAssembler {

    DeviceMptAssembler INSTANCE = Mappers.getMapper(DeviceMptAssembler.class);

    /**
     * 数据对象转数据传输对象
     *
     * @param devicePo 数据对象
     * @return 数据传输对象
     */
    @Mappings({
            @Mapping(target = "commProtocol", expression = "java(net.hwyz.iov.cloud.framework.common.util.StrUtil.isBlank(devicePo.getCommProtocol()) ? null : devicePo.getCommProtocol().split(\",\"))"),
            @Mapping(target = "flashProtocol", expression = "java(net.hwyz.iov.cloud.framework.common.util.StrUtil.isBlank(devicePo.getFlashProtocol()) ? null : devicePo.getFlashProtocol().split(\",\"))"),
            @Mapping(target = "nodeType", expression = "java(net.hwyz.iov.cloud.framework.common.util.StrUtil.isBlank(devicePo.getNodeType()) ? null :devicePo.getNodeType().split(\",\"))")
    })
    DeviceMpt fromPo(DevicePo devicePo);

    /**
     * 数据传输对象转数据对象
     *
     * @param deviceMpt 数据传输对象
     * @return 数据对象
     */
    @Mappings({
            @Mapping(target = "commProtocol", expression = "java(deviceMpt.getCommProtocol() == null ? null : java.lang.String.join(\",\", deviceMpt.getCommProtocol()))"),
            @Mapping(target = "flashProtocol", expression = "java(deviceMpt.getFlashProtocol() == null ? null : java.lang.String.join(\",\", deviceMpt.getFlashProtocol()))"),
            @Mapping(target = "nodeType", expression = "java(deviceMpt.getNodeType() == null ? null : java.lang.String.join(\",\", deviceMpt.getNodeType()))")
    })
    DevicePo toPo(DeviceMpt deviceMpt);

    /**
     * 数据对象列表转数据传输对象列表
     *
     * @param devicePoList 数据对象列表
     * @return 数据传输对象列表
     */
    List<DeviceMpt> fromPoList(List<DevicePo> devicePoList);

}

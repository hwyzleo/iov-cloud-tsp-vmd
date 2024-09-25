package net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.BaseDo;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.DomainObj;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.VehicleLifecycleNode;

import java.util.Date;

/**
 * 车辆生命周期节点领域对象
 *
 * @author hwyz_leo
 */
@Slf4j
@Getter
@SuperBuilder
public class VehicleLifecycleNodeDo extends BaseDo<Long> implements DomainObj<VehicleDo> {

    /**
     * 车架号
     */
    private String vin;
    /**
     * 生命周期节点
     */
    private VehicleLifecycleNode node;
    /**
     * 触达时间
     */
    private Date reachTime;
    /**
     * 排序
     */
    private Integer sort;

    public void init() {
        stateInit();
    }

}

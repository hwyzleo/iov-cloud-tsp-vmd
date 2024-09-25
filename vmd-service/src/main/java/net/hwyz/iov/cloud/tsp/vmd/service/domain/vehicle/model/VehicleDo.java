package net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.BaseDo;
import net.hwyz.iov.cloud.tsp.framework.commons.domain.DomainObj;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.VehicleLifecycleNode;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车辆领域对象
 *
 * @author hwyz_leo
 */
@Slf4j
@Getter
@SuperBuilder
public class VehicleDo extends BaseDo<String> implements DomainObj<VehicleDo> {

    /**
     * 车架号
     */
    private String vin;
    /**
     * 最新生命周期节点时间Map
     */
    private Map<VehicleLifecycleNode, Date> nodeTimeMap;
    /**
     * 所有生命周期节点列表
     */
    private List<VehicleLifecycleNodeDo> allNodeList;

    /**
     * 车辆是否已激活
     *
     * @return true:已激活, false:未激活
     */
    public boolean isActive() {
        return nodeTimeMap.get(VehicleLifecycleNode.VEHICLE_ACTIVE) != null;
    }

    /**
     * 激活车辆
     */
    public void activate() {
        VehicleLifecycleNodeDo node = VehicleLifecycleNodeDo.builder()
                .vin(vin)
                .node(VehicleLifecycleNode.VEHICLE_ACTIVE)
                .reachTime(new Date())
                .sort(0)
                .build();
        node.init();
        allNodeList.add(node);
        nodeTimeMap.put(VehicleLifecycleNode.VEHICLE_ACTIVE, node.getReachTime());
        stateChange();
    }

}

package net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.domain.BaseDo;
import net.hwyz.iov.cloud.framework.common.domain.DomainObj;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
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
     * 订单号
     */
    private String orderNum;
    /**
     * 最新生命周期节点时间Map
     */
    private Map<VehicleLifecycleNode, Date> nodeTimeMap;
    /**
     * 所有生命周期节点列表
     */
    private List<VehicleLifecycleNodeDo> allNodeList;

    /**
     * 车辆是否已绑定订单
     *
     * @return true:已绑定, false:未绑定
     */
    public boolean hasOrder() {
        return StrUtil.isNotBlank(this.orderNum);
    }

    /**
     * 车辆是否已激活
     *
     * @return true:已激活, false:未激活
     */
    public boolean isActive() {
        return nodeTimeMap.get(VehicleLifecycleNode.VEHICLE_ACTIVE) != null;
    }

    /**
     * 绑定订单
     *
     * @param orderNum 订单号
     */
    public void bindOrder(String orderNum) {
        this.orderNum = orderNum;
        VehicleLifecycleNodeDo node = newLifecycleNode(VehicleLifecycleNode.BIND_ORDER);
        allNodeList.add(node);
        nodeTimeMap.put(VehicleLifecycleNode.BIND_ORDER, node.getReachTime());
        stateChange();
    }

    /**
     * 激活车辆
     */
    public void activate() {
        VehicleLifecycleNodeDo node = newLifecycleNode(VehicleLifecycleNode.VEHICLE_ACTIVE);
        allNodeList.add(node);
        nodeTimeMap.put(VehicleLifecycleNode.VEHICLE_ACTIVE, node.getReachTime());
        stateChange();
    }

    /**
     * 添加生命周期节点
     *
     * @param lifecycleNode 生命周期节点
     * @return 生命周期节点
     */
    private VehicleLifecycleNodeDo newLifecycleNode(VehicleLifecycleNode lifecycleNode) {
        VehicleLifecycleNodeDo node = VehicleLifecycleNodeDo.builder()
                .vin(vin)
                .node(lifecycleNode)
                .reachTime(new Date())
                .sort(99)
                .build();
        node.init();
        return node;
    }

}

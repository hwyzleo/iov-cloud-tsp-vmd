package net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.domain.BaseDo;
import net.hwyz.iov.cloud.framework.common.domain.DomainObj;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;

import java.util.Date;

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
     * 下线时间
     */
    private Date eolTime;
    /**
     * 订单号
     */
    private String orderNum;

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
        return true;
    }

    /**
     * 绑定订单
     *
     * @param orderNum 订单号
     */
    public void bindOrder(String orderNum) {
        this.orderNum = orderNum;
        stateChange();
    }

}

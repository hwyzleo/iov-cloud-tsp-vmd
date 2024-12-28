package net.hwyz.iov.cloud.tsp.vmd.api.feign.service;

import net.hwyz.iov.cloud.framework.common.constant.ServiceNameConstants;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleOrderExService;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.factory.ExVehicleServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 车辆相关服务接口
 *
 * @author hwyz_leo
 */
@FeignClient(contextId = "exVehicleService", value = ServiceNameConstants.TSP_VMD, path = "/service/vehicle", fallbackFactory = ExVehicleServiceFallbackFactory.class)
public interface ExVehicleService {

    /**
     * 车辆绑定订单
     *
     * @param vin          车架号
     * @param vehicleOrder 车辆订单
     */
    @PostMapping("/{vin}/action/bindOrder")
    void bindOrder(@PathVariable String vin, @RequestBody @Validated VehicleOrderExService vehicleOrder);

}

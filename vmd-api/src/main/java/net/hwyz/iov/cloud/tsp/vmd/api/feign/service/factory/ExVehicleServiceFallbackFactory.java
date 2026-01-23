package net.hwyz.iov.cloud.tsp.vmd.api.feign.service.factory;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleExService;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleOrderExService;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.ExVehicleService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 车辆相关服务降级处理
 *
 * @author hwyz_leo
 */
@Slf4j
@Component
public class ExVehicleServiceFallbackFactory implements FallbackFactory<ExVehicleService> {

    @Override
    public ExVehicleService create(Throwable throwable) {
        return new ExVehicleService() {
            @Override
            public void bindOrder(String vin, VehicleOrderExService vehicleOrder) {
                logger.error("车辆服务车辆[{}]绑定订单[{}]调用失败", vin, vehicleOrder.getOrderNum(), throwable);
            }

            @Override
            public VehicleExService getByVin(String vin) {
                logger.error("车辆服务根据车架号[{}]查询车辆信息调用失败", vin, throwable);
                return null;
            }
        };
    }
}

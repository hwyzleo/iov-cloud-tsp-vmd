package net.hwyz.iov.cloud.tsp.vmd.api.feign.service.factory;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.enums.EcuType;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehiclePartExService;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.ExVehiclePartService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 车辆零部件相关服务降级处理
 *
 * @author hwyz_leo
 */
@Slf4j
@Component
public class ExVehiclePartServiceFallbackFactory implements FallbackFactory<ExVehiclePartService> {

    @Override
    public ExVehiclePartService create(Throwable throwable) {
        return new ExVehiclePartService() {
            @Override
            public VehiclePartExService getPartBySn(EcuType ecuType, String sn) {
                logger.error("车辆零部件服务根据零部件类型[{}]及序列号[{}]获取零部件调用失败", ecuType, sn, throwable);
                return null;
            }
        };
    }
}

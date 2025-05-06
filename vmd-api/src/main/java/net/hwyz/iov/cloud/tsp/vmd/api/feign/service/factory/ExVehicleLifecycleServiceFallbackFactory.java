package net.hwyz.iov.cloud.tsp.vmd.api.feign.service.factory;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.ExVehicleLifecycleService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 车辆生命周期相关服务降级处理
 *
 * @author hwyz_leo
 */
@Slf4j
@Component
public class ExVehicleLifecycleServiceFallbackFactory implements FallbackFactory<ExVehicleLifecycleService> {

    @Override
    public ExVehicleLifecycleService create(Throwable throwable) {
        return new ExVehicleLifecycleService() {
            @Override
            public void recordApplyTboxCertNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]申请车联终端证书节点调用失败", vin, throwable);
            }

            @Override
            public void recordApplyCcpCertNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]申请中央计算平台证书节点调用失败", vin, throwable);
            }

            @Override
            public void recordApplyIdcmCertNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]申请信息娱乐模块证书节点调用失败", vin, throwable);
            }

            @Override
            public void recordApplyAdcmCertNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]申请智驾模块证书节点调用失败", vin, throwable);
            }
        };
    }
}

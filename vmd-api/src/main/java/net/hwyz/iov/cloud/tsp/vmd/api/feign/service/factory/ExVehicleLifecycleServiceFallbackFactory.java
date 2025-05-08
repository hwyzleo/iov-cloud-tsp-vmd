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
            public void recordFirstApplyTboxCertNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]第一次申请车联终端证书节点调用失败", vin, throwable);
            }

            @Override
            public void recordFirstApplyTboxCommSkNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]第一次申请车联终端通讯密钥节点调用失败", vin, throwable);
            }

            @Override
            public void recordFirstApplyCcpCertNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]第一次申请中央计算平台证书节点调用失败", vin, throwable);
            }

            @Override
            public void recordFirstApplyCcpCommSkNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]第一次申请中央计算平台通讯密钥节点调用失败", vin, throwable);
            }

            @Override
            public void recordFirstApplyIdcmCertNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]第一次申请信息娱乐模块证书节点调用失败", vin, throwable);
            }

            @Override
            public void recordFirstApplyIdcmCommSkNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]第一次申请信息娱乐模块通讯密钥节点调用失败", vin, throwable);
            }

            @Override
            public void recordFirstApplyAdcmCertNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]第一次申请智驾模块证书节点调用失败", vin, throwable);
            }

            @Override
            public void recordFirstApplyAdcmCommSkNode(String vin) {
                logger.error("车辆生命周期服务记录车辆[{}]第一次申请智驾模块通讯密钥节点调用失败", vin, throwable);
            }
        };
    }
}

package net.hwyz.iov.cloud.tsp.vmd.api.feign.service;

import net.hwyz.iov.cloud.framework.common.constant.ServiceNameConstants;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.factory.ExVehicleLifecycleServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 车辆生命周期相关服务接口
 *
 * @author hwyz_leo
 */
@FeignClient(contextId = "exVehicleLifecycleService", value = ServiceNameConstants.TSP_VMD, path = "/service/vehicleLifecycle", fallbackFactory = ExVehicleLifecycleServiceFallbackFactory.class)
public interface ExVehicleLifecycleService {

    /**
     * 记录第一次申请车联终端证书节点
     *
     * @param vin 车架号
     */
    @PostMapping("/{vin}/recordFirstApplyTboxCertNode")
    void recordFirstApplyTboxCertNode(@PathVariable String vin);

    /**
     * 记录第一次申请中央计算平台证书节点
     *
     * @param vin 车架号
     */
    @PostMapping("/{vin}/recordFirstApplyCcpCertNode")
    void recordFirstApplyCcpCertNode(@PathVariable String vin);

    /**
     * 记录第一次申请信息娱乐模块平台证书节点
     *
     * @param vin 车架号
     */
    @PostMapping("/{vin}/recordFirstApplyIdcmCertNode")
    void recordFirstApplyIdcmCertNode(@PathVariable String vin);

    /**
     * 记录第一次申请智驾模块平台证书节点
     *
     * @param vin 车架号
     */
    @PostMapping("/{vin}/recordFirstApplyAdcmCertNode")
    void recordFirstApplyAdcmCertNode(@PathVariable String vin);

}

package net.hwyz.iov.cloud.tsp.vmd.service.facade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehicleLifecycleAppService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆生命周期相关服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/service/vehicleLifecycle")
public class VehicleLifecycleServiceController {

    private final VehicleLifecycleAppService vehicleLifecycleAppService;

    /**
     * 记录申请车联终端证书节点
     *
     * @param vin 车架号
     */
    @PostMapping("/{vin}/recordApplyTboxCertNode")
    public void recordApplyTboxCertNode(@PathVariable String vin) {
        logger.info("记录车辆[{}]申请车联终端证书节点", vin);
        vehicleLifecycleAppService.applyTboxCert(vin);
    }

    /**
     * 记录申请中央计算平台证书节点
     *
     * @param vin 车架号
     */
    @PostMapping("/{vin}/recordApplyCcpCertNode")
    public void recordApplyCcpCertNode(@PathVariable String vin) {
        logger.info("记录车辆[{}]申请中央计算平台证书节点", vin);
        vehicleLifecycleAppService.applyCcpCert(vin);
    }

    /**
     * 记录申请信息娱乐模块证书节点
     *
     * @param vin 车架号
     */
    @PostMapping("/{vin}/recordApplyIdcmCertNode")
    public void recordApplyIdcmCertNode(@PathVariable String vin) {
        logger.info("记录车辆[{}]申请信息娱乐模块证书节点", vin);
        vehicleLifecycleAppService.applyIdcmCert(vin);
    }

    /**
     * 记录申请智驾模块证书节点
     *
     * @param vin 车架号
     */
    @PostMapping("/{vin}/recordApplyAdcmCertNode")
    public void recordApplyAdcmCertNode(@PathVariable String vin) {
        logger.info("记录车辆[{}]申请智驾模块证书节点", vin);
        vehicleLifecycleAppService.applyAdcmCert(vin);
    }

}

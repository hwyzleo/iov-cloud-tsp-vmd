package net.hwyz.iov.cloud.tsp.vmd.service.facade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.VehicleModelConfigServiceApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehicleModelConfigAppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆车系车型配置相关服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/service/vehicleModelConfig")
public class VehicleModelConfigServiceController implements VehicleModelConfigServiceApi {

    private final VehicleModelConfigAppService vehicleModelConfigAppService;

    /**
     * 根据车型配置类型得到匹配的车型配置代码
     *
     * @param modelCode     车型代码
     * @param exteriorCode  外饰代码
     * @param interiorCode  内饰代码
     * @param wheelCode     车轮代码
     * @param spareTireCode 备胎代码
     * @param adasCode      智驾代码
     * @return 车型配置代码
     */
    @Override
    @GetMapping("/modelConfigCode")
    public String getVehicleModeConfigCode(@RequestParam String modelCode, @RequestParam String exteriorCode,
                                           @RequestParam String interiorCode, @RequestParam String wheelCode,
                                           @RequestParam String spareTireCode, @RequestParam String adasCode) {
        logger.info("根据车型[{}]配置类型得到匹配的车型配置代码", modelCode);
        return vehicleModelConfigAppService.getModelConfigCodeByType(modelCode, exteriorCode, interiorCode, wheelCode,
                spareTireCode, adasCode);
    }
}

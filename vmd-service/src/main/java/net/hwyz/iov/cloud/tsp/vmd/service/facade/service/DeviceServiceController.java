package net.hwyz.iov.cloud.tsp.vmd.service.facade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.DeviceExService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.DeviceAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.DeviceExServiceAssembler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备相关服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/service/device")
public class DeviceServiceController {

    private final DeviceAppService deviceAppService;

    /**
     * 根据设备代码查询设备信息
     *
     * @param code 设备代码
     * @return 设备信息
     */
    @GetMapping("/{code}")
    public DeviceExService getByCode(@PathVariable String code) {
        logger.info("根据设备代码[{}]查询设备信息", code);
        return DeviceExServiceAssembler.INSTANCE.fromPo(deviceAppService.getDeviceByCode(code));
    }

}

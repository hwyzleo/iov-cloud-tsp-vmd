package net.hwyz.iov.cloud.tsp.vmd.service.facade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehiclePartExService;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.enums.EcuType;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehiclePartAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehiclePartExServiceAssembler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆零部件相关服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/service/vehiclePart")
public class VehiclePartServiceController {

    private final VehiclePartAppService vehiclePartAppService;

    /**
     * 根据零部件类型及序列号获取零部件
     *
     * @param ecuType 零部件类型
     * @param sn      序列号
     */
    @GetMapping("/getBySn")
    public VehiclePartExService getPartBySn(@RequestParam EcuType ecuType, @RequestParam String sn) {
        logger.info("根据零部件类型[{}]及序列号[{}]获取零部件", ecuType, sn);
        return VehiclePartExServiceAssembler.INSTANCE.fromPo(vehiclePartAppService.getPartBySn(ecuType, sn));
    }

}

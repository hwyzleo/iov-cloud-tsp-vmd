package net.hwyz.iov.cloud.tsp.vmd.service.facade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleExService;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleOrderExService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehicleAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehicleExServiceAssembler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 车辆相关服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/service/vehicle")
public class VehicleServiceController {

    private final VehicleAppService vehicleAppService;

    /**
     * 车辆绑定订单
     *
     * @param vin          车架号
     * @param vehicleOrder 车辆订单
     */
    @PostMapping("/{vin}/action/bindOrder")
    public void bindOrder(@PathVariable String vin, @RequestBody @Validated VehicleOrderExService vehicleOrder) {
        logger.info("车辆[{}]绑定订单[{}]", vin, vehicleOrder.getOrderNum());
        vehicleAppService.bindOrder(vin, vehicleOrder.getOrderNum());
    }

    /**
     * 根据车架号查询车辆信息
     *
     * @param vin 车架号
     * @return 车辆信息
     */
    @GetMapping("/{vin}")
    public VehicleExService getByVin(@PathVariable String vin) {
        logger.info("根据车架号[{}]查询车辆信息", vin);
        return VehicleExServiceAssembler.INSTANCE.fromPo(vehicleAppService.getVehicleByVin(vin));
    }

}

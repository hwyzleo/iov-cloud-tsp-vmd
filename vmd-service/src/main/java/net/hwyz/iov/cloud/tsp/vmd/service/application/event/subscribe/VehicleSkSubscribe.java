package net.hwyz.iov.cloud.tsp.vmd.service.application.event.subscribe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.sec.api.feign.service.ExSkService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.event.event.VehicleProduceEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 车辆密钥事件订阅类
 *
 * @author hwyz_leo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleSkSubscribe {

    private final ExSkService exSkService;

    /**
     * 订阅车辆生产事件
     *
     * @param event 车辆生产事件
     */
    @EventListener
    public void onQrcodeConfirmEvent(VehicleProduceEvent event) {
        exSkService.generateVehicleSk(event.getVin());
    }

}

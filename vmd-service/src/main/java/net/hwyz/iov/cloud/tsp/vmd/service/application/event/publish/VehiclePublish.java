package net.hwyz.iov.cloud.tsp.vmd.service.application.event.publish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.service.application.event.event.VehicleProduceEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 车辆事件发布类
 *
 * @author hwyz_leo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VehiclePublish {

    final ApplicationContext ctx;

    /**
     * 车辆生产
     *
     * @param vin 车架号
     */
    public void produce(String vin) {
        logger.debug("发布车辆[{}]生产事件", vin);
        ctx.publishEvent(new VehicleProduceEvent(vin));
    }

}

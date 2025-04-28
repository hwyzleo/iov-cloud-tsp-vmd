package net.hwyz.iov.cloud.tsp.vmd.api.feign.service;

import net.hwyz.iov.cloud.framework.common.constant.ServiceNameConstants;
import net.hwyz.iov.cloud.framework.common.enums.EcuType;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehiclePartExService;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.factory.ExVehiclePartServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 车辆零部件相关服务接口
 *
 * @author hwyz_leo
 */
@FeignClient(contextId = "exVehiclePartService", value = ServiceNameConstants.TSP_VMD, path = "/service/vehiclePart", fallbackFactory = ExVehiclePartServiceFallbackFactory.class)
public interface ExVehiclePartService {

    /**
     * 根据零部件类型及序列号获取零部件
     *
     * @param ecuType 零部件类型
     * @param sn      序列号
     */
    @GetMapping("/getBySn")
    VehiclePartExService getPartBySn(@RequestParam EcuType ecuType, @RequestParam String sn);

}

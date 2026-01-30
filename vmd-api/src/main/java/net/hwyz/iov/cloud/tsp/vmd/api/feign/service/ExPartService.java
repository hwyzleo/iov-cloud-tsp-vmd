package net.hwyz.iov.cloud.tsp.vmd.api.feign.service;

import net.hwyz.iov.cloud.framework.common.constant.ServiceNameConstants;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.PartExService;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.factory.ExDeviceServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 零件相关服务接口
 *
 * @author hwyz_leo
 */
@FeignClient(contextId = "exPartService", value = ServiceNameConstants.TSP_VMD, path = "/service/part", fallbackFactory = ExDeviceServiceFallbackFactory.class)
public interface ExPartService {

    /**
     * 根据零件号查询零件信息
     *
     * @param pn 零件号
     * @return 零件信息
     */
    @GetMapping("/{pn}")
    PartExService getByPn(@PathVariable String pn);

}

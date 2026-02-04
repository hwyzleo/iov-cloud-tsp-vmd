package net.hwyz.iov.cloud.tsp.vmd.api.feign.service.factory;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.DeviceExService;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.PartExService;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.ExDeviceService;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.service.ExPartService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 零件相关服务降级处理
 *
 * @author hwyz_leo
 */
@Slf4j
@Component
public class ExPartServiceFallbackFactory implements FallbackFactory<ExPartService> {

    @Override
    public ExPartService create(Throwable throwable) {
        return new ExPartService() {
            @Override
            public PartExService getByPn(String pn) {
                logger.error("零件服务根据零件号[{}]查询零件信息调用失败", pn, throwable);
                return null;
            }

            @Override
            public List<PartExService> listAllFota() {
                logger.error("零件服务获取所有FOTA升级零件信息调用失败", throwable);
                return null;
            }
        };
    }
}

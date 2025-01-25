package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehModelConfigDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelConfigPo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车系车型配置相关应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleModelConfigAppService {

    private final VehModelConfigDao vehModelConfigDao;

    /**
     * 根据车型配置类型获取车型配置编码
     *
     * @param modelCode     车型编码
     * @param exteriorCode  外饰编码
     * @param interiorCode  内饰编码
     * @param wheelCode     车轮编码
     * @param spareTireCode 备胎编码
     * @param adasCode      智驾编码
     * @param seatCode      座椅编码
     * @return 车型配置编码
     */
    public String getModelConfigCodeByType(String modelCode, String exteriorCode, String interiorCode, String wheelCode,
                                           String spareTireCode, String adasCode, String seatCode) {
        List<VehModelConfigPo> vehModelConfigPoList = vehModelConfigDao.selectPoByExample(VehModelConfigPo.builder()
                .modelCode(modelCode)
                .exteriorCode(exteriorCode)
                .interiorCode(interiorCode)
                .wheelCode(wheelCode)
                .spareTireCode(spareTireCode)
                .adasCode(adasCode)
                .seatCode(seatCode)
                .build());
        if (vehModelConfigPoList.isEmpty()) {
            return null;
        }
        if (vehModelConfigPoList.size() > 1) {
            logger.warn("车型[{}]外饰[{}]内饰[{}]车轮[{}]备胎[{}]智驾[{}]查询车型配置编码结果数量大于1", modelCode, exteriorCode,
                    interiorCode, wheelCode, spareTireCode, adasCode);
        }
        return vehModelConfigPoList.get(0).getCode();
    }

}

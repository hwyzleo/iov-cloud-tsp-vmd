package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.enums.EcuType;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehPartDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPartPo;
import org.springframework.stereotype.Service;

/**
 * 车辆零部件应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehiclePartAppService {

    private final VehPartDao vehPartDao;


    /**
     * 根据零部件类型及序列号获取零部件
     *
     * @param ecuType 零部件类型
     * @param sn      序列号
     * @return 零部件
     */
    public VehPartPo getPartBySn(EcuType ecuType, String sn) {
        return vehPartDao.selectPoBySn(ecuType.name(), sn);
    }

    /**
     * 根据零部件类型及车架号获取零部件
     *
     * @param ecuType 零部件类型
     * @param vin     车架号
     * @return 零部件
     */
    public VehPartPo getPartByVin(EcuType ecuType, String vin) {
        return vehPartDao.selectPoByVin(ecuType.name(), vin);
    }

    /**
     * 新增车辆零部件
     *
     * @param vehPartPo 车辆零部件
     * @return 结果
     */
    public int createPart(VehPartPo vehPartPo) {
        if (ObjUtil.isNull(vehPartPo.getSort())) {
            vehPartPo.setSort(99);
        }
        return vehPartDao.insertPo(vehPartPo);
    }

    /**
     * 修改车辆零部件
     *
     * @param vehPartPo 车辆零部件
     * @return 结果
     */
    public int modifyPart(VehPartPo vehPartPo) {
        return vehPartDao.updatePo(vehPartPo);
    }

}

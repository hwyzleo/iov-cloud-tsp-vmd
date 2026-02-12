package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehicleConfigDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehicleConfigItemDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehicleConfigItemPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehicleConfigPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆配置应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleConfigAppService {

    private final VehicleConfigDao vehicleConfigDao;
    private final VehicleConfigItemDao vehicleConfigItemDao;

    /**
     * 查询车辆配置
     *
     * @param vin       车架号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 车辆零件列表
     */
    public List<VehicleConfigPo> search(String vin, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("vin", vin);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehicleConfigDao.selectPoByMap(map);
    }

    /**
     * 查询车辆配置项
     *
     * @param vin       车架号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 车辆零件列表
     */
    public List<VehicleConfigItemPo> searchConfigItem(String vin, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("vin", vin);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehicleConfigItemDao.selectPoByMap(map);
    }

    /**
     * 根据主键ID获取车辆配置
     *
     * @param id 主键ID
     * @return 车辆配置
     */
    public VehicleConfigPo getVehicleConfigById(Long id) {
        return vehicleConfigDao.selectPoById(id);
    }

    /**
     * 根据主键ID获取车辆配置项
     *
     * @param id 主键ID
     * @return 车辆配置项
     */
    public VehicleConfigItemPo getVehicleConfigItemById(String vin, Long id) {
        return vehicleConfigItemDao.selectPoById(id);
    }

}

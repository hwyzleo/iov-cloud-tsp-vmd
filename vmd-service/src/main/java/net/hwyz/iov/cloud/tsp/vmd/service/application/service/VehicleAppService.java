package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehLifecycleDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehLifecyclePo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleAppService {

    private final VehBasicInfoDao vehBasicInfoDao;
    private final VehLifecycleDao vehLifecycleDao;

    /**
     * 查询车辆信息
     *
     * @param vin       车架号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 车辆平台列表
     */
    public List<VehBasicInfoPo> search(String vin, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("vin", ParamHelper.fuzzyQueryParam(vin));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehBasicInfoDao.selectPoByMap(map);
    }

    /**
     * 查询车辆生命周期
     *
     * @param vin 车架号
     * @return 车辆生命周期列表
     */
    public List<VehLifecyclePo> listLifecycle(String vin) {
        return vehLifecycleDao.selectPoByExample(VehLifecyclePo.builder().vin(vin).build());
    }

    /**
     * 检查车架号是否唯一
     *
     * @param vehicleId 车辆ID
     * @param vin       车架号
     * @return 结果
     */
    public Boolean checkVinUnique(Long vehicleId, String vin) {
        if (ObjUtil.isNull(vehicleId)) {
            vehicleId = -1L;
        }
        VehBasicInfoPo vehBasicInfoPo = getVehicleByVin(vin);
        return !ObjUtil.isNotNull(vehBasicInfoPo) || vehBasicInfoPo.getId().longValue() == vehicleId.longValue();
    }

    /**
     * 根据主键ID获取车辆信息
     *
     * @param id 主键ID
     * @return 车辆信息
     */
    public VehBasicInfoPo getVehicleById(Long id) {
        return vehBasicInfoDao.selectPoById(id);
    }

    /**
     * 根据车架号获取车辆信息
     *
     * @param vin 车架号
     * @return 车辆信息
     */
    public VehBasicInfoPo getVehicleByVin(String vin) {
        return vehBasicInfoDao.selectPoByVin(vin);
    }

    /**
     * 新增车辆
     *
     * @param vehBasicInfo 车辆信息
     * @return 结果
     */
    public int createVehicle(VehBasicInfoPo vehBasicInfo) {
        return vehBasicInfoDao.insertPo(vehBasicInfo);
    }

    /**
     * 新增车辆生命周期
     *
     * @param vehLifecyclePo 车辆生命周期
     * @return 结果
     */
    public int createVehicleLifecycle(VehLifecyclePo vehLifecyclePo) {
        return vehLifecycleDao.insertPo(vehLifecyclePo);
    }

    /**
     * 修改车辆
     *
     * @param vehBasicInfo 车辆信息
     * @return 结果
     */
    public int modifyVehicle(VehBasicInfoPo vehBasicInfo) {
        return vehBasicInfoDao.updatePo(vehBasicInfo);
    }

    /**
     * 修改车辆生命周期
     *
     * @param vehLifecyclePo 车辆生命周期
     * @return 结果
     */
    public int modifyVehicleLifecycle(VehLifecyclePo vehLifecyclePo) {
        return vehLifecycleDao.updatePo(vehLifecyclePo);
    }

    /**
     * 批量删除车辆
     *
     * @param ids 车辆ID数组
     * @return 结果
     */
    public int deleteVehicleByIds(Long[] ids) {
        for (Long id : ids) {
            VehBasicInfoPo vehiclePo = getVehicleById(id);
            if (ObjUtil.isNotNull(vehiclePo)) {
                vehLifecycleDao.batchPhysicalDeletePoByVin(vehiclePo.getVin());
            }
        }
        return vehBasicInfoDao.batchPhysicalDeletePo(ids);
    }

    /**
     * 批量删除车辆生命周期
     *
     * @param ids 车辆生命周期ID数组
     * @return 结果
     */
    public int deleteVehicleLifecycleByIds(Long[] ids) {
        return vehLifecycleDao.batchPhysicalDeletePo(ids);
    }

}

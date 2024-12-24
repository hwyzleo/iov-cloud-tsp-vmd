package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.DateUtil;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.VehicleLifecycleNode;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.MesVehicleDataDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehLifecycleDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.MesVehicleDataPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehLifecyclePo;
import org.springframework.scheduling.annotation.Async;
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
    private final MesVehicleDataDao mesVehicleDataDao;

    /**
     * 查询车辆信息
     *
     * @param vin             车架号
     * @param modelConfigCode 车型配置代码
     * @param beginTime       开始时间
     * @param endTime         结束时间
     * @param isEol           是否下线
     * @param isOrder         是否有订单
     * @return 车辆平台列表
     */
    public List<VehBasicInfoPo> search(String vin, String modelConfigCode, Date beginTime, Date endTime, Boolean isEol,
                                       Boolean isOrder) {
        Map<String, Object> map = new HashMap<>();
        map.put("vin", ParamHelper.fuzzyQueryParam(vin));
        map.put("modelConfigCode", modelConfigCode);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        map.put("isEol", isEol);
        map.put("isOrder", isOrder);
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

    /**
     * 解析MES车辆数据
     *
     * @param batchNum 批次号
     */
    @Async
    public void parseMesVehicleData(String batchNum) {
        MesVehicleDataPo mesVehicleDataPo = mesVehicleDataDao.selectPoByBatchNum(batchNum);
        if (ObjUtil.isNull(mesVehicleDataPo)) {
            logger.warn("MES车辆数据批次号[{}]不存在", batchNum);
            return;
        }
        if (mesVehicleDataPo.getHandle()) {
            logger.warn("MES车辆数据批次号[{}]已处理", batchNum);
            return;
        }
        String dataStr = mesVehicleDataPo.getData();
        // 当前没有别的类型和版本，不做额外判断
        if (StrUtil.isBlank(dataStr)) {
            logger.warn("MES车辆数据批次号[{}]数据为空", batchNum);
            return;
        }
        JSONObject dataJson = JSONUtil.parseObj(dataStr);
        String vin = dataJson.getByPath("$.REQUEST.DATA.ITEM.VIN", String[].class)[0];
        if (StrUtil.isBlank(vin)) {
            logger.warn("MES车辆数据批次号[{}]车架号为空", batchNum);
            return;
        }
        VehBasicInfoPo vehBasicInfoPo = getVehicleByVin(vin);
        if (ObjUtil.isNull(vehBasicInfoPo)) {
            vehBasicInfoPo = new VehBasicInfoPo();
            vehBasicInfoPo.setVin(vin);
        }
        String manufacturer = dataJson.getByPath("$.REQUEST.DATA.ITEM.MANUFACTURER", String[].class)[0];
        if (StrUtil.isNotBlank(manufacturer)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getManufacturerCode())) {
                vehBasicInfoPo.setManufacturerCode(manufacturer.trim().toUpperCase());
            } else if (!manufacturer.trim().equalsIgnoreCase(vehBasicInfoPo.getManufacturerCode())) {
                logger.warn("MES车辆数据批次号[{}]工厂数据[{}]与原数据[{}]不一致", batchNum, manufacturer.trim(),
                        vehBasicInfoPo.getManufacturerCode());
            }
        } else {
            logger.warn("MES车辆数据批次号[{}]工厂为空", batchNum);
        }
        String brand = dataJson.getByPath("$.REQUEST.DATA.ITEM.BRAND", String[].class)[0];
        if (StrUtil.isNotBlank(brand)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getBrandCode())) {
                vehBasicInfoPo.setBrandCode(brand.trim().toUpperCase());
            } else if (!brand.trim().equalsIgnoreCase(vehBasicInfoPo.getBrandCode())) {
                logger.warn("MES车辆数据批次号[{}]品牌数据[{}]与原数据[{}]不一致", batchNum, brand.trim(),
                        vehBasicInfoPo.getBrandCode());
            }
        } else {
            logger.warn("MES车辆数据批次号[{}]品牌为空", batchNum);
        }
        String platform = dataJson.getByPath("$.REQUEST.DATA.ITEM.PLATFORM", String[].class)[0];
        if (StrUtil.isNotBlank(platform)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getPlatformCode())) {
                vehBasicInfoPo.setPlatformCode(platform.trim().toUpperCase());
            } else if (!platform.trim().equalsIgnoreCase(vehBasicInfoPo.getPlatformCode())) {
                logger.warn("MES车辆数据批次号[{}]平台数据[{}]与原数据[{}]不一致", batchNum, platform.trim(),
                        vehBasicInfoPo.getPlatformCode());
            }
        } else {
            logger.warn("MES车辆数据批次号[{}]平台为空", batchNum);
        }
        String series = dataJson.getByPath("$.REQUEST.DATA.ITEM.SERIES", String[].class)[0];
        if (StrUtil.isNotBlank(series)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getSeriesCode())) {
                vehBasicInfoPo.setSeriesCode(series.trim().toUpperCase());
            } else if (!series.trim().equalsIgnoreCase(vehBasicInfoPo.getSeriesCode())) {
                logger.warn("MES车辆数据批次号[{}]车系数据[{}]与原数据[{}]不一致", batchNum, series.trim(),
                        vehBasicInfoPo.getSeriesCode());
            }
        } else {
            logger.warn("MES车辆数据批次号[{}]车系为空", batchNum);
        }
        String model = dataJson.getByPath("$.REQUEST.DATA.ITEM.MODEL", String[].class)[0];
        if (StrUtil.isNotBlank(model)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getModelCode())) {
                vehBasicInfoPo.setModelCode(model.trim().toUpperCase());
            } else if (!model.trim().equalsIgnoreCase(vehBasicInfoPo.getModelCode())) {
                logger.warn("MES车辆数据批次号[{}]车型数据[{}]与原数据[{}]不一致", batchNum, model.trim(),
                        vehBasicInfoPo.getModelCode());
            }
        } else {
            logger.warn("MES车辆数据批次号[{}]车型为空", batchNum);
        }
        String modelConfig = dataJson.getByPath("$.REQUEST.DATA.ITEM.MODEL_CONFIG", String[].class)[0];
        if (StrUtil.isNotBlank(modelConfig)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getModelConfigCode())) {
                vehBasicInfoPo.setModelConfigCode(modelConfig.trim().toUpperCase());
            } else if (!modelConfig.trim().equalsIgnoreCase(vehBasicInfoPo.getModelConfigCode())) {
                logger.warn("MES车辆数据批次号[{}]车型配置数据[{}]与原数据[{}]不一致", batchNum, modelConfig.trim(),
                        vehBasicInfoPo.getModelConfigCode());
            }
        } else {
            logger.warn("MES车辆数据批次号[{}]车型配置为空", batchNum);
        }
        String eolDateStr = dataJson.getByPath("$.REQUEST.DATA.ITEM.EOL_DATE", String[].class)[0];
        if (StrUtil.isNotBlank(modelConfig)) {
            DateTime eolDate = DateUtil.parse(eolDateStr, "yyyyMMdd");
            if (ObjUtil.isNull(vehBasicInfoPo.getEolTime())) {
                vehBasicInfoPo.setEolTime(eolDate);
                // 同时产生生命周期节点
                VehLifecyclePo vehLifecyclePo = new VehLifecyclePo();
                vehLifecyclePo.setVin(vin);
                vehLifecyclePo.setNode(VehicleLifecycleNode.EOL.name());
                vehLifecyclePo.setReachTime(eolDate);
                vehLifecyclePo.setSort(99);
                vehLifecycleDao.insertPo(vehLifecyclePo);
            } else if (eolDate.isBefore(vehBasicInfoPo.getEolTime()) || eolDate.isAfter(vehBasicInfoPo.getEolTime())) {
                logger.warn("MES车辆数据批次号[{}]下线日期数据[{}]与原数据[{}]不一致", batchNum, eolDateStr, DateUtil.formatDate(vehBasicInfoPo.getEolTime()));
            } else {
                logger.warn("MES车辆数据批次号[{}]下线日期为空", batchNum);
            }
        }
        if (ObjUtil.isNull(vehBasicInfoPo.getId())) {
            vehBasicInfoDao.insertPo(vehBasicInfoPo);
        } else {
            vehBasicInfoDao.updatePo(vehBasicInfoPo);
        }
        mesVehicleDataPo.setHandle(true);
        mesVehicleDataDao.updatePo(mesVehicleDataPo);
    }
}
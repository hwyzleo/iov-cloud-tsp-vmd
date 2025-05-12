package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleImportDataException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehImportDataDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehImportDataPo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆导入数据应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleImportDataAppService {

    private final VehImportDataDao vehImportDataDao;
    private final VehicleAppService vehicleAppService;
    private final VehiclePartAppService vehiclePartAppService;

    /**
     * 查询车辆导入数据
     *
     * @param batchNum  批次号
     * @param type      数据类型
     * @param version   数据版本
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 车辆导入数据列表
     */
    public List<VehImportDataPo> search(String batchNum, String type, String version, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("batchNum", batchNum);
        map.put("type", type);
        map.put("version", version);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehImportDataDao.selectPoByMap(map);
    }

    /**
     * 检查批次号是否唯一
     *
     * @param vehicleImportDataId 车辆导入数据ID
     * @param batchNum            批次号
     * @return 结果
     */
    public Boolean checkBatchNumUnique(Long vehicleImportDataId, String batchNum) {
        if (ObjUtil.isNull(vehicleImportDataId)) {
            vehicleImportDataId = -1L;
        }
        VehImportDataPo vehImportDataPo = getVehicleImportDataByBatchNum(batchNum);
        return !ObjUtil.isNotNull(vehImportDataPo) || vehImportDataPo.getId().longValue() == vehicleImportDataId.longValue();
    }

    /**
     * 根据主键ID获取车辆导入数据
     *
     * @param id 主键ID
     * @return 车辆导入数据
     */
    public VehImportDataPo getVehicleImportDataById(Long id) {
        return vehImportDataDao.selectPoById(id);
    }

    /**
     * 根据批次号获取车辆导入数据
     *
     * @param batchNum 批次号
     * @return 车辆导入数据
     */
    public VehImportDataPo getVehicleImportDataByBatchNum(String batchNum) {
        return vehImportDataDao.selectPoByBatchNum(batchNum);
    }

    /**
     * 新增车辆导入数据
     *
     * @param vehicleImportData 车辆导入数据
     * @return 结果
     */
    public int createVehicleImportData(VehImportDataPo vehicleImportData) {
        vehicleImportData.setHandle(false);
        return vehImportDataDao.insertPo(vehicleImportData);
    }

    /**
     * 修改车辆导入数据
     *
     * @param vehicleImportData 车辆导入数据
     * @return 结果
     */
    public int modifyVehicleImportData(VehImportDataPo vehicleImportData) {
        return vehImportDataDao.updatePo(vehicleImportData);
    }

    /**
     * 批量删除车辆导入数据
     *
     * @param ids 车辆导入数据ID数组
     * @return 结果
     */
    public int deleteVehicleImportDataByIds(Long[] ids) {
        return vehImportDataDao.batchPhysicalDeletePo(ids);
    }

    /**
     * 解析车辆导入数据
     *
     * @param batchNum 批次号
     */
    @Async
    public void parseVehicleImportData(String batchNum) {
        VehImportDataPo vehImportDataPo = vehImportDataDao.selectPoByBatchNum(batchNum);
        if (ObjUtil.isNull(vehImportDataPo)) {
            throw new VehicleImportDataException(batchNum, "车辆导入数据批次号不存在");
        }
        if (vehImportDataPo.getHandle()) {
            throw new VehicleImportDataException(batchNum, "车辆导入数据批次号已处理");
        }
        String dataStr = vehImportDataPo.getData();
        if (StrUtil.isBlank(dataStr)) {
            throw new VehicleImportDataException(batchNum, "车辆导入数据数据为空");
        }
        String type = vehImportDataPo.getType();
        if (StrUtil.isBlank(type)) {
            throw new VehicleImportDataException(batchNum, "车辆导入数据数据类型为空");
        }
        String version = vehImportDataPo.getVersion();
        if (StrUtil.isBlank(version)) {
            throw new VehicleImportDataException(batchNum, "车辆导入数据版本为空");
        }
        JSONObject dataJson = JSONUtil.parseObj(dataStr);
        vehImportDataPo.setHandle(true);
        vehImportDataPo.setDescription("");
        try {
            switch (type.toUpperCase()) {
                case "SIM" -> vehiclePartAppService.parseSimImportData(batchNum, version, dataJson);
                case "TBOX" -> vehiclePartAppService.parseTboxImportData(batchNum, version, dataJson);
                case "CCP" -> vehiclePartAppService.parseCcpImportData(batchNum, version, dataJson);
                case "BTM" -> vehiclePartAppService.parseBtmImportData(batchNum, version, dataJson);
                case "IDCM" -> vehiclePartAppService.parseIdcmImportData(batchNum, version, dataJson);
                case "PRODUCE" -> vehicleAppService.parseVehicleProduceImportData(batchNum, version, dataJson);
                case "EOL" -> vehicleAppService.parseVehicleEolImportData(batchNum, version, dataJson);
                default -> {
                    logger.warn("车辆导入数据批次号[{}]类型[{}]暂未处理", batchNum, vehImportDataPo.getType());
                    vehImportDataPo.setHandle(false);
                    vehImportDataPo.setDescription("未知类型：" + vehImportDataPo.getType());
                }
            }
        } catch (Exception e) {
            logger.error("车辆导入数据批次号[{}]处理失败", batchNum, e);
            vehImportDataPo.setHandle(false);
            vehImportDataPo.setDescription("车辆导入数据批次号[" + batchNum + "]处理失败:" + e.getMessage());
        }
        vehImportDataDao.updatePo(vehImportDataPo);
    }

}

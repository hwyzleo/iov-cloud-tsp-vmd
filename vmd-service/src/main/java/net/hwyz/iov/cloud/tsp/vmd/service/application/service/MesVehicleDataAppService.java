package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.MesVehicleDataDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.MesVehicleDataPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MES车辆数据应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MesVehicleDataAppService {

    private final MesVehicleDataDao mesVehicleDataDao;

    /**
     * 查询MES车辆数据
     *
     * @param batchNum  批次号
     * @param type      数据类型
     * @param version   数据版本
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 车辆平台列表
     */
    public List<MesVehicleDataPo> search(String batchNum, String type, String version, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("batchNum", batchNum);
        map.put("type", type);
        map.put("version", version);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return mesVehicleDataDao.selectPoByMap(map);
    }

    /**
     * 检查批次号是否唯一
     *
     * @param mesVehicleDataId MES车辆数据ID
     * @param batchNum         批次号
     * @return 结果
     */
    public Boolean checkBatchNumUnique(Long mesVehicleDataId, String batchNum) {
        if (ObjUtil.isNull(mesVehicleDataId)) {
            mesVehicleDataId = -1L;
        }
        MesVehicleDataPo mesVehicleDataPo = getMesVehicleDataByBatchNum(batchNum);
        return !ObjUtil.isNotNull(mesVehicleDataPo) || mesVehicleDataPo.getId().longValue() == mesVehicleDataId.longValue();
    }

    /**
     * 根据主键ID获取MES车辆数据
     *
     * @param id 主键ID
     * @return MES车辆数据
     */
    public MesVehicleDataPo getMesVehicleDataById(Long id) {
        return mesVehicleDataDao.selectPoById(id);
    }

    /**
     * 根据批次号获取MES车辆数据
     *
     * @param batchNum 批次号
     * @return MES车辆数据
     */
    public MesVehicleDataPo getMesVehicleDataByBatchNum(String batchNum) {
        return mesVehicleDataDao.selectPoByBatchNum(batchNum);
    }

    /**
     * 新增MES车辆数据
     *
     * @param mesVehicleData MES车辆数据
     * @return 结果
     */
    public int createMesVehicleData(MesVehicleDataPo mesVehicleData) {
        mesVehicleData.setHandle(false);
        return mesVehicleDataDao.insertPo(mesVehicleData);
    }

    /**
     * 修改MES车辆数据
     *
     * @param mesVehicleData MES车辆数据
     * @return 结果
     */
    public int modifyMesVehicleData(MesVehicleDataPo mesVehicleData) {
        return mesVehicleDataDao.updatePo(mesVehicleData);
    }

    /**
     * 批量删除MES车辆数据
     *
     * @param ids MES车辆数据ID数组
     * @return 结果
     */
    public int deleteMesVehicleDataByIds(Long[] ids) {
        return mesVehicleDataDao.batchPhysicalDeletePo(ids);
    }

}

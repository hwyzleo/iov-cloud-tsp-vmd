package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehModelConfigDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelConfigPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车型配置应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ModelConfigAppService {

    private final VehBasicInfoDao vehBasicInfoDao;
    private final VehModelConfigDao vehModelConfigDao;

    /**
     * 查询车型配置信息
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @param modelCode    车型代码
     * @param code         车型配置代码
     * @param name         车型配置名称
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @return 车系列表
     */
    public List<VehModelConfigPo> search(String platformCode, String seriesCode, String modelCode, String code, String name,
                                         Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("platformCode", platformCode);
        map.put("seriesCode", seriesCode);
        map.put("modelCode", modelCode);
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehModelConfigDao.selectPoByMap(map);
    }

    /**
     * 检查车型配置代码是否唯一
     *
     * @param modelConfigId 车型配置ID
     * @param code          车型配置代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long modelConfigId, String code) {
        if (ObjUtil.isNull(modelConfigId)) {
            modelConfigId = -1L;
        }
        VehModelConfigPo modelConfigPo = getModelConfigByCode(code);
        return !ObjUtil.isNotNull(modelConfigPo) || modelConfigPo.getId().longValue() == modelConfigId.longValue();
    }

    /**
     * 检查车型配置下是否存在车辆
     *
     * @param modelConfigId 车型配置ID
     * @return 结果
     */
    public Boolean checkModelConfigVehicleExist(Long modelConfigId) {
        VehModelConfigPo modelConfigPo = getModelConfigById(modelConfigId);
        Map<String, Object> map = new HashMap<>();
        map.put("modelConfigCode", modelConfigPo.getCode());
        return vehBasicInfoDao.countPoByMap(map) > 0;
    }

    /**
     * 根据主键ID获取车型配置信息
     *
     * @param id 主键ID
     * @return 车型信息
     */
    public VehModelConfigPo getModelConfigById(Long id) {
        return vehModelConfigDao.selectPoById(id);
    }

    /**
     * 根据车型配置代码获取车型配置信息
     *
     * @param code 车型配置代码
     * @return 车型配置信息
     */
    public VehModelConfigPo getModelConfigByCode(String code) {
        return vehModelConfigDao.selectPoByCode(code);
    }

    /**
     * 新增车型配置
     *
     * @param modelConfig 车型配置信息
     * @return 结果
     */
    public int createModelConfig(VehModelConfigPo modelConfig) {
        return vehModelConfigDao.insertPo(modelConfig);
    }

    /**
     * 修改车型配置
     *
     * @param modelConfig 车型配置信息
     * @return 结果
     */
    public int modifyModelConfig(VehModelConfigPo modelConfig) {
        return vehModelConfigDao.updatePo(modelConfig);
    }

    /**
     * 批量删除车型
     *
     * @param ids 车型ID数组
     * @return 结果
     */
    public int deleteModelConfigByIds(Long[] ids) {
        return vehModelConfigDao.batchPhysicalDeletePo(ids);
    }

}

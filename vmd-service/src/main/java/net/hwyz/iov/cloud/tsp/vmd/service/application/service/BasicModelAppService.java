package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicModelDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicModelFeatureCodeDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehModelConfigDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicModelFeatureCodePo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicModelPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础车型应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BasicModelAppService {

    private final VehBasicInfoDao vehBasicInfoDao;
    private final VehBasicModelDao vehBasicModelDao;
    private final VehModelConfigDao vehModelConfigDao;
    private final VehBasicModelFeatureCodeDao vehBasicModelFeatureCodeDao;

    /**
     * 查询基础车型信息
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @param modelCode    车型代码
     * @param code         基础车型代码
     * @param name         基础车型名称
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @return 基础车型列表
     */
    public List<VehBasicModelPo> search(String platformCode, String seriesCode, String modelCode, String code, String name,
                                        Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("platformCode", platformCode);
        map.put("seriesCode", seriesCode);
        map.put("modelCode", modelCode);
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehBasicModelDao.selectPoByMap(map);
    }

    /**
     * 查询基础车型信息
     *
     * @param basicModelCode 基础车型代码
     * @param familyCode     特征族代码
     * @param beginTime      开始时间
     * @param endTime        结束时间
     * @return 基础车型列表
     */
    public List<VehBasicModelFeatureCodePo> searchFeatureCode(String basicModelCode, String familyCode, Date beginTime,
                                                              Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("basicModelCode", basicModelCode);
        map.put("familyCode", familyCode);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehBasicModelFeatureCodeDao.selectPoByMap(map);
    }

    /**
     * 检查基础车型代码是否唯一
     *
     * @param basicModelId 基础车型ID
     * @param code         基础车型代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long basicModelId, String code) {
        if (ObjUtil.isNull(basicModelId)) {
            basicModelId = -1L;
        }
        VehBasicModelPo basicModelPo = getBasicModelByCode(code);
        return !ObjUtil.isNotNull(basicModelPo) || basicModelPo.getId().longValue() == basicModelId.longValue();
    }

    /**
     * 检查基础车型特征值代码是否唯一
     *
     * @param basicModelFeatureCodeId 基础车型特征值ID
     * @param basicModelCode          基础车型代码
     * @param familyCode              特征族代码
     * @return 结果
     */
    public Boolean checkFeatureCodeUnique(Long basicModelFeatureCodeId, String basicModelCode, String familyCode) {
        if (ObjUtil.isNull(basicModelFeatureCodeId)) {
            basicModelFeatureCodeId = -1L;
        }
        VehBasicModelFeatureCodePo basicModelFeatureCodePo = getBasicModelFeatureCodeByCode(basicModelCode, familyCode);
        return !ObjUtil.isNotNull(basicModelFeatureCodePo) || basicModelFeatureCodePo.getId().longValue() == basicModelFeatureCodeId.longValue();
    }

    /**
     * 检查基础车型下是否存在车型配置
     *
     * @param basicModelId 基础车型ID
     * @return 结果
     */
    public Boolean checkBasicModelModelConfigExist(Long basicModelId) {
        VehBasicModelPo basicModelPo = getBasicModelById(basicModelId);
        Map<String, Object> map = new HashMap<>();
        map.put("basicModelCode", basicModelPo.getCode());
        return vehModelConfigDao.countPoByMap(map) > 0;
    }

    /**
     * 检查基础车型下是否存在车辆
     *
     * @param basicModelId 基础车型ID
     * @return 结果
     */
    public Boolean checkBasicModelVehicleExist(Long basicModelId) {
        VehBasicModelPo basicModelPo = getBasicModelById(basicModelId);
        Map<String, Object> map = new HashMap<>();
        map.put("basicModelCode", basicModelPo.getCode());
        return vehBasicInfoDao.countPoByMap(map) > 0;
    }

    /**
     * 根据主键ID获取基础车型信息
     *
     * @param id 主键ID
     * @return 基础车型信息
     */
    public VehBasicModelPo getBasicModelById(Long id) {
        return vehBasicModelDao.selectPoById(id);
    }

    /**
     * 根据主键ID获取基础车型信息
     *
     * @param id 主键ID
     * @return 基础车型信息
     */
    public VehBasicModelFeatureCodePo getBasicModelFeatureCodeById(Long id) {
        return vehBasicModelFeatureCodeDao.selectPoById(id);
    }

    /**
     * 根据基础车型代码获取基础车型信息
     *
     * @param code 基础车型代码
     * @return 基础车型信息
     */
    public VehBasicModelPo getBasicModelByCode(String code) {
        return vehBasicModelDao.selectPoByCode(code);
    }

    /**
     * 根据基础车型代码获取基础车型信息
     *
     * @param basicModelCode 基础车型代码
     * @param familyCode     特征族代码
     * @return 基础车型特征值信息
     */
    public VehBasicModelFeatureCodePo getBasicModelFeatureCodeByCode(String basicModelCode, String familyCode) {
        return vehBasicModelFeatureCodeDao.selectPoByBasicModelCodeAndFamilyCode(basicModelCode, familyCode);
    }

    /**
     * 新增基础车型
     *
     * @param basicModel 基础车型信息
     * @return 结果
     */
    public int createBasicModel(VehBasicModelPo basicModel) {
        return vehBasicModelDao.insertPo(basicModel);
    }

    /**
     * 新增基础车型
     *
     * @param basicModelFeatureCode 基础车型特征值
     * @return 结果
     */
    public int createBasicModelFeatureCode(VehBasicModelFeatureCodePo basicModelFeatureCode) {
        return vehBasicModelFeatureCodeDao.insertPo(basicModelFeatureCode);
    }

    /**
     * 修改基础车型
     *
     * @param basicModel 基础车型信息
     * @return 结果
     */
    public int modifyBasicModel(VehBasicModelPo basicModel) {
        return vehBasicModelDao.updatePo(basicModel);
    }

    /**
     * 修改基础车型特征值
     *
     * @param basicModelFeatureCode 基础车型特征值
     * @return 结果
     */
    public int modifyBasicModelFeatureCode(VehBasicModelFeatureCodePo basicModelFeatureCode) {
        return vehBasicModelFeatureCodeDao.updatePo(basicModelFeatureCode);
    }

    /**
     * 批量删除基础车型
     *
     * @param ids 基础车型ID数组
     * @return 结果
     */
    public int deleteBasicModelByIds(Long[] ids) {
        return vehBasicModelDao.batchPhysicalDeletePo(ids);
    }

    /**
     * 批量删除基础车型
     *
     * @param ids 基础车型ID数组
     * @return 结果
     */
    public int deleteBasicModelFeatureCodeByIds(Long[] ids) {
        return vehBasicModelFeatureCodeDao.batchPhysicalDeletePo(ids);
    }

}

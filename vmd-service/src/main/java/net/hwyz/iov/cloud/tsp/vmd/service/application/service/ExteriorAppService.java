package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehExteriorDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehModelConfigDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehExteriorPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车身颜色应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExteriorAppService {

    private final VehExteriorDao vehExteriorDao;
    private final VehModelConfigDao vehModelConfigDao;

    /**
     * 查询车身颜色信息
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @param code         车身颜色代码
     * @param name         车身颜色名称
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @return 车身颜色列表
     */
    public List<VehExteriorPo> search(String platformCode, String seriesCode, String code, String name, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("platformCode", platformCode);
        map.put("seriesCode", seriesCode);
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehExteriorDao.selectPoByMap(map);
    }

    /**
     * 检查车身颜色代码是否唯一
     *
     * @param exteriorId 车身颜色ID
     * @param code       车身颜色代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long exteriorId, String code) {
        if (ObjUtil.isNull(exteriorId)) {
            exteriorId = -1L;
        }
        VehExteriorPo exteriorPo = getExteriorByCode(code);
        return !ObjUtil.isNotNull(exteriorPo) || exteriorPo.getId().longValue() == exteriorId.longValue();
    }

    /**
     * 检查车身颜色下是否存在车型配置
     *
     * @param exteriorId 车身颜色ID
     * @return 结果
     */
    public Boolean checkExteriorModelConfigExist(Long exteriorId) {
        VehExteriorPo exteriorPo = getExteriorById(exteriorId);
        Map<String, Object> map = new HashMap<>();
        map.put("exteriorCode", exteriorPo.getCode());
        return vehModelConfigDao.countPoByMap(map) > 0;
    }

    /**
     * 根据主键ID获取车身颜色信息
     *
     * @param id 主键ID
     * @return 车身颜色信息
     */
    public VehExteriorPo getExteriorById(Long id) {
        return vehExteriorDao.selectPoById(id);
    }

    /**
     * 根据车身颜色代码获取车身颜色信息
     *
     * @param code 车身颜色代码
     * @return 车身颜色信息
     */
    public VehExteriorPo getExteriorByCode(String code) {
        return vehExteriorDao.selectPoByCode(code);
    }

    /**
     * 新增车身颜色
     *
     * @param exterior 车身颜色信息
     * @return 结果
     */
    public int createExterior(VehExteriorPo exterior) {
        return vehExteriorDao.insertPo(exterior);
    }

    /**
     * 修改车身颜色
     *
     * @param exterior 车身颜色信息
     * @return 结果
     */
    public int modifyExterior(VehExteriorPo exterior) {
        return vehExteriorDao.updatePo(exterior);
    }

    /**
     * 批量删除车身颜色
     *
     * @param ids 车身颜色ID数组
     * @return 结果
     */
    public int deleteModelByIds(Long[] ids) {
        return vehExteriorDao.batchPhysicalDeletePo(ids);
    }

}

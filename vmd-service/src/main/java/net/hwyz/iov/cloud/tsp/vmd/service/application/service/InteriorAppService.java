package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehInteriorDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehModelConfigDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehInteriorPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内饰颜色应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InteriorAppService {

    private final VehInteriorDao vehInteriorDao;
    private final VehModelConfigDao vehModelConfigDao;

    /**
     * 查询内饰颜色信息
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @param code         内饰颜色代码
     * @param name         内饰颜色名称
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @return 内饰颜色列表
     */
    public List<VehInteriorPo> search(String platformCode, String seriesCode, String code, String name, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("platformCode", platformCode);
        map.put("seriesCode", seriesCode);
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehInteriorDao.selectPoByMap(map);
    }

    /**
     * 检查内饰颜色代码是否唯一
     *
     * @param interiorId 内饰颜色ID
     * @param code       内饰颜色代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long interiorId, String code) {
        if (ObjUtil.isNull(interiorId)) {
            interiorId = -1L;
        }
        VehInteriorPo interiorPo = getInteriorByCode(code);
        return !ObjUtil.isNotNull(interiorPo) || interiorPo.getId().longValue() == interiorId.longValue();
    }

    /**
     * 检查内饰颜色下是否存在车型配置
     *
     * @param interiorId 内饰颜色ID
     * @return 结果
     */
    public Boolean checkInteriorModelConfigExist(Long interiorId) {
        VehInteriorPo interiorPo = getInteriorById(interiorId);
        Map<String, Object> map = new HashMap<>();
        map.put("interiorCode", interiorPo.getCode());
        return vehModelConfigDao.countPoByMap(map) > 0;
    }

    /**
     * 根据主键ID获取内饰颜色信息
     *
     * @param id 主键ID
     * @return 内饰颜色信息
     */
    public VehInteriorPo getInteriorById(Long id) {
        return vehInteriorDao.selectPoById(id);
    }

    /**
     * 根据内饰颜色代码获取内饰颜色信息
     *
     * @param code 内饰颜色代码
     * @return 内饰颜色信息
     */
    public VehInteriorPo getInteriorByCode(String code) {
        return vehInteriorDao.selectPoByCode(code);
    }

    /**
     * 新增内饰颜色
     *
     * @param interior 内饰颜色信息
     * @return 结果
     */
    public int createInterior(VehInteriorPo interior) {
        return vehInteriorDao.insertPo(interior);
    }

    /**
     * 修改内饰颜色
     *
     * @param interior 内饰颜色信息
     * @return 结果
     */
    public int modifyInterior(VehInteriorPo interior) {
        return vehInteriorDao.updatePo(interior);
    }

    /**
     * 批量删除内饰颜色
     *
     * @param ids 内饰颜色ID数组
     * @return 结果
     */
    public int deleteModelByIds(Long[] ids) {
        return vehInteriorDao.batchPhysicalDeletePo(ids);
    }

}

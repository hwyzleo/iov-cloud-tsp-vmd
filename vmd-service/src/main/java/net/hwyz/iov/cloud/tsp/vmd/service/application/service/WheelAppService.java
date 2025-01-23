package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehModelConfigDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehWheelDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehWheelPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 轮胎轮毂应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WheelAppService {

    private final VehWheelDao vehWheelDao;
    private final VehModelConfigDao vehModelConfigDao;

    /**
     * 查询轮胎轮毂信息
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @param code         轮胎轮毂代码
     * @param name         轮胎轮毂名称
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @return 内饰颜色列表
     */
    public List<VehWheelPo> search(String platformCode, String seriesCode, String code, String name, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("platformCode", platformCode);
        map.put("seriesCode", seriesCode);
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehWheelDao.selectPoByMap(map);
    }

    /**
     * 检查轮胎轮毂代码是否唯一
     *
     * @param wheelId 轮胎轮毂ID
     * @param code    轮胎轮毂代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long wheelId, String code) {
        if (ObjUtil.isNull(wheelId)) {
            wheelId = -1L;
        }
        VehWheelPo wheelPo = getWheelByCode(code);
        return !ObjUtil.isNotNull(wheelPo) || wheelPo.getId().longValue() == wheelId.longValue();
    }

    /**
     * 检查轮胎轮毂下是否存在车型配置
     *
     * @param wheelId 轮胎轮毂ID
     * @return 结果
     */
    public Boolean checkInteriorModelConfigExist(Long wheelId) {
        VehWheelPo wheelPo = getWheelById(wheelId);
        Map<String, Object> map = new HashMap<>();
        map.put("wheelCode", wheelPo.getCode());
        return vehModelConfigDao.countPoByMap(map) > 0;
    }

    /**
     * 根据主键ID获取轮胎轮毂信息
     *
     * @param id 主键ID
     * @return 轮胎轮毂信息
     */
    public VehWheelPo getWheelById(Long id) {
        return vehWheelDao.selectPoById(id);
    }

    /**
     * 根据轮胎轮毂代码获取轮胎轮毂信息
     *
     * @param code 轮胎轮毂代码
     * @return 轮胎轮毂信息
     */
    public VehWheelPo getWheelByCode(String code) {
        return vehWheelDao.selectPoByCode(code);
    }

    /**
     * 新增轮胎轮毂
     *
     * @param wheel 轮胎轮毂信息
     * @return 结果
     */
    public int createWheel(VehWheelPo wheel) {
        return vehWheelDao.insertPo(wheel);
    }

    /**
     * 修改轮胎轮毂
     *
     * @param wheel 轮胎轮毂信息
     * @return 结果
     */
    public int modifyWheel(VehWheelPo wheel) {
        return vehWheelDao.updatePo(wheel);
    }

    /**
     * 批量删除轮胎轮毂
     *
     * @param ids 轮胎轮毂ID数组
     * @return 结果
     */
    public int deleteWheelByIds(Long[] ids) {
        return vehWheelDao.batchPhysicalDeletePo(ids);
    }

}

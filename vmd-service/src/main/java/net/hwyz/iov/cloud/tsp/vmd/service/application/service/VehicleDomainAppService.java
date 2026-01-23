package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehDomainDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehDomainPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车载域应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleDomainAppService {

    private final VehDomainDao vehDomainDao;

    /**
     * 查询车载域信息
     *
     * @param code      车载域代码
     * @param name      车载域名称
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 车辆平台列表
     */
    public List<VehDomainPo> search(String code, String name, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehDomainDao.selectPoByMap(map);
    }

    /**
     * 检查车辆工厂代码是否唯一
     *
     * @param manufacturerId 车辆工厂ID
     * @param code           车辆工厂代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long manufacturerId, String code) {
        if (ObjUtil.isNull(manufacturerId)) {
            manufacturerId = -1L;
        }
        VehDomainPo vehDomainPo = getVehicleDomainByCode(code);
        return !ObjUtil.isNotNull(vehDomainPo) || vehDomainPo.getId().longValue() == manufacturerId.longValue();
    }

    /**
     * 根据主键ID获取车载域信息
     *
     * @param id 主键ID
     * @return 车载域信息
     */
    public VehDomainPo getVehicleDomainById(Long id) {
        return vehDomainDao.selectPoById(id);
    }

    /**
     * 根据车载域代码获取车载域信息
     *
     * @param code 车载域代码
     * @return 车载域信息
     */
    public VehDomainPo getVehicleDomainByCode(String code) {
        return vehDomainDao.selectPoByCode(code);
    }

    /**
     * 新增车载域
     *
     * @param vehDomain 车载域信息
     * @return 结果
     */
    public int createVehicleDomain(VehDomainPo vehDomain) {
        return vehDomainDao.insertPo(vehDomain);
    }

    /**
     * 修改车载域
     *
     * @param vehDomain 车载域信息
     * @return 结果
     */
    public int modifyVehicleDomain(VehDomainPo vehDomain) {
        return vehDomainDao.updatePo(vehDomain);
    }

    /**
     * 批量删除车载域
     *
     * @param ids 车载域ID数组
     * @return 结果
     */
    public int deleteVehicleDomainByIds(Long[] ids) {
        return vehDomainDao.batchPhysicalDeletePo(ids);
    }

}

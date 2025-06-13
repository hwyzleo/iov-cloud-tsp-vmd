package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBrandDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBrandPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehManufacturerPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆品牌应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandAppService {

    private final VehBrandDao vehBrandDao;
    private final VehBasicInfoDao vehBasicInfoDao;

    /**
     * 查询车辆品牌信息
     *
     * @param code      车辆品牌代码
     * @param name      车辆品牌名称
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 车辆平台列表
     */
    public List<VehBrandPo> search(String code, String name, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return vehBrandDao.selectPoByMap(map);
    }

    /**
     * 检查车辆品牌代码是否唯一
     *
     * @param brandId 车辆品牌ID
     * @param code    车辆品牌代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long brandId, String code) {
        if (ObjUtil.isNull(brandId)) {
            brandId = -1L;
        }
        VehBrandPo brandPo = getBrandByCode(code);
        return !ObjUtil.isNotNull(brandPo) || brandPo.getId().longValue() == brandId.longValue();
    }

    /**
     * 检查车辆品牌下是否存在车辆
     *
     * @param brandId 车辆品牌ID
     * @return 结果
     */
    public Boolean checkBrandVehicleExist(Long brandId) {
        VehBrandPo brandPo = getBrandById(brandId);
        Map<String, Object> map = new HashMap<>();
        map.put("brandCode", brandPo.getCode());
        return vehBasicInfoDao.countPoByMap(map) > 0;
    }

    /**
     * 根据主键ID获取车辆品牌信息
     *
     * @param id 主键ID
     * @return 车辆品牌信息
     */
    public VehBrandPo getBrandById(Long id) {
        return vehBrandDao.selectPoById(id);
    }

    /**
     * 根据车辆品牌代码获取车辆品牌信息
     *
     * @param code 车辆品牌代码
     * @return 车辆品牌信息
     */
    public VehBrandPo getBrandByCode(String code) {
        return vehBrandDao.selectPoByCode(code);
    }

    /**
     * 新增车辆品牌
     *
     * @param brand 车辆品牌信息
     * @return 结果
     */
    public int createBrand(VehBrandPo brand) {
        return vehBrandDao.insertPo(brand);
    }

    /**
     * 修改车辆品牌
     *
     * @param brand 车辆品牌信息
     * @return 结果
     */
    public int modifyBrand(VehBrandPo brand) {
        return vehBrandDao.updatePo(brand);
    }

    /**
     * 批量删除车辆品牌
     *
     * @param ids 车辆品牌ID数组
     * @return 结果
     */
    public int deleteBrandByIds(Long[] ids) {
        return vehBrandDao.batchPhysicalDeletePo(ids);
    }

}

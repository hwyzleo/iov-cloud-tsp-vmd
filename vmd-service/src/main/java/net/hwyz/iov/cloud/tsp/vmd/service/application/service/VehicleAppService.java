package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.account.api.contract.Account;
import net.hwyz.iov.cloud.tsp.account.api.feign.service.ExAccountService;
import net.hwyz.iov.cloud.tsp.mno.api.feign.service.ExVehicleNetworkService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.event.publish.VehiclePublish;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.repository.VehicleRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleHasBindOrderException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehiclePresetOwnerNotMatchException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleWithoutPresetOwnerException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehDetailInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehPresetOwnerDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehDetailInfoPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPresetOwnerPo;
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
    private final VehDetailInfoDao vehDetailInfoDao;
    private final ExAccountService exAccountService;
    private final VehicleRepository vehicleRepository;
    private final VehPresetOwnerDao vehPresetOwnerDao;
    private final VehicleLifecycleAppService vehicleLifecycleAppService;

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
    public List<VehBasicInfoPo> search(String vin, String modelConfigCode, Date beginTime, Date endTime, Boolean isEol, Boolean isOrder) {
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
     * 根据车架号获取车辆详细信息
     *
     * @param vin 车架号
     * @return 车辆详细信息
     */
    public VehDetailInfoPo getVehicleDetailByVin(String vin) {
        return vehDetailInfoDao.selectPoByVin(vin);
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
     * 修改车辆
     *
     * @param vehBasicInfo 车辆信息
     * @return 结果
     */
    public int modifyVehicle(VehBasicInfoPo vehBasicInfo) {
        return vehBasicInfoDao.updatePo(vehBasicInfo);
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
                vehicleLifecycleAppService.deleteVehicleLifecycleByVin(vehiclePo.getVin());
            }
        }
        return vehBasicInfoDao.batchPhysicalDeletePo(ids);
    }

    /**
     * 绑定订单
     *
     * @param vin      车架号
     * @param orderNum 订单编号
     */
    public void bindOrder(String vin, String orderNum) {
        logger.info("车辆[{}]绑定订单[{}]", vin, orderNum);
        VehicleDo vehicleDo = vehicleRepository.getByVin(vin);
        if (vehicleDo.hasOrder()) {
            throw new VehicleHasBindOrderException(vin, vehicleDo.getOrderNum());
        }
        vehicleDo.bindOrder(orderNum);
        vehicleRepository.save(vehicleDo);
        vehicleLifecycleAppService.recordBindOrderNode(vin, orderNum);
    }

    /**
     * 检查车辆预设车主
     *
     * @param vin       车架号
     * @param accountId 账号ID
     */
    public void checkVehiclePresetOwner(String vin, String accountId) {
        List<VehPresetOwnerPo> vehPresetOwnerPoList = vehPresetOwnerDao.selectPoByExample(VehPresetOwnerPo.builder().vin(vin).build());
        if (vehPresetOwnerPoList.isEmpty()) {
            throw new VehicleWithoutPresetOwnerException(vin);
        }
        VehPresetOwnerPo vehPresetOwnerPo = vehPresetOwnerPoList.get(0);
        Account account = exAccountService.getAccountInfo(accountId);
        if (!vehPresetOwnerPo.getMobile().equals(account.getMobile()) ||
                !vehPresetOwnerPo.getCountryRegionCode().equals(account.getCountryRegionCode())) {
            throw new VehiclePresetOwnerNotMatchException(vin, account.getCountryRegionCode(), account.getMobile(),
                    vehPresetOwnerPo.getCountryRegionCode(), vehPresetOwnerPo.getMobile());
        }
    }
}
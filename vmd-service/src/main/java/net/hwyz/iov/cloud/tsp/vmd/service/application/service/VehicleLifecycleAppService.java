package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.account.api.contract.Account;
import net.hwyz.iov.cloud.tsp.account.api.feign.service.ExAccountService;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.repository.VehicleRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleHasActivatedException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehiclePresetOwnerNotMatchException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleWithoutPresetOwnerException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehPresetOwnerDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPresetOwnerPo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车辆生命周期应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleLifecycleAppService {

    private final ExAccountService exAccountService;
    private final VehicleRepository vehicleRepository;
    private final VehPresetOwnerDao vehPresetOwnerDao;

    /**
     * 车辆激活
     *
     * @param vin       车架号
     * @param accountId 账户ID
     */
    public void vehicleActive(String vin, String accountId) {
        logger.info("用户[{}]激活车辆[{}]", accountId, vin);
        VehicleDo vehicleDo = vehicleRepository.getByVin(vin);
        if (vehicleDo.isActive()) {
            throw new VehicleHasActivatedException(vin);
        }
        checkVehiclePresetOwner(vin, accountId);
        vehicleDo.activate();
        vehicleRepository.save(vehicleDo);
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

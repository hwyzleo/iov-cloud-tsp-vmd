package net.hwyz.iov.cloud.tsp.vmd.service.facade.mpt;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.audit.annotation.Log;
import net.hwyz.iov.cloud.framework.audit.enums.BusinessType;
import net.hwyz.iov.cloud.framework.common.web.controller.BaseController;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.framework.security.annotation.RequiresPermissions;
import net.hwyz.iov.cloud.framework.security.util.SecurityUtils;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleConfigItemMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleConfigMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.VehicleConfigMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehicleConfigAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehicleConfigItemMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehicleConfigMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehicleConfigItemPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehicleConfigPo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆配置相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/vehicleConfig")
public class VehicleConfigMptController extends BaseController implements VehicleConfigMptApi {

    private final VehicleConfigAppService vehicleConfigAppService;

    /**
     * 分页查询车辆配置
     *
     * @param vehicleConfig 车辆配置
     * @return 车辆配置列表
     */
    @RequiresPermissions("iov:configCenter:vehicleConfig:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(VehicleConfigMpt vehicleConfig) {
        logger.info("管理后台用户[{}]分页查询车辆配置", SecurityUtils.getUsername());
        startPage();
        List<VehicleConfigPo> vehicleConfigPoList = vehicleConfigAppService.search(vehicleConfig.getVin(), getBeginTime(vehicleConfig),
                getEndTime(vehicleConfig));
        List<VehicleConfigMpt> vehicleConfigMptList = VehicleConfigMptAssembler.INSTANCE.fromPoList(vehicleConfigPoList);
        return getDataTable(vehicleConfigPoList, vehicleConfigMptList);
    }

    /**
     * 分页查询车辆配置项
     *
     * @param vin               车架号
     * @param vehicleConfigItem 车辆配置项
     * @return 车辆配置项列表
     */
    @RequiresPermissions("iov:configCenter:vehicleConfig:list")
    @Override
    @GetMapping(value = "/{vin}/configItem/list")
    public TableDataInfo listConfigItem(@PathVariable String vin, VehicleConfigItemMpt vehicleConfigItem) {
        logger.info("管理后台用户[{}]分页查询车辆[{}]配置项", SecurityUtils.getUsername(), vin);
        startPage();
        List<VehicleConfigItemPo> vehicleConfigItemPoList = vehicleConfigAppService.searchConfigItem(vin,
                getBeginTime(vehicleConfigItem), getEndTime(vehicleConfigItem));
        List<VehicleConfigItemMpt> vehicleConfigItemMptList = VehicleConfigItemMptAssembler.INSTANCE.fromPoList(vehicleConfigItemPoList);
        return getDataTable(vehicleConfigItemPoList, vehicleConfigItemMptList);
    }

    /**
     * 导出车辆配置
     *
     * @param response      响应
     * @param vehicleConfig 车辆配置
     */
    @Log(title = "车辆配置管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("iov:configCenter:vehicleConfig:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleConfigMpt vehicleConfig) {
        logger.info("管理后台用户[{}]导出车辆配置", SecurityUtils.getUsername());
    }

    /**
     * 根据车辆配置ID获取车辆配置
     *
     * @param vehicleConfigId 车辆配置ID
     * @return 车辆配置
     */
    @RequiresPermissions("iov:configCenter:vehicleConfig:query")
    @Override
    @GetMapping(value = "/{vehicleConfigId}")
    public AjaxResult getInfo(@PathVariable Long vehicleConfigId) {
        logger.info("管理后台用户[{}]根据车辆配置ID[{}]获取车辆配置", SecurityUtils.getUsername(), vehicleConfigId);
        VehicleConfigPo vehicleConfigPo = vehicleConfigAppService.getVehicleConfigById(vehicleConfigId);
        return success(VehicleConfigMptAssembler.INSTANCE.fromPo(vehicleConfigPo));
    }

    /**
     * 根据车辆配置项ID获取车辆配置项
     *
     * @param vin                 车架号
     * @param vehicleConfigItemId 车辆配置项ID
     * @return 车辆配置项
     */
    @RequiresPermissions("iov:configCenter:vehicleConfig:query")
    @Override
    @GetMapping(value = "/{vin}/configItem/{vehicleConfigItemId}")
    public AjaxResult getConfigItemInfo(@PathVariable String vin, @PathVariable Long vehicleConfigItemId) {
        logger.info("管理后台用户[{}]根据车辆[{}]配置项ID[{}]获取车辆配置项", SecurityUtils.getUsername(), vin, vehicleConfigItemId);
        VehicleConfigItemPo vehicleConfigItemPo = vehicleConfigAppService.getVehicleConfigItemById(vin, vehicleConfigItemId);
        return success(VehicleConfigItemMptAssembler.INSTANCE.fromPo(vehicleConfigItemPo));
    }
}

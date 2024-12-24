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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleLifecycleMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.VehicleMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehicleAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehicleLifecycleMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehicleMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehLifecyclePo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/vehicle")
public class VehicleMptController extends BaseController implements VehicleMptApi {

    private final VehicleAppService vehicleAppService;

    /**
     * 分页查询车辆信息
     *
     * @param vehicle 车辆信息
     * @return 车辆信息列表
     */
    @RequiresPermissions("completeVehicle:vehicle:info:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(VehicleMpt vehicle) {
        logger.info("管理后台用户[{}]分页查询车辆信息", SecurityUtils.getUsername());
        startPage();
        List<VehBasicInfoPo> vehBasicInfoPoList = vehicleAppService.search(vehicle.getVin(), vehicle.getModelConfigCode(),
                getBeginTime(vehicle), getEndTime(vehicle), null, null);
        List<VehicleMpt> vehicleMptList = VehicleMptAssembler.INSTANCE.fromPoList(vehBasicInfoPoList);
        return getDataTable(vehBasicInfoPoList, vehicleMptList);
    }

    /**
     * 分页查询可分配车辆信息
     *
     * @param vehicle 车辆信息
     * @return 车辆信息列表
     */
    @RequiresPermissions("completeVehicle:vehicle:info:list")
    @Override
    @GetMapping(value = "/listAssignable")
    public TableDataInfo listAssignable(VehicleMpt vehicle) {
        logger.info("管理后台用户[{}]分页查询可分配车辆信息", SecurityUtils.getUsername());
        startPage();
        List<VehBasicInfoPo> vehBasicInfoPoList = vehicleAppService.search(vehicle.getVin(), vehicle.getModelConfigCode(),
                getBeginTime(vehicle), getEndTime(vehicle), true, false);
        List<VehicleMpt> vehicleMptList = VehicleMptAssembler.INSTANCE.fromPoList(vehBasicInfoPoList);
        return getDataTable(vehBasicInfoPoList, vehicleMptList);
    }

    /**
     * 分页查询车辆生命周期
     *
     * @param vin 车辆VIN号
     * @return 车辆生命周期列表
     */
    @RequiresPermissions("completeVehicle:vehicle:info:query")
    @Override
    @GetMapping(value = "/{vin}/lifecycle")
    public List<VehicleLifecycleMpt> listLifecycle(@PathVariable String vin) {
        logger.info("管理后台用户[{}]分页查询车辆[{}]生命周期", SecurityUtils.getUsername(), vin);
        List<VehLifecyclePo> vehLifecyclePoList = vehicleAppService.listLifecycle(vin);
        return VehicleLifecycleMptAssembler.INSTANCE.fromPoList(vehLifecyclePoList);
    }

    /**
     * 导出车辆信息
     *
     * @param response 响应
     * @param vehicle  车辆信息
     */
    @Log(title = "车辆管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:vehicle:info:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleMpt vehicle) {
        logger.info("管理后台用户[{}]导出车辆信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车辆ID获取车辆信息
     *
     * @param vehicleId 车辆ID
     * @return 车辆信息
     */
    @RequiresPermissions("completeVehicle:vehicle:info:query")
    @Override
    @GetMapping(value = "/{vehicleId}")
    public AjaxResult getInfo(@PathVariable Long vehicleId) {
        logger.info("管理后台用户[{}]根据车辆ID[{}]获取车辆信息", SecurityUtils.getUsername(), vehicleId);
        VehBasicInfoPo vehBasicInfoPo = vehicleAppService.getVehicleById(vehicleId);
        return success(VehicleMptAssembler.INSTANCE.fromPo(vehBasicInfoPo));
    }

    /**
     * 新增车辆信息
     *
     * @param vehicle 车辆信息
     * @return 结果
     */
    @Log(title = "车辆管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:vehicle:info:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody VehicleMpt vehicle) {
        logger.info("管理后台用户[{}]新增车辆信息[{}]", SecurityUtils.getUsername(), vehicle.getVin());
        if (!vehicleAppService.checkVinUnique(vehicle.getId(), vehicle.getVin())) {
            return error("新增车辆'" + vehicle.getVin() + "'失败，车辆车架号已存在");
        }
        VehBasicInfoPo vehBasicInfoPo = VehicleMptAssembler.INSTANCE.toPo(vehicle);
        vehBasicInfoPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(vehicleAppService.createVehicle(vehBasicInfoPo));
    }

    /**
     * 新增车辆生命周期
     *
     * @param vin              车架号
     * @param vehicleLifecycle 车辆生命周期
     * @return 结果
     */
    @Log(title = "车辆管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:info:edit")
    @Override
    @PostMapping("/{vin}/lifecycle")
    public AjaxResult addLifecycle(@PathVariable String vin, @Validated @RequestBody VehicleLifecycleMpt vehicleLifecycle) {
        logger.info("管理后台用户[{}]新增车辆[{}]生命周期[{}]", SecurityUtils.getUsername(), vin, vehicleLifecycle.getNode());
        VehLifecyclePo vehLifecyclePo = VehicleLifecycleMptAssembler.INSTANCE.toPo(vehicleLifecycle);
        vehLifecyclePo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(vehicleAppService.createVehicleLifecycle(vehLifecyclePo));
    }

    /**
     * 修改保存车辆信息
     *
     * @param vehicle 车辆信息
     * @return 结果
     */
    @Log(title = "车辆管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:info:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody VehicleMpt vehicle) {
        logger.info("管理后台用户[{}]修改保存车辆信息[{}]", SecurityUtils.getUsername(), vehicle.getVin());
        if (!vehicleAppService.checkVinUnique(vehicle.getId(), vehicle.getVin())) {
            return error("修改保存车辆'" + vehicle.getVin() + "'失败，车辆车架号已存在");
        }
        VehBasicInfoPo vehBasicInfoPo = VehicleMptAssembler.INSTANCE.toPo(vehicle);
        vehBasicInfoPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(vehicleAppService.modifyVehicle(vehBasicInfoPo));
    }

    /**
     * 修改保存车辆生命周期
     *
     * @param vin              车架号
     * @param vehicleLifecycle 车辆生命周期
     * @return 结果
     */
    @Log(title = "车辆管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:info:edit")
    @Override
    @PutMapping("/{vin}/lifecycle")
    public AjaxResult editLifecycle(@PathVariable String vin, @Validated @RequestBody VehicleLifecycleMpt vehicleLifecycle) {
        logger.info("管理后台用户[{}]修改保存车辆[{}]生命周期[{}]", SecurityUtils.getUsername(), vin, vehicleLifecycle.getNode());
        VehLifecyclePo vehLifecyclePo = VehicleLifecycleMptAssembler.INSTANCE.toPo(vehicleLifecycle);
        vehLifecyclePo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(vehicleAppService.modifyVehicleLifecycle(vehLifecyclePo));
    }

    /**
     * 删除车辆信息
     *
     * @param vehicleIds 车辆ID数组
     * @return 结果
     */
    @Log(title = "车辆管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:vehicle:info:remove")
    @Override
    @DeleteMapping("/{vehicleIds}")
    public AjaxResult remove(@PathVariable Long[] vehicleIds) {
        logger.info("管理后台用户[{}]删除车辆信息[{}]", SecurityUtils.getUsername(), vehicleIds);
        return toAjax(vehicleAppService.deleteVehicleByIds(vehicleIds));
    }

    /**
     * 删除车辆生命周期
     *
     * @param lifecycleIds 车辆生命周期ID数组
     * @return 结果
     */
    @Log(title = "车辆管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:info:edit")
    @Override
    @DeleteMapping("/{vin}/lifecycle/{lifecycleIds}")
    public AjaxResult removeLifecycle(@PathVariable String vin, @PathVariable Long[] lifecycleIds) {
        logger.info("管理后台用户[{}]删除车辆[{}]生命周期节点[{}]", SecurityUtils.getUsername(), vin, lifecycleIds);
        return toAjax(vehicleAppService.deleteVehicleLifecycleByIds(lifecycleIds));
    }
}

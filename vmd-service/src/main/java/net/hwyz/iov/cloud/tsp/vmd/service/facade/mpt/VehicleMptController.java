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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.VehicleMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehicleAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehicleMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
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
    @RequiresPermissions("tsp:vmd:vehicle:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(VehicleMpt vehicle) {
        logger.info("管理后台用户[{}]分页查询车辆信息", SecurityUtils.getUsername());
        startPage();
        List<VehBasicInfoPo> vehBasicInfoPoList = vehicleAppService.search(vehicle.getVin(), getBeginTime(vehicle), getEndTime(vehicle));
        List<VehicleMpt> vehicleMptList = VehicleMptAssembler.INSTANCE.fromPoList(vehBasicInfoPoList);
        return getDataTable(vehBasicInfoPoList, vehicleMptList);
    }

    /**
     * 导出车辆信息
     *
     * @param response 响应
     * @param vehicle  车辆信息
     */
    @Log(title = "车辆管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("tsp:vmd:vehicle:export")
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
    @RequiresPermissions("tsp:vmd:vehicle:query")
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
    @RequiresPermissions("tsp:vmd:vehicle:add")
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
     * 修改保存车辆信息
     *
     * @param vehicle 车辆信息
     * @return 结果
     */
    @Log(title = "车辆管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("tsp:vmd:vehicle:edit")
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
     * 删除车辆信息
     *
     * @param vehicleIds 车辆ID数组
     * @return 结果
     */
    @Log(title = "车辆管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("tsp:vmd:vehicle:remove")
    @Override
    @DeleteMapping("/{vehicleIds}")
    public AjaxResult remove(@PathVariable Long[] vehicleIds) {
        logger.info("管理后台用户[{}]删除车辆信息[{}]", SecurityUtils.getUsername(), vehicleIds);
        return toAjax(vehicleAppService.deleteVehicleByIds(vehicleIds));
    }

}

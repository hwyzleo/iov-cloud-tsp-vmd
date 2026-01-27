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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehiclePartMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.VehiclePartMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehiclePartAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehiclePartMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehiclePartPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆零件相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/vehiclePart")
public class VehiclePartMptController extends BaseController implements VehiclePartMptApi {

    private final VehiclePartAppService vehiclePartAppService;

    /**
     * 分页查询车辆零件
     *
     * @param vehiclePart 车辆零件
     * @return 车辆零件列表
     */
    @RequiresPermissions("completeVehicle:vehicle:vehiclePart:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(VehiclePartMpt vehiclePart) {
        logger.info("管理后台用户[{}]分页查询车辆零件", SecurityUtils.getUsername());
        startPage();
        List<VehiclePartPo> vehiclePartPoList = vehiclePartAppService.search(vehiclePart.getVin(), vehiclePart.getPn(),
                getBeginTime(vehiclePart), getEndTime(vehiclePart));
        List<VehiclePartMpt> vehiclePartMptList = VehiclePartMptAssembler.INSTANCE.fromPoList(vehiclePartPoList);
        return getDataTable(vehiclePartPoList, vehiclePartMptList);
    }

    /**
     * 导出车辆零件
     *
     * @param response    响应
     * @param vehiclePart 车辆零件
     */
    @Log(title = "车辆零件管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:vehicle:vehiclePart:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehiclePartMpt vehiclePart) {
        logger.info("管理后台用户[{}]导出车辆零件", SecurityUtils.getUsername());
    }

    /**
     * 根据车辆零件ID获取车辆零件
     *
     * @param vehiclePartId 车辆零件ID
     * @return 车辆零件
     */
    @RequiresPermissions("completeVehicle:vehicle:vehiclePart:query")
    @Override
    @GetMapping(value = "/{vehiclePartId}")
    public AjaxResult getInfo(@PathVariable Long vehiclePartId) {
        logger.info("管理后台用户[{}]根据车辆零件ID[{}]获取车辆零件", SecurityUtils.getUsername(), vehiclePartId);
        VehiclePartPo vehiclePartPo = vehiclePartAppService.getVehiclePartById(vehiclePartId);
        return success(VehiclePartMptAssembler.INSTANCE.fromPo(vehiclePartPo));
    }

    /**
     * 新增车辆零件
     *
     * @param vehiclePart 车辆零件
     * @return 结果
     */
    @Log(title = "车辆零件管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:vehicle:vehiclePart:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody VehiclePartMpt vehiclePart) {
        logger.info("管理后台用户[{}]新增车辆[{}]零件[{}:{}]", SecurityUtils.getUsername(), vehiclePart.getVin(), vehiclePart.getPn(), vehiclePart.getSn());
        if (!vehiclePartAppService.checkPnAndSnUnique(vehiclePart.getId(), vehiclePart.getPn(), vehiclePart.getSn())) {
            return error("新增车辆零件'" + vehiclePart.getPn() + "'失败，车辆零件已存在");
        }
        VehiclePartPo vehiclePartPo = VehiclePartMptAssembler.INSTANCE.toPo(vehiclePart);
        vehiclePartPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(vehiclePartAppService.createVehiclePart(vehiclePartPo));
    }

    /**
     * 修改保存车辆零件
     *
     * @param vehiclePart 车辆零件
     * @return 结果
     */
    @Log(title = "车辆零件管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:vehiclePart:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody VehiclePartMpt vehiclePart) {
        logger.info("管理后台用户[{}]修改保存车辆[{}]零件[{}:{}]", SecurityUtils.getUsername(), vehiclePart.getVin(), vehiclePart.getPn(), vehiclePart.getSn());
        if (!vehiclePartAppService.checkPnAndSnUnique(vehiclePart.getId(), vehiclePart.getPn(), vehiclePart.getSn())) {
            return error("修改保存车辆零件'" + vehiclePart.getPn() + "'失败，车辆零件已存在");
        }
        VehiclePartPo vehiclePartPo = VehiclePartMptAssembler.INSTANCE.toPo(vehiclePart);
        vehiclePartPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(vehiclePartAppService.modifyVehiclePart(vehiclePartPo));
    }

    /**
     * 删除车辆零件
     *
     * @param vehiclePartIds 车辆零件ID数组
     * @return 结果
     */
    @Log(title = "车辆零件管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:vehicle:vehiclePart:remove")
    @Override
    @DeleteMapping("/{vehiclePartIds}")
    public AjaxResult remove(@PathVariable Long[] vehiclePartIds) {
        logger.info("管理后台用户[{}]删除车辆零件[{}]", SecurityUtils.getUsername(), vehiclePartIds);
        return toAjax(vehiclePartAppService.deleteVehiclePartByIds(vehiclePartIds));
    }

}

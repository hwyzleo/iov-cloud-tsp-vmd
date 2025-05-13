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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleImportDataMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.VehicleImportDataMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehicleImportDataAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehicleImportDataMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehImportDataPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆导入数据相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/vehicleImportData")
public class VehicleImportDataMptController extends BaseController implements VehicleImportDataMptApi {

    private final VehicleImportDataAppService vehicleImportDataAppService;

    /**
     * 分页查询车辆导入数据
     *
     * @param vehicleImportData 车辆导入数据
     * @return 车辆导入数据列表
     */
    @RequiresPermissions("completeVehicle:vehicle:importData:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(VehicleImportDataMpt vehicleImportData) {
        logger.info("管理后台用户[{}]分页查询车辆导入数据", SecurityUtils.getUsername());
        startPage();
        List<VehImportDataPo> vehImportDataPoList = vehicleImportDataAppService.search(vehicleImportData.getBatchNum(),
                vehicleImportData.getType(), vehicleImportData.getVersion(), getBeginTime(vehicleImportData), getEndTime(vehicleImportData));
        List<VehicleImportDataMpt> mesVehicleDataMptList = VehicleImportDataMptAssembler.INSTANCE.fromPoList(vehImportDataPoList);
        return getDataTable(vehImportDataPoList, mesVehicleDataMptList);
    }

    /**
     * 导出车辆导入数据
     *
     * @param response          响应
     * @param vehicleImportData 车辆导入数据
     */
    @Log(title = "车辆导入数据管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:vehicle:importData:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleImportDataMpt vehicleImportData) {
        logger.info("管理后台用户[{}]导出车辆导入数据", SecurityUtils.getUsername());
    }

    /**
     * 根据车辆导入数据ID获取车辆导入数据
     *
     * @param vehicleImportDataId 车辆导入数据ID
     * @return 车辆导入数据
     */
    @RequiresPermissions("completeVehicle:vehicle:importData:query")
    @Override
    @GetMapping(value = "/{vehicleImportDataId}")
    public AjaxResult getInfo(@PathVariable Long vehicleImportDataId) {
        logger.info("管理后台用户[{}]根据车辆导入数据ID[{}]获取车辆导入数据", SecurityUtils.getUsername(), vehicleImportDataId);
        VehImportDataPo vehImportDataPo = vehicleImportDataAppService.getVehicleImportDataById(vehicleImportDataId);
        return success(VehicleImportDataMptAssembler.INSTANCE.fromPo(vehImportDataPo));
    }

    /**
     * 新增车辆导入数据
     *
     * @param vehicleImportData 车辆导入数据
     * @return 结果
     */
    @Log(title = "车辆导入数据管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:vehicle:importData:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody VehicleImportDataMpt vehicleImportData) {
        logger.info("管理后台用户[{}]新增车辆导入数据[{}]", SecurityUtils.getUsername(), vehicleImportData.getBatchNum());
        if (!vehicleImportDataAppService.checkBatchNumUnique(vehicleImportData.getId(), vehicleImportData.getBatchNum())) {
            return error("新增车辆导入数据'" + vehicleImportData.getBatchNum() + "'失败，批次号已存在");
        }
        VehImportDataPo vehImportDataPo = VehicleImportDataMptAssembler.INSTANCE.toPo(vehicleImportData);
        vehImportDataPo.setCreateBy(SecurityUtils.getUserId().toString());
        AjaxResult result = toAjax(vehicleImportDataAppService.createVehicleImportData(vehImportDataPo));
        try {
            vehicleImportDataAppService.parseVehicleImportData(vehImportDataPo.getBatchNum());
        } catch (Exception e) {
            return error("车辆导入数据'" + vehicleImportData.getBatchNum() + "'解析异常");
        }
        return result;
    }

    /**
     * 修改保存车辆导入数据
     *
     * @param vehicleImportData 车辆导入数据
     * @return 结果
     */
    @Log(title = "车辆导入数据管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:importData:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody VehicleImportDataMpt vehicleImportData) {
        logger.info("管理后台用户[{}]修改保存车辆导入数据[{}]", SecurityUtils.getUsername(), vehicleImportData.getBatchNum());
        if (!vehicleImportDataAppService.checkBatchNumUnique(vehicleImportData.getId(), vehicleImportData.getBatchNum())) {
            return error("修改保存车辆导入数据'" + vehicleImportData.getBatchNum() + "'失败，批次号已存在");
        }
        VehImportDataPo vehImportDataPo = VehicleImportDataMptAssembler.INSTANCE.toPo(vehicleImportData);
        vehImportDataPo.setModifyBy(SecurityUtils.getUserId().toString());
        AjaxResult result = toAjax(vehicleImportDataAppService.modifyVehicleImportData(vehImportDataPo));
        try {
            vehicleImportDataAppService.parseVehicleImportData(vehImportDataPo.getBatchNum());
        } catch (Exception e) {
            return error("车辆导入数据'" + vehicleImportData.getBatchNum() + "'解析异常");
        }
        return result;
    }

    /**
     * 删除车辆导入数据
     *
     * @param vehicleImportDataIds 车辆导入数据ID数组
     * @return 结果
     */
    @Log(title = "车辆导入数据管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:vehicle:importData:remove")
    @Override
    @DeleteMapping("/{vehicleImportDataIds}")
    public AjaxResult remove(@PathVariable Long[] vehicleImportDataIds) {
        logger.info("管理后台用户[{}]删除车辆导入数据[{}]", SecurityUtils.getUsername(), vehicleImportDataIds);
        return toAjax(vehicleImportDataAppService.deleteVehicleImportDataByIds(vehicleImportDataIds));
    }
}

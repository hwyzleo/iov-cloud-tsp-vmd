package net.hwyz.iov.cloud.tsp.vmd.service.facade.mpt;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.audit.annotation.Log;
import net.hwyz.iov.cloud.framework.audit.enums.BusinessType;
import net.hwyz.iov.cloud.framework.common.enums.DeviceItem;
import net.hwyz.iov.cloud.framework.common.web.controller.BaseController;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.framework.security.annotation.RequiresPermissions;
import net.hwyz.iov.cloud.framework.security.util.SecurityUtils;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.DeviceMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.DeviceMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.DeviceAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.DeviceMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.DevicePo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 设备信息相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/device")
public class DeviceMptController extends BaseController implements DeviceMptApi {

    private final DeviceAppService deviceAppService;

    /**
     * 分页查询设备信息
     *
     * @param device 设备信息
     * @return 设备信息列表
     */
    @RequiresPermissions("completeVehicle:vehicle:device:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(DeviceMpt device) {
        logger.info("管理后台用户[{}]分页查询设备信息", SecurityUtils.getUsername());
        startPage();
        List<DevicePo> devicePoList = deviceAppService.search(device.getCode(), device.getName(), device.getFuncDomain(),
                getBeginTime(device), getEndTime(device));
        List<DeviceMpt> deviceMptList = DeviceMptAssembler.INSTANCE.fromPoList(devicePoList);
        return getDataTable(devicePoList, deviceMptList);
    }

    /**
     * 获取所有设备项
     *
     * @return 设备类型列表
     */
    @RequiresPermissions("completeVehicle:vehicle:device:list")
    @Override
    @GetMapping(value = "/listAllDeviceItem")
    public AjaxResult listAllDeviceItem() {
        logger.info("管理后台用户[{}]获取所有设备项", SecurityUtils.getUsername());
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeviceItem deviceItem : DeviceItem.values()) {
            list.add(Map.of("code", deviceItem.name(), "label", deviceItem.label));
        }
        return success(list);
    }

    /**
     * 导出设备信息
     *
     * @param response 响应
     * @param device   设备信息
     */
    @Log(title = "设备信息管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:vehicle:device:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceMpt device) {
        logger.info("管理后台用户[{}]导出设备信息", SecurityUtils.getUsername());
    }

    /**
     * 根据设备信息ID获取设备信息
     *
     * @param deviceId 设备信息ID
     * @return 设备信息信息
     */
    @RequiresPermissions("completeVehicle:vehicle:device:query")
    @Override
    @GetMapping(value = "/{deviceId}")
    public AjaxResult getInfo(@PathVariable Long deviceId) {
        logger.info("管理后台用户[{}]根据设备信息ID[{}]获取设备信息", SecurityUtils.getUsername(), deviceId);
        DevicePo devicePo = deviceAppService.getDeviceById(deviceId);
        return success(DeviceMptAssembler.INSTANCE.fromPo(devicePo));
    }

    /**
     * 新增设备信息
     *
     * @param device 设备信息
     * @return 结果
     */
    @Log(title = "设备信息管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:vehicle:device:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DeviceMpt device) {
        logger.info("管理后台用户[{}]新增设备信息[{}]", SecurityUtils.getUsername(), device.getCode());
        if (!deviceAppService.checkCodeUnique(device.getId(), device.getCode())) {
            return error("新增设备信息'" + device.getCode() + "'失败，设备信息代码已存在");
        }
        DevicePo devicePo = DeviceMptAssembler.INSTANCE.toPo(device);
        devicePo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(deviceAppService.createDevice(devicePo));
    }

    /**
     * 修改保存设备信息
     *
     * @param device 设备信息
     * @return 结果
     */
    @Log(title = "设备信息管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:device:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DeviceMpt device) {
        logger.info("管理后台用户[{}]修改保存设备信息[{}]", SecurityUtils.getUsername(), device.getCode());
        if (!deviceAppService.checkCodeUnique(device.getId(), device.getCode())) {
            return error("修改保存设备信息'" + device.getCode() + "'失败，设备信息代码已存在");
        }
        DevicePo devicePo = DeviceMptAssembler.INSTANCE.toPo(device);
        devicePo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(deviceAppService.modifyDevice(devicePo));
    }

    /**
     * 删除设备信息
     *
     * @param deviceIds 设备信息ID数组
     * @return 结果
     */
    @Log(title = "设备信息管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:vehicle:device:remove")
    @Override
    @DeleteMapping("/{deviceIds}")
    public AjaxResult remove(@PathVariable Long[] deviceIds) {
        logger.info("管理后台用户[{}]删除设备信息[{}]", SecurityUtils.getUsername(), deviceIds);
        return toAjax(deviceAppService.deleteDeviceByIds(deviceIds));
    }

}

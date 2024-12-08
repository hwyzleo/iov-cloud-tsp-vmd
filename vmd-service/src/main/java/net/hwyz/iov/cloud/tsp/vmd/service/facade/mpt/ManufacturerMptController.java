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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ManufacturerMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.ManufacturerMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.ManufacturerAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.ManufacturerMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehManufacturerPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆工厂相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/manufacturer")
public class ManufacturerMptController extends BaseController implements ManufacturerMptApi {

    private final ManufacturerAppService manufacturerAppService;

    /**
     * 分页查询车辆工厂信息
     *
     * @param manufacturer 车辆工厂信息
     * @return 车辆工厂信息列表
     */
    @RequiresPermissions("vehicle:product:manufacturer:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(ManufacturerMpt manufacturer) {
        logger.info("管理后台用户[{}]分页查询工厂信息", SecurityUtils.getUsername());
        startPage();
        List<VehManufacturerPo> manufacturerPoList = manufacturerAppService.search(manufacturer.getCode(), manufacturer.getName(),
                getBeginTime(manufacturer), getEndTime(manufacturer));
        List<ManufacturerMpt> manufacturerMptList = ManufacturerMptAssembler.INSTANCE.fromPoList(manufacturerPoList);
        return getDataTable(manufacturerPoList, manufacturerMptList);
    }

    /**
     * 导出车辆工厂信息
     *
     * @param response     响应
     * @param manufacturer 车辆平台信息
     */
    @Log(title = "工厂管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("vehicle:product:manufacturer:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManufacturerMpt manufacturer) {
        logger.info("管理后台用户[{}]导出车辆工厂信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车辆工厂ID获取车辆工厂信息
     *
     * @param manufacturerId 车辆工厂ID
     * @return 车辆工厂信息
     */
    @RequiresPermissions("vehicle:product:manufacturer:query")
    @Override
    @GetMapping(value = "/{manufacturerId}")
    public AjaxResult getInfo(@PathVariable Long manufacturerId) {
        logger.info("管理后台用户[{}]根据车辆工厂ID[{}]获取车辆工厂信息", SecurityUtils.getUsername(), manufacturerId);
        VehManufacturerPo manufacturerPo = manufacturerAppService.getManufacturerById(manufacturerId);
        return success(ManufacturerMptAssembler.INSTANCE.fromPo(manufacturerPo));
    }

    /**
     * 新增车辆工厂信息
     *
     * @param manufacturer 车辆工厂信息
     * @return 结果
     */
    @Log(title = "工厂管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("vehicle:product:manufacturer:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ManufacturerMpt manufacturer) {
        logger.info("管理后台用户[{}]新增车辆工厂信息[{}]", SecurityUtils.getUsername(), manufacturer.getCode());
        if (!manufacturerAppService.checkCodeUnique(manufacturer.getId(), manufacturer.getCode())) {
            return error("新增车辆工厂'" + manufacturer.getCode() + "'失败，车辆工厂代码已存在");
        }
        VehManufacturerPo manufacturerPo = ManufacturerMptAssembler.INSTANCE.toPo(manufacturer);
        manufacturerPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(manufacturerAppService.createManufacturer(manufacturerPo));
    }

    /**
     * 修改保存车辆工厂信息
     *
     * @param manufacturer 车辆工厂信息
     * @return 结果
     */
    @Log(title = "工厂管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("vehicle:product:manufacturer:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ManufacturerMpt manufacturer) {
        logger.info("管理后台用户[{}]修改保存车辆工厂信息[{}]", SecurityUtils.getUsername(), manufacturer.getCode());
        if (!manufacturerAppService.checkCodeUnique(manufacturer.getId(), manufacturer.getCode())) {
            return error("修改保存车辆工厂'" + manufacturer.getCode() + "'失败，车辆工厂代码已存在");
        }
        VehManufacturerPo manufacturerPo = ManufacturerMptAssembler.INSTANCE.toPo(manufacturer);
        manufacturerPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(manufacturerAppService.modifyManufacturer(manufacturerPo));
    }

    /**
     * 删除车辆工厂信息
     *
     * @param manufacturerIds 车辆工厂ID数组
     * @return 结果
     */
    @Log(title = "工厂管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("vehicle:product:manufacturer:remove")
    @Override
    @DeleteMapping("/{manufacturerIds}")
    public AjaxResult remove(@PathVariable Long[] manufacturerIds) {
        logger.info("管理后台用户[{}]删除车辆工厂信息[{}]", SecurityUtils.getUsername(), manufacturerIds);
        for (Long manufacturerId : manufacturerIds) {
            if (manufacturerAppService.checkManufacturerVehicleExist(manufacturerId)) {
                return error("删除车辆工厂'" + manufacturerId + "'失败，该车辆工厂下存在车辆");
            }
        }
        return toAjax(manufacturerAppService.deletePlatformByIds(manufacturerIds));
    }

}

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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.WheelMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.WheelMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.WheelAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.WheelMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehWheelPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮胎轮毂相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/wheel")
public class WheelMptController extends BaseController implements WheelMptApi {

    private final WheelAppService wheelAppService;

    /**
     * 分页查询轮胎轮毂信息
     *
     * @param wheel 轮胎轮毂信息
     * @return 轮胎轮毂信息列表
     */
    @RequiresPermissions("completeVehicle:product:wheel:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(WheelMpt wheel) {
        logger.info("管理后台用户[{}]分页查询轮胎轮毂信息", SecurityUtils.getUsername());
        startPage();
        List<VehWheelPo> wheelPoList = wheelAppService.search(wheel.getPlatformCode(), wheel.getSeriesCode(),
                wheel.getCode(), wheel.getName(), getBeginTime(wheel), getEndTime(wheel));
        List<WheelMpt> wheelMptList = WheelMptAssembler.INSTANCE.fromPoList(wheelPoList);
        return getDataTable(wheelPoList, wheelMptList);
    }

    /**
     * 获取指定车辆平台及车系下的所有轮胎轮毂
     *
     * @return 轮胎轮毂信息列表
     */
    @RequiresPermissions("completeVehicle:product:wheel:list")
    @Override
    @GetMapping(value = "/listByPlatformCodeAndSeriesCode")
    public List<WheelMpt> listByPlatformCodeAndSeriesCode(@RequestParam String platformCode, @RequestParam String seriesCode) {
        logger.info("管理后台用户[{}]获取指定车辆平台[{}]及车系[{}]下的所有轮胎轮毂", SecurityUtils.getUsername(), platformCode, seriesCode);
        List<VehWheelPo> wheelPoList = wheelAppService.search(platformCode, seriesCode, null, null, null, null);
        return WheelMptAssembler.INSTANCE.fromPoList(wheelPoList);
    }

    /**
     * 导出轮胎轮毂信息
     *
     * @param response 响应
     * @param wheel    轮胎轮毂信息
     */
    @Log(title = "轮胎轮毂管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:product:wheel:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, WheelMpt wheel) {
        logger.info("管理后台用户[{}]导出轮胎轮毂信息", SecurityUtils.getUsername());
    }

    /**
     * 根据轮胎轮毂ID获取轮胎轮毂信息
     *
     * @param wheelId 轮胎轮毂ID
     * @return 轮胎轮毂信息
     */
    @RequiresPermissions("completeVehicle:product:wheel:query")
    @Override
    @GetMapping(value = "/{wheelId}")
    public AjaxResult getInfo(@PathVariable Long wheelId) {
        logger.info("管理后台用户[{}]根据轮胎轮毂ID[{}]获取轮胎轮毂信息", SecurityUtils.getUsername(), wheelId);
        VehWheelPo wheelPo = wheelAppService.getWheelById(wheelId);
        return success(WheelMptAssembler.INSTANCE.fromPo(wheelPo));
    }

    /**
     * 新增轮胎轮毂信息
     *
     * @param wheel 轮胎轮毂信息
     * @return 结果
     */
    @Log(title = "轮胎轮毂管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:product:wheel:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WheelMpt wheel) {
        logger.info("管理后台用户[{}]新增轮胎轮毂信息[{}]", SecurityUtils.getUsername(), wheel.getCode());
        if (!wheelAppService.checkCodeUnique(wheel.getId(), wheel.getCode())) {
            return error("新增轮胎轮毂'" + wheel.getCode() + "'失败，轮胎轮毂代码已存在");
        }
        VehWheelPo wheelPo = WheelMptAssembler.INSTANCE.toPo(wheel);
        wheelPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(wheelAppService.createWheel(wheelPo));
    }

    /**
     * 修改保存轮胎轮毂信息
     *
     * @param wheel 轮胎轮毂信息
     * @return 结果
     */
    @Log(title = "轮胎轮毂管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:wheel:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WheelMpt wheel) {
        logger.info("管理后台用户[{}]修改保存轮胎轮毂信息[{}]", SecurityUtils.getUsername(), wheel.getCode());
        if (!wheelAppService.checkCodeUnique(wheel.getId(), wheel.getCode())) {
            return error("修改保存轮胎轮毂'" + wheel.getCode() + "'失败，轮胎轮毂代码已存在");
        }
        VehWheelPo wheelPo = WheelMptAssembler.INSTANCE.toPo(wheel);
        wheelPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(wheelAppService.modifyWheel(wheelPo));
    }

    /**
     * 删除轮胎轮毂信息
     *
     * @param wheelIds 轮胎轮毂ID数组
     * @return 结果
     */
    @Log(title = "轮胎轮毂管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:product:wheel:remove")
    @Override
    @DeleteMapping("/{wheelIds}")
    public AjaxResult remove(@PathVariable Long[] wheelIds) {
        logger.info("管理后台用户[{}]删除轮胎轮毂信息[{}]", SecurityUtils.getUsername(), wheelIds);
        for (Long wheelId : wheelIds) {
            if (wheelAppService.checkInteriorModelConfigExist(wheelId)) {
                return error("删除轮胎轮毂'" + wheelId + "'失败，该轮胎轮毂下存在车型配置");
            }
        }
        return toAjax(wheelAppService.deleteWheelByIds(wheelIds));
    }

}

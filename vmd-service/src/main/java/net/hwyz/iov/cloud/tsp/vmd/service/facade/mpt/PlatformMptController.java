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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.PlatformMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.PlatformMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.PlatformAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.PlatformMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPlatformPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆平台相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/platform")
public class PlatformMptController extends BaseController implements PlatformMptApi {

    private final PlatformAppService platformAppService;

    /**
     * 分页查询车辆平台信息
     *
     * @param platform 车辆平台信息
     * @return 车辆平台信息列表
     */
    @RequiresPermissions("vehicle:product:platform:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(PlatformMpt platform) {
        logger.info("管理后台用户[{}]分页查询车辆平台信息", SecurityUtils.getUsername());
        startPage();
        List<VehPlatformPo> platformPoList = platformAppService.search(platform.getCode(), platform.getName(),
                getBeginTime(platform), getEndTime(platform));
        List<PlatformMpt> platformMptList = PlatformMptAssembler.INSTANCE.fromPoList(platformPoList);
        return getDataTable(platformPoList, platformMptList);
    }

    /**
     * 获取所有车辆平台信息
     *
     * @return 车辆平台信息列表
     */
    @RequiresPermissions("vehicle:product:platform:list")
    @Override
    @GetMapping(value = "/listAll")
    public List<PlatformMpt> listAll() {
        logger.info("管理后台用户[{}]获取所有车辆平台信息", SecurityUtils.getUsername());
        List<VehPlatformPo> platformPoList = platformAppService.search(null, null, null, null);
        return PlatformMptAssembler.INSTANCE.fromPoList(platformPoList);
    }

    /**
     * 导出车辆平台信息
     *
     * @param response 响应
     * @param platform 车辆平台信息
     */
    @Log(title = "车辆平台管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("vehicle:product:platform:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformMpt platform) {
        logger.info("管理后台用户[{}]导出车辆平台信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车辆平台ID获取车辆平台信息
     *
     * @param platformId 车辆平台ID
     * @return 车辆平台信息
     */
    @RequiresPermissions("vehicle:product:platform:query")
    @Override
    @GetMapping(value = "/{platformId}")
    public AjaxResult getInfo(@PathVariable Long platformId) {
        logger.info("管理后台用户[{}]根据车辆平台ID[{}]获取车辆平台信息", SecurityUtils.getUsername(), platformId);
        VehPlatformPo platformPo = platformAppService.getPlatformById(platformId);
        return success(PlatformMptAssembler.INSTANCE.fromPo(platformPo));
    }

    /**
     * 新增车辆平台信息
     *
     * @param platform 车辆平台信息
     * @return 结果
     */
    @Log(title = "车辆平台管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("vehicle:product:platform:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody PlatformMpt platform) {
        logger.info("管理后台用户[{}]新增车辆平台信息[{}]", SecurityUtils.getUsername(), platform.getCode());
        if (!platformAppService.checkCodeUnique(platform.getId(), platform.getCode())) {
            return error("新增车辆平台'" + platform.getCode() + "'失败，车辆平台代码已存在");
        }
        VehPlatformPo platformPo = PlatformMptAssembler.INSTANCE.toPo(platform);
        platformPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(platformAppService.createPlatform(platformPo));
    }

    /**
     * 修改保存车辆平台信息
     *
     * @param platform 车辆平台信息
     * @return 结果
     */
    @Log(title = "车辆平台管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("vehicle:product:platform:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody PlatformMpt platform) {
        logger.info("管理后台用户[{}]修改保存车辆平台信息[{}]", SecurityUtils.getUsername(), platform.getCode());
        if (!platformAppService.checkCodeUnique(platform.getId(), platform.getCode())) {
            return error("修改保存车辆平台'" + platform.getCode() + "'失败，车辆平台代码已存在");
        }
        VehPlatformPo platformPo = PlatformMptAssembler.INSTANCE.toPo(platform);
        platformPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(platformAppService.modifyPlatform(platformPo));
    }

    /**
     * 删除车辆平台信息
     *
     * @param platformIds 车辆平台ID数组
     * @return 结果
     */
    @Log(title = "车辆平台管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("vehicle:product:platform:remove")
    @Override
    @DeleteMapping("/{platformIds}")
    public AjaxResult remove(@PathVariable Long[] platformIds) {
        logger.info("管理后台用户[{}]删除车辆平台信息[{}]", SecurityUtils.getUsername(), platformIds);
        for (Long platformId : platformIds) {
            if (platformAppService.checkPlatformSeriesExist(platformId)) {
                return error("删除车辆平台'" + platformId + "'失败，该车辆平台下存在车系");
            }
            if (platformAppService.checkPlatformModelExist(platformId)) {
                return error("删除车辆平台'" + platformId + "'失败，该车辆平台下存在车型");
            }
            if (platformAppService.checkPlatformModelConfigExist(platformId)) {
                return error("删除车辆平台'" + platformId + "'失败，该车辆平台下存在车型配置");
            }
            if (platformAppService.checkPlatformVehicleExist(platformId)) {
                return error("删除车辆平台'" + platformId + "'失败，该车辆平台下存在车辆");
            }
        }
        return toAjax(platformAppService.deletePlatformByIds(platformIds));
    }

}

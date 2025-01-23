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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ExteriorMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.ExteriorMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.ExteriorAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.ExteriorMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehExteriorPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车身颜色相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/exterior")
public class ExteriorMptController extends BaseController implements ExteriorMptApi {

    private final ExteriorAppService exteriorAppService;

    /**
     * 分页查询车身颜色信息
     *
     * @param exterior 车身颜色信息
     * @return 车身颜色信息列表
     */
    @RequiresPermissions("completeVehicle:product:exterior:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(ExteriorMpt exterior) {
        logger.info("管理后台用户[{}]分页查询车身颜色信息", SecurityUtils.getUsername());
        startPage();
        List<VehExteriorPo> exteriorPoList = exteriorAppService.search(exterior.getPlatformCode(), exterior.getSeriesCode(),
                exterior.getCode(), exterior.getName(), getBeginTime(exterior), getEndTime(exterior));
        List<ExteriorMpt> exteriorMptList = ExteriorMptAssembler.INSTANCE.fromPoList(exteriorPoList);
        return getDataTable(exteriorPoList, exteriorMptList);
    }

    /**
     * 获取指定车辆平台及车系下的所有车身颜色
     *
     * @return 车身颜色信息列表
     */
    @RequiresPermissions("completeVehicle:product:exterior:list")
    @Override
    @GetMapping(value = "/listByPlatformCodeAndSeriesCode")
    public List<ExteriorMpt> listByPlatformCodeAndSeriesCode(@RequestParam String platformCode, @RequestParam String seriesCode) {
        logger.info("管理后台用户[{}]获取指定车辆平台[{}]及车系[{}]下的所有车身颜色", SecurityUtils.getUsername(), platformCode, seriesCode);
        List<VehExteriorPo> exteriorPoList = exteriorAppService.search(platformCode, seriesCode, null, null, null, null);
        return ExteriorMptAssembler.INSTANCE.fromPoList(exteriorPoList);
    }

    /**
     * 导出车身颜色信息
     *
     * @param response 响应
     * @param exterior 车身颜色信息
     */
    @Log(title = "车身颜色管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:product:exterior:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExteriorMpt exterior) {
        logger.info("管理后台用户[{}]导出车身颜色信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车身颜色ID获取车身颜色信息
     *
     * @param exteriorId 车身颜色ID
     * @return 车身颜色信息
     */
    @RequiresPermissions("completeVehicle:product:exterior:query")
    @Override
    @GetMapping(value = "/{exteriorId}")
    public AjaxResult getInfo(@PathVariable Long exteriorId) {
        logger.info("管理后台用户[{}]根据车身颜色ID[{}]获取车身颜色信息", SecurityUtils.getUsername(), exteriorId);
        VehExteriorPo exteriorPo = exteriorAppService.getExteriorById(exteriorId);
        return success(ExteriorMptAssembler.INSTANCE.fromPo(exteriorPo));
    }

    /**
     * 新增车身颜色信息
     *
     * @param exterior 车身颜色信息
     * @return 结果
     */
    @Log(title = "车身颜色管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:product:exterior:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ExteriorMpt exterior) {
        logger.info("管理后台用户[{}]新增车身颜色信息[{}]", SecurityUtils.getUsername(), exterior.getCode());
        if (!exteriorAppService.checkCodeUnique(exterior.getId(), exterior.getCode())) {
            return error("新增车身颜色'" + exterior.getCode() + "'失败，车身颜色代码已存在");
        }
        VehExteriorPo exteriorPo = ExteriorMptAssembler.INSTANCE.toPo(exterior);
        exteriorPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(exteriorAppService.createExterior(exteriorPo));
    }

    /**
     * 修改保存车身颜色信息
     *
     * @param exterior 车身颜色信息
     * @return 结果
     */
    @Log(title = "车身颜色管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:exterior:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ExteriorMpt exterior) {
        logger.info("管理后台用户[{}]修改保存车身颜色信息[{}]", SecurityUtils.getUsername(), exterior.getCode());
        if (!exteriorAppService.checkCodeUnique(exterior.getId(), exterior.getCode())) {
            return error("修改保存车身颜色'" + exterior.getCode() + "'失败，车身颜色代码已存在");
        }
        VehExteriorPo exteriorPo = ExteriorMptAssembler.INSTANCE.toPo(exterior);
        exteriorPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(exteriorAppService.modifyExterior(exteriorPo));
    }

    /**
     * 删除车身颜色信息
     *
     * @param exteriorIds 车身颜色ID数组
     * @return 结果
     */
    @Log(title = "车身颜色管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:product:exterior:remove")
    @Override
    @DeleteMapping("/{exteriorIds}")
    public AjaxResult remove(@PathVariable Long[] exteriorIds) {
        logger.info("管理后台用户[{}]删除车身颜色信息[{}]", SecurityUtils.getUsername(), exteriorIds);
        for (Long exteriorId : exteriorIds) {
            if (exteriorAppService.checkExteriorModelConfigExist(exteriorId)) {
                return error("删除车身颜色'" + exteriorId + "'失败，该车身颜色下存在车型配置");
            }
        }
        return toAjax(exteriorAppService.deleteExteriorByIds(exteriorIds));
    }

}

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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.InteriorMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.InteriorMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.InteriorAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.InteriorMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehInteriorPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内饰颜色相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/interior")
public class InteriorMptController extends BaseController implements InteriorMptApi {

    private final InteriorAppService interiorAppService;

    /**
     * 分页查询内饰颜色信息
     *
     * @param interior 内饰颜色信息
     * @return 内饰颜色信息列表
     */
    @RequiresPermissions("completeVehicle:product:interior:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(InteriorMpt interior) {
        logger.info("管理后台用户[{}]分页查询内饰颜色信息", SecurityUtils.getUsername());
        startPage();
        List<VehInteriorPo> interiorPoList = interiorAppService.search(interior.getPlatformCode(), interior.getSeriesCode(),
                interior.getCode(), interior.getName(), getBeginTime(interior), getEndTime(interior));
        List<InteriorMpt> interiorMptList = InteriorMptAssembler.INSTANCE.fromPoList(interiorPoList);
        return getDataTable(interiorPoList, interiorMptList);
    }

    /**
     * 获取指定车辆平台及车系下的所有内饰颜色
     *
     * @return 内饰颜色信息列表
     */
    @RequiresPermissions("completeVehicle:product:interior:list")
    @Override
    @GetMapping(value = "/listByPlatformCodeAndSeriesCode")
    public List<InteriorMpt> listByPlatformCodeAndSeriesCode(@RequestParam String platformCode, @RequestParam String seriesCode) {
        logger.info("管理后台用户[{}]获取指定车辆平台[{}]及车系[{}]下的所有内饰颜色", SecurityUtils.getUsername(), platformCode, seriesCode);
        List<VehInteriorPo> interiorPoList = interiorAppService.search(platformCode, seriesCode, null, null, null, null);
        return InteriorMptAssembler.INSTANCE.fromPoList(interiorPoList);
    }

    /**
     * 导出内饰颜色信息
     *
     * @param response 响应
     * @param interior 内饰颜色信息
     */
    @Log(title = "内饰颜色管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:product:interior:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, InteriorMpt interior) {
        logger.info("管理后台用户[{}]导出内饰颜色信息", SecurityUtils.getUsername());
    }

    /**
     * 根据内饰颜色ID获取内饰颜色信息
     *
     * @param interiorId 内饰颜色ID
     * @return 内饰颜色信息
     */
    @RequiresPermissions("completeVehicle:product:interior:query")
    @Override
    @GetMapping(value = "/{interiorId}")
    public AjaxResult getInfo(@PathVariable Long interiorId) {
        logger.info("管理后台用户[{}]根据内饰颜色ID[{}]获取内饰颜色信息", SecurityUtils.getUsername(), interiorId);
        VehInteriorPo interiorPo = interiorAppService.getInteriorById(interiorId);
        return success(InteriorMptAssembler.INSTANCE.fromPo(interiorPo));
    }

    /**
     * 新增内饰颜色信息
     *
     * @param interior 内饰颜色信息
     * @return 结果
     */
    @Log(title = "内饰颜色管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:product:interior:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody InteriorMpt interior) {
        logger.info("管理后台用户[{}]新增内饰颜色信息[{}]", SecurityUtils.getUsername(), interior.getCode());
        if (!interiorAppService.checkCodeUnique(interior.getId(), interior.getCode())) {
            return error("新增内饰颜色'" + interior.getCode() + "'失败，内饰颜色代码已存在");
        }
        VehInteriorPo interiorPo = InteriorMptAssembler.INSTANCE.toPo(interior);
        interiorPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(interiorAppService.createInterior(interiorPo));
    }

    /**
     * 修改保存内饰颜色信息
     *
     * @param interior 内饰颜色信息
     * @return 结果
     */
    @Log(title = "内饰颜色管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:interior:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody InteriorMpt interior) {
        logger.info("管理后台用户[{}]修改保存内饰颜色信息[{}]", SecurityUtils.getUsername(), interior.getCode());
        if (!interiorAppService.checkCodeUnique(interior.getId(), interior.getCode())) {
            return error("修改保存内饰颜色'" + interior.getCode() + "'失败，内饰颜色代码已存在");
        }
        VehInteriorPo interiorPo = InteriorMptAssembler.INSTANCE.toPo(interior);
        interiorPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(interiorAppService.modifyInterior(interiorPo));
    }

    /**
     * 删除内饰颜色信息
     *
     * @param interiorIds 内饰颜色ID数组
     * @return 结果
     */
    @Log(title = "内饰颜色管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:product:interior:remove")
    @Override
    @DeleteMapping("/{interiorIds}")
    public AjaxResult remove(@PathVariable Long[] interiorIds) {
        logger.info("管理后台用户[{}]删除内饰颜色信息[{}]", SecurityUtils.getUsername(), interiorIds);
        for (Long interiorId : interiorIds) {
            if (interiorAppService.checkInteriorModelConfigExist(interiorId)) {
                return error("删除内饰颜色'" + interiorId + "'失败，该内饰颜色下存在车型配置");
            }
        }
        return toAjax(interiorAppService.deleteInteriorByIds(interiorIds));
    }

}

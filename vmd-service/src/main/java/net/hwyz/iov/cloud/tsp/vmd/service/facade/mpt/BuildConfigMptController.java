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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BuildConfigMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.BuildConfigMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.BuildConfigAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.BuildConfigMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBuildConfigPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 生产配置相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/buildConfig")
public class BuildConfigMptController extends BaseController implements BuildConfigMptApi {

    private final BuildConfigAppService buildConfigAppService;

    /**
     * 分页查询生产配置信息
     *
     * @param buildConfig 生产配置信息
     * @return 生产配置信息列表
     */
    @RequiresPermissions("completeVehicle:product:buildConfig:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(BuildConfigMpt buildConfig) {
        logger.info("管理后台用户[{}]分页查询生产配置信息", SecurityUtils.getUsername());
        startPage();
        List<VehBuildConfigPo> buildConfigPoList = buildConfigAppService.search(buildConfig.getPlatformCode(), buildConfig.getSeriesCode(),
                buildConfig.getModelCode(), buildConfig.getCode(), buildConfig.getName(), getBeginTime(buildConfig), getEndTime(buildConfig));
        List<BuildConfigMpt> buildConfigMptList = BuildConfigMptAssembler.INSTANCE.fromPoList(buildConfigPoList);
        return getDataTable(buildConfigPoList, buildConfigMptList);
    }

    /**
     * 导出生产配置信息
     *
     * @param response    响应
     * @param buildConfig 生产配置信息
     */
    @Log(title = "生产配置管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:product:buildConfig:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, BuildConfigMpt buildConfig) {
        logger.info("管理后台用户[{}]导出生产配置信息", SecurityUtils.getUsername());
    }

    /**
     * 根据生产配置ID获取生产配置信息
     *
     * @param buildConfigId 生产配置ID
     * @return 生产配置信息
     */
    @RequiresPermissions("completeVehicle:product:buildConfig:query")
    @Override
    @GetMapping(value = "/{buildConfigId}")
    public AjaxResult getInfo(@PathVariable Long buildConfigId) {
        logger.info("管理后台用户[{}]根据生产配置ID[{}]获取生产配置信息", SecurityUtils.getUsername(), buildConfigId);
        VehBuildConfigPo buildConfigPo = buildConfigAppService.getBuildConfigById(buildConfigId);
        return success(BuildConfigMptAssembler.INSTANCE.fromPo(buildConfigPo));
    }

    /**
     * 新增生产配置信息
     *
     * @param buildConfig 生产配置信息
     * @return 结果
     */
    @Log(title = "生产配置管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:product:buildConfig:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BuildConfigMpt buildConfig) {
        logger.info("管理后台用户[{}]新增生产配置信息[{}]", SecurityUtils.getUsername(), buildConfig.getCode());
        if (!buildConfigAppService.checkCodeUnique(buildConfig.getId(), buildConfig.getCode())) {
            return error("新增生产配置'" + buildConfig.getCode() + "'失败，生产配置代码已存在");
        }
        VehBuildConfigPo buildConfigPo = BuildConfigMptAssembler.INSTANCE.toPo(buildConfig);
        buildConfigPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(buildConfigAppService.createBuildConfig(buildConfigPo));
    }

    /**
     * 修改保存生产配置信息
     *
     * @param buildConfig 生产配置信息
     * @return 结果
     */
    @Log(title = "生产配置管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:buildConfig:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BuildConfigMpt buildConfig) {
        logger.info("管理后台用户[{}]修改保存生产配置信息[{}]", SecurityUtils.getUsername(), buildConfig.getCode());
        if (!buildConfigAppService.checkCodeUnique(buildConfig.getId(), buildConfig.getCode())) {
            return error("修改保存生产配置'" + buildConfig.getCode() + "'失败，生产配置代码已存在");
        }
        VehBuildConfigPo buildConfigPo = BuildConfigMptAssembler.INSTANCE.toPo(buildConfig);
        buildConfigPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(buildConfigAppService.modifyBuildConfig(buildConfigPo));
    }

    /**
     * 删除生产配置信息
     *
     * @param buildConfigIds 生产配置ID数组
     * @return 结果
     */
    @Log(title = "生产配置管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:product:buildConfig:remove")
    @Override
    @DeleteMapping("/{buildConfigIds}")
    public AjaxResult remove(@PathVariable Long[] buildConfigIds) {
        logger.info("管理后台用户[{}]删除生产配置信息[{}]", SecurityUtils.getUsername(), buildConfigIds);
        for (Long buildConfigId : buildConfigIds) {
            if (buildConfigAppService.checkBuildConfigVehicleExist(buildConfigId)) {
                return error("删除生产配置'" + buildConfigId + "'失败，该生产配置下存在车辆");
            }
        }
        return toAjax(buildConfigAppService.deleteBuildConfigByIds(buildConfigIds));
    }

}

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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ModelConfigMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.ModelConfigMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.ModelConfigAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.ModelConfigMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelConfigPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车型配置相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/modelConfig")
public class ModelConfigMptController extends BaseController implements ModelConfigMptApi {

    private final ModelConfigAppService modelConfigAppService;

    /**
     * 分页查询车型配置信息
     *
     * @param modelConfig 车型配置信息
     * @return 车型配置信息列表
     */
    @RequiresPermissions("tsp:vmd:modelConfig:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(ModelConfigMpt modelConfig) {
        logger.info("管理后台用户[{}]分页查询车型配置信息", SecurityUtils.getUsername());
        startPage();
        List<VehModelConfigPo> modelConfigPoList = modelConfigAppService.search(modelConfig.getPlatformCode(), modelConfig.getSeriesCode(),
                modelConfig.getModelCode(), modelConfig.getCode(), modelConfig.getName(), getBeginTime(modelConfig), getEndTime(modelConfig));
        List<ModelConfigMpt> modelConfigMptList = ModelConfigMptAssembler.INSTANCE.fromPoList(modelConfigPoList);
        return getDataTable(modelConfigPoList, modelConfigMptList);
    }

    /**
     * 导出车型信息
     *
     * @param response    响应
     * @param modelConfig 车型信息
     */
    @Log(title = "车型配置管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("tsp:vmd:modelConfig:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, ModelConfigMpt modelConfig) {
        logger.info("管理后台用户[{}]导出车型配置信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车型配置ID获取车型配置信息
     *
     * @param modelConfigId 车型配置ID
     * @return 车型配置信息
     */
    @RequiresPermissions("tsp:vmd:modelConfig:query")
    @Override
    @GetMapping(value = "/{modelConfigId}")
    public AjaxResult getInfo(@PathVariable Long modelConfigId) {
        logger.info("管理后台用户[{}]根据车型配置ID[{}]获取车型配置信息", SecurityUtils.getUsername(), modelConfigId);
        VehModelConfigPo modelConfigPo = modelConfigAppService.getModelConfigById(modelConfigId);
        return success(ModelConfigMptAssembler.INSTANCE.fromPo(modelConfigPo));
    }

    /**
     * 新增车型配置信息
     *
     * @param modelConfig 车型配置信息
     * @return 结果
     */
    @Log(title = "车型配置管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("tsp:vmd:modelConfig:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ModelConfigMpt modelConfig) {
        logger.info("管理后台用户[{}]新增车型配置信息[{}]", SecurityUtils.getUsername(), modelConfig.getCode());
        if (!modelConfigAppService.checkCodeUnique(modelConfig.getId(), modelConfig.getCode())) {
            return error("新增车型配置'" + modelConfig.getCode() + "'失败，车型配置代码已存在");
        }
        VehModelConfigPo modelConfigPo = ModelConfigMptAssembler.INSTANCE.toPo(modelConfig);
        modelConfigPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(modelConfigAppService.createModelConfig(modelConfigPo));
    }

    /**
     * 修改保存车型配置信息
     *
     * @param modelConfig 车型配置信息
     * @return 结果
     */
    @Log(title = "车型配置管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("tsp:vmd:modelConfig:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ModelConfigMpt modelConfig) {
        logger.info("管理后台用户[{}]修改保存车型配置信息[{}]", SecurityUtils.getUsername(), modelConfig.getCode());
        if (!modelConfigAppService.checkCodeUnique(modelConfig.getId(), modelConfig.getCode())) {
            return error("修改保存车型配置'" + modelConfig.getCode() + "'失败，车型配置代码已存在");
        }
        VehModelConfigPo modelConfigPo = ModelConfigMptAssembler.INSTANCE.toPo(modelConfig);
        modelConfigPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(modelConfigAppService.modifyModelConfig(modelConfigPo));
    }

    /**
     * 删除车型配置信息
     *
     * @param modelConfigIds 车型配置ID数组
     * @return 结果
     */
    @Log(title = "车型配置管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("tsp:vmd:modelConfig:remove")
    @Override
    @DeleteMapping("/{modelConfigIds}")
    public AjaxResult remove(@PathVariable Long[] modelConfigIds) {
        logger.info("管理后台用户[{}]删除车型配置信息[{}]", SecurityUtils.getUsername(), modelConfigIds);
        return toAjax(modelConfigAppService.deleteModelConfigByIds(modelConfigIds));
    }

}

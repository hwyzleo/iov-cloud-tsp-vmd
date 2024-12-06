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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ModelMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.ModelMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.ModelAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.ModelMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车型相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/model")
public class ModelMptController extends BaseController implements ModelMptApi {

    private final ModelAppService modelAppService;

    /**
     * 分页查询车型信息
     *
     * @param model 车型信息
     * @return 车型信息列表
     */
    @RequiresPermissions("tsp:vmd:model:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(ModelMpt model) {
        logger.info("管理后台用户[{}]分页查询车型信息", SecurityUtils.getUsername());
        startPage();
        List<VehModelPo> modelPoList = modelAppService.search(model.getPlatformCode(), model.getSeriesCode(), model.getCode(),
                model.getName(), getBeginTime(model), getEndTime(model));
        List<ModelMpt> modelMptList = ModelMptAssembler.INSTANCE.fromPoList(modelPoList);
        return getDataTable(modelPoList, modelMptList);
    }

    /**
     * 导出车型信息
     *
     * @param response 响应
     * @param model    车型信息
     */
    @Log(title = "车型管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("tsp:vmd:model:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, ModelMpt model) {
        logger.info("管理后台用户[{}]导出车型信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车型ID获取车型信息
     *
     * @param modelId 车型ID
     * @return 车型信息
     */
    @RequiresPermissions("tsp:vmd:model:query")
    @Override
    @GetMapping(value = "/{modelId}")
    public AjaxResult getInfo(@PathVariable Long modelId) {
        logger.info("管理后台用户[{}]根据车型ID[{}]获取车型信息", SecurityUtils.getUsername(), modelId);
        VehModelPo modelPo = modelAppService.getModelById(modelId);
        return success(ModelMptAssembler.INSTANCE.fromPo(modelPo));
    }

    /**
     * 新增车型信息
     *
     * @param model 车型信息
     * @return 结果
     */
    @Log(title = "车型管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("tsp:vmd:model:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ModelMpt model) {
        logger.info("管理后台用户[{}]新增车型信息[{}]", SecurityUtils.getUsername(), model.getCode());
        if (!modelAppService.checkCodeUnique(model.getId(), model.getCode())) {
            return error("新增车型'" + model.getCode() + "'失败，车型代码已存在");
        }
        VehModelPo modelPo = ModelMptAssembler.INSTANCE.toPo(model);
        modelPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(modelAppService.createModel(modelPo));
    }

    /**
     * 修改保存车型信息
     *
     * @param model 车型信息
     * @return 结果
     */
    @Log(title = "车型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("tsp:vmd:model:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ModelMpt model) {
        logger.info("管理后台用户[{}]修改保存车型信息[{}]", SecurityUtils.getUsername(), model.getCode());
        if (!modelAppService.checkCodeUnique(model.getId(), model.getCode())) {
            return error("修改保存车型'" + model.getCode() + "'失败，车型代码已存在");
        }
        VehModelPo modelPo = ModelMptAssembler.INSTANCE.toPo(model);
        modelPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(modelAppService.modifyModel(modelPo));
    }

    /**
     * 删除车型信息
     *
     * @param modelIds 车型ID数组
     * @return 结果
     */
    @Log(title = "车型管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("tsp:vmd:model:remove")
    @Override
    @DeleteMapping("/{modelIds}")
    public AjaxResult remove(@PathVariable Long[] modelIds) {
        logger.info("管理后台用户[{}]删除车型信息[{}]", SecurityUtils.getUsername(), modelIds);
        return toAjax(modelAppService.deleteModelByIds(modelIds));
    }

}

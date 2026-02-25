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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BaseModelFeatureCodeMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BaseModelMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.BaseModelMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.BaseModelAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.FeatureFamilyAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.BaseModelFeatureCodeMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.BaseModelMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBaseModelFeatureCodePo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBaseModelPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehFeatureCodePo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehFeatureFamilyPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基础车型相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/baseModel")
public class BaseModelMptController extends BaseController implements BaseModelMptApi {

    private final BaseModelAppService baseModelAppService;
    private final FeatureFamilyAppService featureFamilyAppService;

    /**
     * 分页查询基础车型信息
     *
     * @param baseModel 基础车型信息
     * @return 基础车型信息列表
     */
    @RequiresPermissions("completeVehicle:product:baseModel:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(BaseModelMpt baseModel) {
        logger.info("管理后台用户[{}]分页查询基础车型信息", SecurityUtils.getUsername());
        startPage();
        List<VehBaseModelPo> baseModelPoList = baseModelAppService.search(baseModel.getPlatformCode(), baseModel.getSeriesCode(),
                baseModel.getModelCode(), baseModel.getCode(), baseModel.getName(), getBeginTime(baseModel), getEndTime(baseModel));
        List<BaseModelMpt> baseModelMptList = BaseModelMptAssembler.INSTANCE.fromPoList(baseModelPoList);
        return getDataTable(baseModelPoList, baseModelMptList);
    }

    /**
     * 查询基础车型下特征值
     *
     * @param baseModelCode        基础车型编码
     * @param baseModelFeatureCode 基础车型特征值
     * @return 基础车型下特征值列表
     */
    @RequiresPermissions("completeVehicle:product:baseModel:list")
    @Override
    @GetMapping(value = "/{baseModelCode}/featureCode/list")
    public AjaxResult listFeatureCode(@PathVariable String baseModelCode, BaseModelFeatureCodeMpt baseModelFeatureCode) {
        logger.info("管理后台用户[{}]分页查询基础车型下特征值", SecurityUtils.getUsername());
        List<VehBaseModelFeatureCodePo> baseModelFeatureCodePoList = baseModelAppService.searchFeatureCode(baseModelCode,
                baseModelFeatureCode.getFamilyCode(), getBeginTime(baseModelFeatureCode), getEndTime(baseModelFeatureCode));
        List<BaseModelFeatureCodeMpt> baseModelFeatureCodeMptList = BaseModelFeatureCodeMptAssembler.INSTANCE.fromPoList(baseModelFeatureCodePoList);
        baseModelFeatureCodeMptList.forEach(mpt -> {
            VehFeatureFamilyPo featureFamily = featureFamilyAppService.getFeatureFamilyByCode(mpt.getFamilyCode());
            if (featureFamily != null) {
                mpt.setFamilyName(featureFamily.getName());
            }
            mpt.setFeatureName(new String[mpt.getFeatureCode().length]);
            int i = 0;
            for (String code : mpt.getFeatureCode()) {
                VehFeatureCodePo featureCode = featureFamilyAppService.getFeatureCodeByCode(code);
                if (featureCode != null) {
                    mpt.getFeatureName()[i] = featureCode.getName();
                }
                i++;
            }

        });
        return success(baseModelFeatureCodeMptList);
    }

    /**
     * 获取指定车辆平台及车系及车型下的所有基础车型
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @param modelCode    车型代码
     * @return 基础车型信息列表
     */
    @RequiresPermissions("completeVehicle:product:baseModel:list")
    @Override
    @GetMapping(value = "/listByPlatformCodeAndSeriesCodeAndModelCode")
    public List<BaseModelMpt> listByPlatformCodeAndSeriesCodeAndModelCode(@RequestParam String platformCode,
                                                                          @RequestParam String seriesCode,
                                                                          @RequestParam String modelCode) {
        logger.info("管理后台用户[{}]获取指定车辆平台[{}]及车系[{}]及车型[{}]下的所有基础车型", SecurityUtils.getUsername(),
                platformCode, seriesCode, modelCode);
        List<VehBaseModelPo> baseModelPoList = baseModelAppService.search(platformCode, seriesCode, modelCode,
                null, null, null, null);
        return BaseModelMptAssembler.INSTANCE.fromPoList(baseModelPoList);
    }

    /**
     * 导出基础车型信息
     *
     * @param response  响应
     * @param baseModel 基础车型信息
     */
    @Log(title = "基础车型管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:product:baseModel:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaseModelMpt baseModel) {
        logger.info("管理后台用户[{}]导出基础车型信息", SecurityUtils.getUsername());
    }

    /**
     * 根据基础车型ID获取基础车型信息
     *
     * @param baseModelId 基础车型ID
     * @return 基础车型信息
     */
    @RequiresPermissions("completeVehicle:product:baseModel:query")
    @Override
    @GetMapping(value = "/{baseModelId}")
    public AjaxResult getInfo(@PathVariable Long baseModelId) {
        logger.info("管理后台用户[{}]根据基础车型ID[{}]获取基础车型信息", SecurityUtils.getUsername(), baseModelId);
        VehBaseModelPo baseModelPo = baseModelAppService.getBaseModelById(baseModelId);
        return success(BaseModelMptAssembler.INSTANCE.fromPo(baseModelPo));
    }

    /**
     * 根据基础车型特征值ID获取基础车型特征值信息
     *
     * @param baseModelCode          基础车型编码
     * @param baseModelFeatureCodeId 基础车型特征值ID
     * @return 基础车型特征值信息
     */
    @RequiresPermissions("completeVehicle:product:baseModel:query")
    @Override
    @GetMapping(value = "/{baseModelCode}/featureCode/{baseModelFeatureCodeId}")
    public AjaxResult getFeatureCodeInfo(@PathVariable String baseModelCode, @PathVariable Long baseModelFeatureCodeId) {
        logger.info("管理后台用户[{}]根据基础车型[{}]特征值ID[{}]获取基础车型特征值信息", SecurityUtils.getUsername(), baseModelCode, baseModelFeatureCodeId);
        VehBaseModelFeatureCodePo baseModelFeatureCodePo = baseModelAppService.getBaseModelFeatureCodeById(baseModelFeatureCodeId);
        return success(BaseModelFeatureCodeMptAssembler.INSTANCE.fromPo(baseModelFeatureCodePo));
    }

    /**
     * 新增基础车型信息
     *
     * @param baseModel 基础车型信息
     * @return 结果
     */
    @Log(title = "基础车型管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:product:baseModel:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BaseModelMpt baseModel) {
        logger.info("管理后台用户[{}]新增基础车型信息[{}]", SecurityUtils.getUsername(), baseModel.getCode());
        if (!baseModelAppService.checkCodeUnique(baseModel.getId(), baseModel.getCode())) {
            return error("新增基础车型'" + baseModel.getCode() + "'失败，基础车型代码已存在");
        }
        VehBaseModelPo baseModelPo = BaseModelMptAssembler.INSTANCE.toPo(baseModel);
        baseModelPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(baseModelAppService.createBasicModel(baseModelPo));
    }

    /**
     * 新增基础车型特征值
     *
     * @param baseModelCode        基础车型编码
     * @param baseModelFeatureCode 基础车型特征值
     * @return 结果
     */
    @Log(title = "基础车型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:baseModel:edit")
    @Override
    @PostMapping("/{baseModelCode}/featureCode")
    public AjaxResult addFeatureCode(@PathVariable String baseModelCode, @Validated @RequestBody BaseModelFeatureCodeMpt baseModelFeatureCode) {
        logger.info("管理后台用户[{}]新增基础车型[{}]特征值[{}]", SecurityUtils.getUsername(), baseModelCode, baseModelFeatureCode.getFamilyCode());
        if (!baseModelAppService.checkFeatureCodeUnique(baseModelFeatureCode.getId(), baseModelCode, baseModelFeatureCode.getFamilyCode())) {
            return error("新增基础车型特征值'" + baseModelFeatureCode.getFamilyCode() + "'失败，基础车型特征值已存在");
        }
        VehBaseModelFeatureCodePo baseModelFeatureCodePo = BaseModelFeatureCodeMptAssembler.INSTANCE.toPo(baseModelFeatureCode);
        baseModelFeatureCodePo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(baseModelAppService.createBasicModelFeatureCode(baseModelFeatureCodePo));
    }

    /**
     * 修改保存基础车型信息
     *
     * @param baseModel 基础车型信息
     * @return 结果
     */
    @Log(title = "基础车型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:baseModel:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BaseModelMpt baseModel) {
        logger.info("管理后台用户[{}]修改保存基础车型信息[{}]", SecurityUtils.getUsername(), baseModel.getCode());
        if (!baseModelAppService.checkCodeUnique(baseModel.getId(), baseModel.getCode())) {
            return error("修改保存基础车型'" + baseModel.getCode() + "'失败，基础车型代码已存在");
        }
        VehBaseModelPo baseModelPo = BaseModelMptAssembler.INSTANCE.toPo(baseModel);
        baseModelPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(baseModelAppService.modifyBasicModel(baseModelPo));
    }

    /**
     * 修改保存基础车型特征值
     *
     * @param baseModelCode        基础车型编码
     * @param baseModelFeatureCode 基础车型特征值
     * @return 结果
     */
    @Log(title = "基础车型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:baseModel:edit")
    @Override
    @PutMapping("/{baseModelCode}/featureCode")
    public AjaxResult editFeatureCode(@PathVariable String baseModelCode, @Validated @RequestBody BaseModelFeatureCodeMpt baseModelFeatureCode) {
        logger.info("管理后台用户[{}]修改保存基础车型[{}]特征值[{}]", SecurityUtils.getUsername(), baseModelCode, baseModelFeatureCode.getFamilyCode());
        if (!baseModelAppService.checkFeatureCodeUnique(baseModelFeatureCode.getId(), baseModelCode, baseModelFeatureCode.getFamilyCode())) {
            return error("修改保存基础车型特征值'" + baseModelFeatureCode.getFamilyCode() + "'失败，基础车型特征值已存在");
        }
        VehBaseModelFeatureCodePo baseModelFeatureCodePo = BaseModelFeatureCodeMptAssembler.INSTANCE.toPo(baseModelFeatureCode);
        baseModelFeatureCodePo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(baseModelAppService.modifyBaseModelFeatureCode(baseModelFeatureCodePo));
    }

    /**
     * 删除基础车型信息
     *
     * @param baseModelIds 基础车型ID数组
     * @return 结果
     */
    @Log(title = "基础车型管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:product:baseModel:remove")
    @Override
    @DeleteMapping("/{baseModelIds}")
    public AjaxResult remove(@PathVariable Long[] baseModelIds) {
        logger.info("管理后台用户[{}]删除基础车型信息[{}]", SecurityUtils.getUsername(), baseModelIds);
        for (Long baseModelId : baseModelIds) {
            if (baseModelAppService.checkBaseModelBuildConfigExist(baseModelId)) {
                return error("删除基础车型'" + baseModelId + "'失败，该基础车型下存在生产配置");
            }
            if (baseModelAppService.checkBaseModelVehicleExist(baseModelId)) {
                return error("删除基础车型'" + baseModelId + "'失败，该基础车型下存在车辆");
            }
        }
        return toAjax(baseModelAppService.deleteBasicModelByIds(baseModelIds));
    }

    /**
     * 删除基础车型特征值
     *
     * @param baseModelCode           基础车型编码
     * @param baseModelFeatureCodeIds 基础车型特征值ID数组
     * @return 结果
     */
    @Log(title = "基础车型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:baseModel:edit")
    @Override
    @DeleteMapping("/{baseModelCode}/featureCode/{baseModelFeatureCodeIds}")
    public AjaxResult removeFeatureCode(@PathVariable String baseModelCode, @PathVariable Long[] baseModelFeatureCodeIds) {
        logger.info("管理后台用户[{}]删除基础车型[{}]特征值[{}]", SecurityUtils.getUsername(), baseModelCode, baseModelFeatureCodeIds);
        return toAjax(baseModelAppService.deleteBaseModelFeatureCodeByIds(baseModelFeatureCodeIds));
    }
}

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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.FeatureCodeMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.FeatureFamilyMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.FeatureFamilyMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.FeatureFamilyAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.FeatureCodeMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.FeatureFamilyMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehFeatureCodePo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehFeatureFamilyPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆特征族相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/featureFamily")
public class FeatureFamilyMptController extends BaseController implements FeatureFamilyMptApi {

    private final FeatureFamilyAppService featureFamilyAppService;

    /**
     * 分页查询车辆特征族信息
     *
     * @param featureFamily 车辆特征族信息
     * @return 车辆特征族信息列表
     */
    @RequiresPermissions("completeVehicle:product:featureFamily:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(FeatureFamilyMpt featureFamily) {
        logger.info("管理后台用户[{}]分页查询车辆特征族信息", SecurityUtils.getUsername());
        startPage();
        List<VehFeatureFamilyPo> featureFamilyPoList = featureFamilyAppService.search(featureFamily.getCode(), featureFamily.getName(),
                featureFamily.getType(), getBeginTime(featureFamily), getEndTime(featureFamily));
        List<FeatureFamilyMpt> featureFamilyMptList = FeatureFamilyMptAssembler.INSTANCE.fromPoList(featureFamilyPoList);
        return getDataTable(featureFamilyPoList, featureFamilyMptList);
    }

    /**
     * 分页查询车辆特征族下特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCode     车辆特征值信息
     * @return 车辆特征族信息列表
     */
    @RequiresPermissions("completeVehicle:product:featureFamily:list")
    @Override
    @GetMapping(value = "/{featureFamilyId}/featureCode/list")
    public TableDataInfo listFeatureCode(@PathVariable Long featureFamilyId, FeatureCodeMpt featureCode) {
        logger.info("管理后台用户[{}]分页查询车辆特征族[{}]下特征值信息", SecurityUtils.getUsername(), featureFamilyId);
        startPage();
        List<VehFeatureCodePo> featureCodePoList = featureFamilyAppService.searchFeatureCode(featureFamilyId, null,
                featureCode.getName(), featureCode.getName(), getBeginTime(featureCode), getEndTime(featureCode));
        List<FeatureCodeMpt> featureCodeMptList = FeatureCodeMptAssembler.INSTANCE.fromPoList(featureCodePoList);
        return getDataTable(featureCodePoList, featureCodeMptList);
    }

    /**
     * 获取车辆特征族列表
     *
     * @return 车辆特征族列表
     */
    @RequiresPermissions("completeVehicle:product:featureFamily:list")
    @Override
    @GetMapping(value = "/listAllFeatureFamily")
    public AjaxResult listAllFeatureFamily() {
        logger.info("管理后台用户[{}]获取车辆特征族列表", SecurityUtils.getUsername());
        List<VehFeatureFamilyPo> featureFamilyPoList = featureFamilyAppService.search(null, null, null, null, null);
        return success(FeatureFamilyMptAssembler.INSTANCE.fromPoList(featureFamilyPoList));
    }

    /**
     * 获取车辆特征值列表
     *
     * @param familyCode 车辆特征族代码
     * @return 车辆特征值列表
     */
    @RequiresPermissions("completeVehicle:product:featureFamily:list")
    @Override
    @GetMapping(value = "/listAllFeatureCode")
    public AjaxResult listAllFeatureCode(@RequestParam String familyCode) {
        logger.info("管理后台用户[{}]获取车辆特征族[{}]下特征值列表", SecurityUtils.getUsername(), familyCode);
        List<VehFeatureCodePo> featureCodePoList = featureFamilyAppService.searchFeatureCode(null, familyCode, null, null, null, null);
        return success(FeatureCodeMptAssembler.INSTANCE.fromPoList(featureCodePoList));
    }

    /**
     * 导出车辆特征族信息
     *
     * @param response      响应
     * @param featureFamily 车辆特征族信息
     */
    @Log(title = "车辆特征族管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:product:featureFamily:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, FeatureFamilyMpt featureFamily) {
        logger.info("管理后台用户[{}]导出车辆特征族信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车辆特征族ID获取车辆特征族信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @return 车辆特征族信息
     */
    @RequiresPermissions("completeVehicle:product:featureFamily:query")
    @Override
    @GetMapping(value = "/{featureFamilyId}")
    public AjaxResult getInfo(@PathVariable Long featureFamilyId) {
        logger.info("管理后台用户[{}]根据车辆特征族ID[{}]获取车辆特征族信息", SecurityUtils.getUsername(), featureFamilyId);
        VehFeatureFamilyPo featureFamilyPo = featureFamilyAppService.getFeatureFamilyById(featureFamilyId);
        return success(FeatureFamilyMptAssembler.INSTANCE.fromPo(featureFamilyPo));
    }

    /**
     * 根据车辆特征值ID获取车辆特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCodeId   车辆特征值ID
     * @return 车辆特征值信息
     */
    @RequiresPermissions("completeVehicle:product:featureFamily:query")
    @Override
    @GetMapping(value = "/{featureFamilyId}/featureCode/{featureCodeId}")
    public AjaxResult getFeatureCodeInfo(@PathVariable Long featureFamilyId, @PathVariable Long featureCodeId) {
        logger.info("管理后台用户[{}]根据车辆特征值ID[{}]获取车辆特征值信息", SecurityUtils.getUsername(), featureCodeId);
        VehFeatureCodePo featureCodePo = featureFamilyAppService.getFeatureCodeById(featureFamilyId, featureCodeId);
        return success(FeatureCodeMptAssembler.INSTANCE.fromPo(featureCodePo));
    }

    /**
     * 新增车辆特征族信息
     *
     * @param featureFamily 车辆特征族信息
     * @return 结果
     */
    @Log(title = "车辆特征族管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:product:featureFamily:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FeatureFamilyMpt featureFamily) {
        logger.info("管理后台用户[{}]新增车辆特征族信息[{}]", SecurityUtils.getUsername(), featureFamily.getCode());
        if (!featureFamilyAppService.checkFamilyCodeUnique(featureFamily.getId(), featureFamily.getCode())) {
            return error("新增车辆特征族'" + featureFamily.getCode() + "'失败，车辆特征族代码已存在");
        }
        VehFeatureFamilyPo featureFamilyPo = FeatureFamilyMptAssembler.INSTANCE.toPo(featureFamily);
        featureFamilyPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(featureFamilyAppService.createFeatureFamily(featureFamilyPo));
    }

    /**
     * 新增车辆特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCode     车辆特征值信息
     * @return 结果
     */
    @Log(title = "车辆特征族管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:featureFamily:edit")
    @Override
    @PostMapping("/{featureFamilyId}/featureCode")
    public AjaxResult addFeatureCode(@PathVariable Long featureFamilyId, @Validated @RequestBody FeatureCodeMpt featureCode) {
        logger.info("管理后台用户[{}]新增车辆特征值信息[{}]", SecurityUtils.getUsername(), featureCode.getCode());
        if (!featureFamilyAppService.checkFeatureCodeUnique(featureCode.getId(), featureCode.getCode())) {
            return error("新增车辆特征值'" + featureCode.getCode() + "'失败，车辆特征值代码已存在");
        }
        VehFeatureCodePo featureCodePo = FeatureCodeMptAssembler.INSTANCE.toPo(featureCode);
        featureCodePo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(featureFamilyAppService.createFeatureCode(featureFamilyId, featureCodePo));
    }

    /**
     * 修改保存车辆特征族信息
     *
     * @param featureFamily 车辆特征族信息
     * @return 结果
     */
    @Log(title = "车辆特征族管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:featureFamily:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FeatureFamilyMpt featureFamily) {
        logger.info("管理后台用户[{}]修改保存车辆特征族信息[{}]", SecurityUtils.getUsername(), featureFamily.getCode());
        if (!featureFamilyAppService.checkFamilyCodeUnique(featureFamily.getId(), featureFamily.getCode())) {
            return error("修改保存车辆特征族'" + featureFamily.getCode() + "'失败，车辆特征族代码已存在");
        }
        VehFeatureFamilyPo featureFamilyPo = FeatureFamilyMptAssembler.INSTANCE.toPo(featureFamily);
        featureFamilyPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(featureFamilyAppService.modifyFeatureFamily(featureFamilyPo));
    }

    /**
     * 修改保存车辆特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCode     车辆特征值信息
     * @return 结果
     */
    @Log(title = "车辆特征族管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:featureFamily:edit")
    @Override
    @PutMapping("/{featureFamilyId}/featureCode")
    public AjaxResult editFeatureCode(@PathVariable Long featureFamilyId, @Validated @RequestBody FeatureCodeMpt featureCode) {
        logger.info("管理后台用户[{}]修改保存车辆特征值信息[{}]", SecurityUtils.getUsername(), featureCode.getCode());
        if (!featureFamilyAppService.checkFeatureCodeUnique(featureCode.getId(), featureCode.getCode())) {
            return error("修改保存车辆特征值'" + featureCode.getCode() + "'失败，车辆特征值代码已存在");
        }
        VehFeatureCodePo featureCodePo = FeatureCodeMptAssembler.INSTANCE.toPo(featureCode);
        featureCodePo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(featureFamilyAppService.modifyFeatureCode(featureFamilyId, featureCodePo));
    }

    /**
     * 删除车辆特征族信息
     *
     * @param featureFamilyIds 车辆特征族ID数组
     * @return 结果
     */
    @Log(title = "车辆特征族管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:product:featureFamily:remove")
    @Override
    @DeleteMapping("/{featureFamilyIds}")
    public AjaxResult remove(@PathVariable Long[] featureFamilyIds) {
        logger.info("管理后台用户[{}]删除车辆特征族信息[{}]", SecurityUtils.getUsername(), featureFamilyIds);
        return toAjax(featureFamilyAppService.deleteFeatureFamilyByIds(featureFamilyIds));
    }

    /**
     * 删除车辆特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCodeIds  车辆特征值ID数组
     * @return 结果
     */
    @Log(title = "车辆特征族管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:featureFamily:edit")
    @Override
    @DeleteMapping("/{featureFamilyId}/featureCode/{featureCodeIds}")
    public AjaxResult removeFeatureCode(@PathVariable Long featureFamilyId, @PathVariable Long[] featureCodeIds) {
        logger.info("管理后台用户[{}]删除车辆特征值信息[{}]", SecurityUtils.getUsername(), featureCodeIds);
        return toAjax(featureFamilyAppService.deleteFeatureCodeByIds(featureFamilyId, featureCodeIds));
    }
}

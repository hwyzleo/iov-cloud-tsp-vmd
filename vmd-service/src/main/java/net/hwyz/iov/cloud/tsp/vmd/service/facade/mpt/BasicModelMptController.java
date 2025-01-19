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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BasicModelMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.BasicModelMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.BasicModelAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.BasicModelMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicModelPo;
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
@RequestMapping(value = "/mpt/basicModel")
public class BasicModelMptController extends BaseController implements BasicModelMptApi {

    private final BasicModelAppService basicModelAppService;

    /**
     * 分页查询基础车型信息
     *
     * @param basicModel 基础车型信息
     * @return 基础车型信息列表
     */
    @RequiresPermissions("completeVehicle:product:basicModel:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(BasicModelMpt basicModel) {
        logger.info("管理后台用户[{}]分页查询基础车型信息", SecurityUtils.getUsername());
        startPage();
        List<VehBasicModelPo> basicModelPoList = basicModelAppService.search(basicModel.getPlatformCode(), basicModel.getSeriesCode(),
                basicModel.getModelCode(), basicModel.getCode(), basicModel.getName(), getBeginTime(basicModel), getEndTime(basicModel));
        List<BasicModelMpt> basicModelMptList = BasicModelMptAssembler.INSTANCE.fromPoList(basicModelPoList);
        return getDataTable(basicModelPoList, basicModelMptList);
    }

    /**
     * 获取指定车辆平台及车系及车型下的所有基础车型
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @param modelCode    车型代码
     * @return 基础车型信息列表
     */
    @RequiresPermissions("completeVehicle:product:basicModel:list")
    @Override
    @GetMapping(value = "/listByPlatformCodeAndSeriesCode")
    public List<BasicModelMpt> listByPlatformCodeAndSeriesCodeAndModelCode(@RequestParam String platformCode,
                                                                           @RequestParam String seriesCode,
                                                                           @RequestParam String modelCode) {
        logger.info("管理后台用户[{}]获取指定车辆平台[{}]及车系[{}]及车型[{}]下的所有基础车型", SecurityUtils.getUsername(),
                platformCode, seriesCode, modelCode);
        List<VehBasicModelPo> basicModelPoList = basicModelAppService.search(platformCode, seriesCode, modelCode,
                null, null, null, null);
        return BasicModelMptAssembler.INSTANCE.fromPoList(basicModelPoList);
    }

    /**
     * 导出基础车型信息
     *
     * @param response   响应
     * @param basicModel 基础车型信息
     */
    @Log(title = "基础车型管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:product:basicModel:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasicModelMpt basicModel) {
        logger.info("管理后台用户[{}]导出基础车型信息", SecurityUtils.getUsername());
    }

    /**
     * 根据基础车型ID获取基础车型信息
     *
     * @param basicModelId 基础车型ID
     * @return 基础车型信息
     */
    @RequiresPermissions("completeVehicle:product:basicModel:query")
    @Override
    @GetMapping(value = "/{basicModelId}")
    public AjaxResult getInfo(@PathVariable Long basicModelId) {
        logger.info("管理后台用户[{}]根据基础车型ID[{}]获取基础车型信息", SecurityUtils.getUsername(), basicModelId);
        VehBasicModelPo basicModelPo = basicModelAppService.getBasicModelById(basicModelId);
        return success(BasicModelMptAssembler.INSTANCE.fromPo(basicModelPo));
    }

    /**
     * 新增基础车型信息
     *
     * @param basicModel 基础车型信息
     * @return 结果
     */
    @Log(title = "基础车型管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:product:basicModel:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BasicModelMpt basicModel) {
        logger.info("管理后台用户[{}]新增基础车型信息[{}]", SecurityUtils.getUsername(), basicModel.getCode());
        if (!basicModelAppService.checkCodeUnique(basicModel.getId(), basicModel.getCode())) {
            return error("新增基础车型'" + basicModel.getCode() + "'失败，基础车型代码已存在");
        }
        VehBasicModelPo basicModelPo = BasicModelMptAssembler.INSTANCE.toPo(basicModel);
        basicModelPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(basicModelAppService.createBasicModel(basicModelPo));
    }

    /**
     * 修改保存基础车型信息
     *
     * @param basicModel 基础车型信息
     * @return 结果
     */
    @Log(title = "基础车型管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:product:basicModel:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BasicModelMpt basicModel) {
        logger.info("管理后台用户[{}]修改保存基础车型信息[{}]", SecurityUtils.getUsername(), basicModel.getCode());
        if (!basicModelAppService.checkCodeUnique(basicModel.getId(), basicModel.getCode())) {
            return error("修改保存基础车型'" + basicModel.getCode() + "'失败，基础车型代码已存在");
        }
        VehBasicModelPo basicModelPo = BasicModelMptAssembler.INSTANCE.toPo(basicModel);
        basicModelPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(basicModelAppService.modifyBasicModel(basicModelPo));
    }

    /**
     * 删除基础车型信息
     *
     * @param basicModelIds 基础车型ID数组
     * @return 结果
     */
    @Log(title = "基础车型管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:product:basicModel:remove")
    @Override
    @DeleteMapping("/{basicModelIds}")
    public AjaxResult remove(@PathVariable Long[] basicModelIds) {
        logger.info("管理后台用户[{}]删除基础车型信息[{}]", SecurityUtils.getUsername(), basicModelIds);
        for (Long basicModelId : basicModelIds) {
            if (basicModelAppService.checkBasicModelModelConfigExist(basicModelId)) {
                return error("删除基础车型'" + basicModelId + "'失败，该基础车型下存在车型配置");
            }
            if (basicModelAppService.checkBasicModelVehicleExist(basicModelId)) {
                return error("删除基础车型'" + basicModelId + "'失败，该基础车型下存在车辆");
            }
        }
        return toAjax(basicModelAppService.deleteBasicModelByIds(basicModelIds));
    }

}

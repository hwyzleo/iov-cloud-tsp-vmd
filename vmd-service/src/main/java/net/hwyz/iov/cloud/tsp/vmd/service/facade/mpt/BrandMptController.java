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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BrandMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.BrandMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.BrandAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.BrandMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.ManufacturerMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBrandPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehManufacturerPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆品牌相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/brand")
public class BrandMptController extends BaseController implements BrandMptApi {

    private final BrandAppService brandAppService;

    /**
     * 分页查询车辆品牌信息
     *
     * @param brand 车辆品牌信息
     * @return 车辆品牌信息列表
     */
    @RequiresPermissions("vehicle:product:brand:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(BrandMpt brand) {
        logger.info("管理后台用户[{}]分页查询车辆品牌信息", SecurityUtils.getUsername());
        startPage();
        List<VehBrandPo> vehBrandPoList = brandAppService.search(brand.getCode(), brand.getName(), getBeginTime(brand),
                getEndTime(brand));
        List<BrandMpt> brandMptList = BrandMptAssembler.INSTANCE.fromPoList(vehBrandPoList);
        return getDataTable(vehBrandPoList, brandMptList);
    }

    /**
     * 导出车辆品牌信息
     *
     * @param response 响应
     * @param brand    车辆品牌信息
     */
    @Log(title = "车辆品牌管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("vehicle:product:brand:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, BrandMpt brand) {
        logger.info("管理后台用户[{}]导出车辆品牌信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车辆品牌ID获取车辆品牌信息
     *
     * @param brandId 车辆品牌ID
     * @return 车辆品牌信息
     */
    @RequiresPermissions("vehicle:product:brand:query")
    @Override
    @GetMapping(value = "/{brandId}")
    public AjaxResult getInfo(@PathVariable Long brandId) {
        logger.info("管理后台用户[{}]根据车辆品牌ID[{}]获取车辆品牌信息", SecurityUtils.getUsername(), brandId);
        VehBrandPo brandPo = brandAppService.getBrandById(brandId);
        return success(BrandMptAssembler.INSTANCE.fromPo(brandPo));
    }

    /**
     * 新增车辆品牌信息
     *
     * @param brand 车辆品牌信息
     * @return 结果
     */
    @Log(title = "车辆品牌管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("vehicle:product:brand:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BrandMpt brand) {
        logger.info("管理后台用户[{}]新增车辆品牌信息[{}]", SecurityUtils.getUsername(), brand.getCode());
        if (!brandAppService.checkCodeUnique(brand.getId(), brand.getCode())) {
            return error("新增车辆品牌'" + brand.getCode() + "'失败，车辆品牌代码已存在");
        }
        VehBrandPo brandPo = BrandMptAssembler.INSTANCE.toPo(brand);
        brandPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(brandAppService.createBrand(brandPo));
    }

    /**
     * 修改保存车辆品牌信息
     *
     * @param brand 车辆品牌信息
     * @return 结果
     */
    @Log(title = "车辆品牌管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("vehicle:product:brand:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BrandMpt brand) {
        logger.info("管理后台用户[{}]修改保存车辆品牌信息[{}]", SecurityUtils.getUsername(), brand.getCode());
        if (!brandAppService.checkCodeUnique(brand.getId(), brand.getCode())) {
            return error("修改保存车辆品牌'" + brand.getCode() + "'失败，车辆品牌代码已存在");
        }
        VehBrandPo brandPo = BrandMptAssembler.INSTANCE.toPo(brand);
        brandPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(brandAppService.modifyBrand(brandPo));
    }

    /**
     * 删除车辆品牌信息
     *
     * @param brandIds 车辆品牌ID数组
     * @return 结果
     */
    @Log(title = "车辆品牌管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("vehicle:product:brand:remove")
    @Override
    @DeleteMapping("/{brandIds}")
    public AjaxResult remove(@PathVariable Long[] brandIds) {
        logger.info("管理后台用户[{}]删除车辆品牌信息[{}]", SecurityUtils.getUsername(), brandIds);
        return toAjax(brandAppService.deletePlatformByIds(brandIds));
    }

}

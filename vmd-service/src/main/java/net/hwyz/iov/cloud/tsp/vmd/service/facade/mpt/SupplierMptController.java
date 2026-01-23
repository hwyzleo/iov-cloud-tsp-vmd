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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.SupplierMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.SupplierMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.SupplierAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.SupplierMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.SupplierPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/supplier")
public class SupplierMptController extends BaseController implements SupplierMptApi {

    private final SupplierAppService supplierAppService;

    /**
     * 分页查询供应商信息
     *
     * @param supplier 供应商信息
     * @return 供应商信息列表
     */
    @RequiresPermissions("completeVehicle:vehicle:supplier:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(SupplierMpt supplier) {
        logger.info("管理后台用户[{}]分页查询供应商信息", SecurityUtils.getUsername());
        startPage();
        List<SupplierPo> supplierPoList = supplierAppService.search(supplier.getCode(), supplier.getName(),
                getBeginTime(supplier), getEndTime(supplier));
        List<SupplierMpt> supplierMptList = SupplierMptAssembler.INSTANCE.fromPoList(supplierPoList);
        return getDataTable(supplierPoList, supplierMptList);
    }

    /**
     * 导出供应商信息
     *
     * @param response 响应
     * @param supplier 供应商信息
     */
    @Log(title = "供应商管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:vehicle:supplier:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierMpt supplier) {
        logger.info("管理后台用户[{}]导出供应商信息", SecurityUtils.getUsername());
    }

    /**
     * 根据供应商ID获取供应商信息
     *
     * @param supplierId 供应商ID
     * @return 供应商信息
     */
    @RequiresPermissions("completeVehicle:vehicle:supplier:query")
    @Override
    @GetMapping(value = "/{supplierId}")
    public AjaxResult getInfo(@PathVariable Long supplierId) {
        logger.info("管理后台用户[{}]根据供应商ID[{}]获取供应商信息", SecurityUtils.getUsername(), supplierId);
        SupplierPo supplierPo = supplierAppService.getSupplierById(supplierId);
        return success(SupplierMptAssembler.INSTANCE.fromPo(supplierPo));
    }

    /**
     * 新增供应商信息
     *
     * @param supplier 供应商信息
     * @return 结果
     */
    @Log(title = "供应商管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:vehicle:supplier:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SupplierMpt supplier) {
        logger.info("管理后台用户[{}]新增供应商信息[{}]", SecurityUtils.getUsername(), supplier.getCode());
        if (!supplierAppService.checkCodeUnique(supplier.getId(), supplier.getCode())) {
            return error("新增供应商'" + supplier.getCode() + "'失败，供应商代码已存在");
        }
        SupplierPo supplierPo = SupplierMptAssembler.INSTANCE.toPo(supplier);
        supplierPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(supplierAppService.createSupplier(supplierPo));
    }

    /**
     * 修改保存供应商信息
     *
     * @param supplier 供应商信息
     * @return 结果
     */
    @Log(title = "供应商管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:supplier:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SupplierMpt supplier) {
        logger.info("管理后台用户[{}]修改保存供应商信息[{}]", SecurityUtils.getUsername(), supplier.getCode());
        if (!supplierAppService.checkCodeUnique(supplier.getId(), supplier.getCode())) {
            return error("修改保存供应商'" + supplier.getCode() + "'失败，供应商代码已存在");
        }
        SupplierPo supplierPo = SupplierMptAssembler.INSTANCE.toPo(supplier);
        supplierPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(supplierAppService.modifySupplier(supplierPo));
    }

    /**
     * 删除供应商信息
     *
     * @param supplierIds 供应商ID数组
     * @return 结果
     */
    @Log(title = "供应商管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:vehicle:supplier:remove")
    @Override
    @DeleteMapping("/{supplierIds}")
    public AjaxResult remove(@PathVariable Long[] supplierIds) {
        logger.info("管理后台用户[{}]删除供应商信息[{}]", SecurityUtils.getUsername(), supplierIds);
        return toAjax(supplierAppService.deleteSupplierByIds(supplierIds));
    }

}

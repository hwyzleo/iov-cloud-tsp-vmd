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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleDomainMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.VehicleDomainMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehicleDomainAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.VehicleDomainMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehDomainPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车载域相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/vehicleDomain")
public class VehicleDomainMptController extends BaseController implements VehicleDomainMptApi {

    private final VehicleDomainAppService vehicleDomainAppService;

    /**
     * 分页查询车载域信息
     *
     * @param vehicleDomain 车载域信息
     * @return 车载域信息列表
     */
    @RequiresPermissions("completeVehicle:vehicle:vehicleDomain:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(VehicleDomainMpt vehicleDomain) {
        logger.info("管理后台用户[{}]分页查询车载域信息", SecurityUtils.getUsername());
        startPage();
        List<VehDomainPo> vehicleDomainPoList = vehicleDomainAppService.search(vehicleDomain.getCode(), vehicleDomain.getName(),
                getBeginTime(vehicleDomain), getEndTime(vehicleDomain));
        List<VehicleDomainMpt> vehicleDomainMptList = VehicleDomainMptAssembler.INSTANCE.fromPoList(vehicleDomainPoList);
        return getDataTable(vehicleDomainPoList, vehicleDomainMptList);
    }

    /**
     * 导出车载域信息
     *
     * @param response      响应
     * @param vehicleDomain 车载域信息
     */
    @Log(title = "车载域管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:vehicle:vehicleDomain:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleDomainMpt vehicleDomain) {
        logger.info("管理后台用户[{}]导出车载域信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车载域ID获取车载域信息
     *
     * @param vehicleDomainId 车载域ID
     * @return 车载域信息
     */
    @RequiresPermissions("completeVehicle:vehicle:vehicleDomain:query")
    @Override
    @GetMapping(value = "/{vehicleDomainId}")
    public AjaxResult getInfo(@PathVariable Long vehicleDomainId) {
        logger.info("管理后台用户[{}]根据车载域ID[{}]获取车载域信息", SecurityUtils.getUsername(), vehicleDomainId);
        VehDomainPo vehDomainPo = vehicleDomainAppService.getVehicleDomainById(vehicleDomainId);
        return success(VehicleDomainMptAssembler.INSTANCE.fromPo(vehDomainPo));
    }

    /**
     * 新增车载域信息
     *
     * @param vehicleDomain 车载域信息
     * @return 结果
     */
    @Log(title = "车载域管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:vehicle:vehicleDomain:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody VehicleDomainMpt vehicleDomain) {
        logger.info("管理后台用户[{}]新增车载域信息[{}]", SecurityUtils.getUsername(), vehicleDomain.getCode());
        if (!vehicleDomainAppService.checkCodeUnique(vehicleDomain.getId(), vehicleDomain.getCode())) {
            return error("新增车载域'" + vehicleDomain.getCode() + "'失败，车载域代码已存在");
        }
        VehDomainPo vehDomainPo = VehicleDomainMptAssembler.INSTANCE.toPo(vehicleDomain);
        vehDomainPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(vehicleDomainAppService.createVehicleDomain(vehDomainPo));
    }

    /**
     * 修改保存车载域信息
     *
     * @param vehicleDomain 车载域信息
     * @return 结果
     */
    @Log(title = "车载域管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:vehicleDomain:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody VehicleDomainMpt vehicleDomain) {
        logger.info("管理后台用户[{}]修改保存车载域信息[{}]", SecurityUtils.getUsername(), vehicleDomain.getCode());
        if (!vehicleDomainAppService.checkCodeUnique(vehicleDomain.getId(), vehicleDomain.getCode())) {
            return error("修改保存车载域'" + vehicleDomain.getCode() + "'失败，车载域代码已存在");
        }
        VehDomainPo vehDomainPo = VehicleDomainMptAssembler.INSTANCE.toPo(vehicleDomain);
        vehDomainPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(vehicleDomainAppService.modifyVehicleDomain(vehDomainPo));
    }

    /**
     * 删除车载域信息
     *
     * @param vehicleDomainIds 车载域ID数组
     * @return 结果
     */
    @Log(title = "车载域管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:vehicle:vehicleDomain:remove")
    @Override
    @DeleteMapping("/{vehicleDomainIds}")
    public AjaxResult remove(@PathVariable Long[] vehicleDomainIds) {
        logger.info("管理后台用户[{}]删除车载域信息[{}]", SecurityUtils.getUsername(), vehicleDomainIds);
        return toAjax(vehicleDomainAppService.deleteVehicleDomainByIds(vehicleDomainIds));
    }

}

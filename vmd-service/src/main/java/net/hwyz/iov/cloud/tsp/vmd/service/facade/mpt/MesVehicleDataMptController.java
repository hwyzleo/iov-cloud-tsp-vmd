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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.MesVehicleDataMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.MesVehicleDataMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.MesVehicleDataAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehicleAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.MesVehicleDataMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.MesVehicleDataPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MES车辆数据相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/mesVehicleData")
public class MesVehicleDataMptController extends BaseController implements MesVehicleDataMptApi {

    private final VehicleAppService vehicleAppService;
    private final MesVehicleDataAppService mesVehicleDataAppService;

    /**
     * 分页查询MES车辆数据
     *
     * @param mesVehicleData MES车辆数据
     * @return MES车辆数据列表
     */
    @RequiresPermissions("completeVehicle:vehicle:mes:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(MesVehicleDataMpt mesVehicleData) {
        logger.info("管理后台用户[{}]分页查询MES车辆数据", SecurityUtils.getUsername());
        startPage();
        List<MesVehicleDataPo> mesVehicleDataPoList = mesVehicleDataAppService.search(mesVehicleData.getBatchNum(),
                mesVehicleData.getType(), mesVehicleData.getVersion(), getBeginTime(mesVehicleData), getEndTime(mesVehicleData));
        List<MesVehicleDataMpt> mesVehicleDataMptList = MesVehicleDataMptAssembler.INSTANCE.fromPoList(mesVehicleDataPoList);
        return getDataTable(mesVehicleDataPoList, mesVehicleDataMptList);
    }

    /**
     * 导出MES车辆数据
     *
     * @param response       响应
     * @param mesVehicleData MES车辆数据
     */
    @Log(title = "工厂数据管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:vehicle:mes:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesVehicleDataMpt mesVehicleData) {
        logger.info("管理后台用户[{}]导出MES车辆数据", SecurityUtils.getUsername());
    }

    /**
     * 根据MES车辆数据ID获取MES车辆数据
     *
     * @param mesVehicleDataId MES车辆数据ID
     * @return MES车辆数据
     */
    @RequiresPermissions("completeVehicle:vehicle:mes:query")
    @Override
    @GetMapping(value = "/{mesVehicleDataId}")
    public AjaxResult getInfo(@PathVariable Long mesVehicleDataId) {
        logger.info("管理后台用户[{}]根据MES车辆数据ID[{}]获取MES车辆数据", SecurityUtils.getUsername(), mesVehicleDataId);
        MesVehicleDataPo mesVehicleDataPo = mesVehicleDataAppService.getMesVehicleDataById(mesVehicleDataId);
        return success(MesVehicleDataMptAssembler.INSTANCE.fromPo(mesVehicleDataPo));
    }

    /**
     * 新增MES车辆数据
     *
     * @param mesVehicleData MES车辆数据
     * @return 结果
     */
    @Log(title = "工厂数据管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:vehicle:mes:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody MesVehicleDataMpt mesVehicleData) {
        logger.info("管理后台用户[{}]新增MES车辆数据[{}]", SecurityUtils.getUsername(), mesVehicleData.getBatchNum());
        if (!mesVehicleDataAppService.checkBatchNumUnique(mesVehicleData.getId(), mesVehicleData.getBatchNum())) {
            return error("新增MES车辆数据'" + mesVehicleData.getBatchNum() + "'失败，批次号已存在");
        }
        MesVehicleDataPo mesVehicleDataPo = MesVehicleDataMptAssembler.INSTANCE.toPo(mesVehicleData);
        mesVehicleDataPo.setCreateBy(SecurityUtils.getUserId().toString());
        AjaxResult result = toAjax(mesVehicleDataAppService.createMesVehicleData(mesVehicleDataPo));
        try {
            vehicleAppService.parseMesVehicleData(mesVehicleDataPo.getBatchNum());
        } catch (Exception e) {
            logger.warn("MES车辆数据'" + mesVehicleData.getBatchNum() + "'解析异常", e);
            return error("MES车辆数据'" + mesVehicleData.getBatchNum() + "'解析异常");
        }
        return result;
    }

    /**
     * 修改保存MES车辆数据
     *
     * @param mesVehicleData MES车辆数据
     * @return 结果
     */
    @Log(title = "工厂数据管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:mes:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody MesVehicleDataMpt mesVehicleData) {
        logger.info("管理后台用户[{}]修改保存MES车辆数据[{}]", SecurityUtils.getUsername(), mesVehicleData.getBatchNum());
        if (!mesVehicleDataAppService.checkBatchNumUnique(mesVehicleData.getId(), mesVehicleData.getBatchNum())) {
            return error("修改保存MES车辆数据'" + mesVehicleData.getBatchNum() + "'失败，批次号已存在");
        }
        MesVehicleDataPo mesVehicleDataPo = MesVehicleDataMptAssembler.INSTANCE.toPo(mesVehicleData);
        mesVehicleDataPo.setModifyBy(SecurityUtils.getUserId().toString());
        AjaxResult result = toAjax(mesVehicleDataAppService.modifyMesVehicleData(mesVehicleDataPo));
        try {
            vehicleAppService.parseMesVehicleData(mesVehicleDataPo.getBatchNum());
        } catch (Exception e) {
            logger.warn("MES车辆数据'" + mesVehicleData.getBatchNum() + "'解析异常", e);
            return error("MES车辆数据'" + mesVehicleData.getBatchNum() + "'解析异常");
        }
        return result;
    }

    /**
     * 删除MES车辆数据
     *
     * @param mesVehicleDataIds MES车辆数据ID数组
     * @return 结果
     */
    @Log(title = "工厂数据管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:vehicle:mes:remove")
    @Override
    @DeleteMapping("/{mesVehicleDataIds}")
    public AjaxResult remove(@PathVariable Long[] mesVehicleDataIds) {
        logger.info("管理后台用户[{}]删除MES车辆数据[{}]", SecurityUtils.getUsername(), mesVehicleDataIds);
        return toAjax(mesVehicleDataAppService.deleteMesVehicleDataByIds(mesVehicleDataIds));
    }
}

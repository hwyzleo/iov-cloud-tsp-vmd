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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.PartMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.PartMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.PartAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.PartMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.PartPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 零件信息相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/part")
public class PartMptController extends BaseController implements PartMptApi {

    private final PartAppService partAppService;

    /**
     * 分页查询零件信息
     *
     * @param part 零件信息
     * @return 零件信息列表
     */
    @RequiresPermissions("completeVehicle:vehicle:part:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(PartMpt part) {
        logger.info("管理后台用户[{}]分页查询零件信息", SecurityUtils.getUsername());
        startPage();
        List<PartPo> partPoList = partAppService.search(part.getPn(), part.getName(), part.getType(), part.getDeviceCode(),
                getBeginTime(part), getEndTime(part));
        List<PartMpt> partMptList = PartMptAssembler.INSTANCE.fromPoList(partPoList);
        return getDataTable(partPoList, partMptList);
    }

    /**
     * 导出零件信息
     *
     * @param response 响应
     * @param part     设备信息
     */
    @Log(title = "零件信息管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("completeVehicle:vehicle:part:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, PartMpt part) {
        logger.info("管理后台用户[{}]导出零件信息", SecurityUtils.getUsername());
    }

    /**
     * 根据零件信息ID获取零件信息
     *
     * @param partId 零件信息ID
     * @return 零件信息信息
     */
    @RequiresPermissions("completeVehicle:vehicle:part:query")
    @Override
    @GetMapping(value = "/{partId}")
    public AjaxResult getInfo(@PathVariable Long partId) {
        logger.info("管理后台用户[{}]根据零件信息ID[{}]获取零件信息", SecurityUtils.getUsername(), partId);
        PartPo partPo = partAppService.getPartById(partId);
        return success(PartMptAssembler.INSTANCE.fromPo(partPo));
    }

    /**
     * 新增零件信息
     *
     * @param part 零件信息
     * @return 结果
     */
    @Log(title = "零件信息管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("completeVehicle:vehicle:part:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody PartMpt part) {
        logger.info("管理后台用户[{}]新增零件信息[{}]", SecurityUtils.getUsername(), part.getPn());
        if (!partAppService.checkPnUnique(part.getId(), part.getPn())) {
            return error("新增零件信息'" + part.getPn() + "'失败，零件号已存在");
        }
        PartPo partPo = PartMptAssembler.INSTANCE.toPo(part);
        partPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(partAppService.createPart(partPo));
    }

    /**
     * 修改保存零件信息
     *
     * @param part 零件信息
     * @return 结果
     */
    @Log(title = "零件信息管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("completeVehicle:vehicle:part:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody PartMpt part) {
        logger.info("管理后台用户[{}]修改保存零件信息[{}]", SecurityUtils.getUsername(), part.getPn());
        if (!partAppService.checkPnUnique(part.getId(), part.getPn())) {
            return error("修改保存零件信息'" + part.getPn() + "'失败，零件号已存在");
        }
        PartPo partPo = PartMptAssembler.INSTANCE.toPo(part);
        partPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(partAppService.modifyPart(partPo));
    }

    /**
     * 删除零件信息
     *
     * @param partIds 零件信息ID数组
     * @return 结果
     */
    @Log(title = "零件信息管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("completeVehicle:vehicle:part:remove")
    @Override
    @DeleteMapping("/{partIds}")
    public AjaxResult remove(@PathVariable Long[] partIds) {
        logger.info("管理后台用户[{}]删除零件信息[{}]", SecurityUtils.getUsername(), partIds);
        return toAjax(partAppService.deletePartByIds(partIds));
    }

}

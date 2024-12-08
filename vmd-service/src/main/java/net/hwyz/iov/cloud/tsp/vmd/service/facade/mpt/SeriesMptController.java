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
import net.hwyz.iov.cloud.tsp.vmd.api.contract.SeriesMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.SeriesMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.SeriesAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.SeriesMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehSeriesPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车系相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/series")
public class SeriesMptController extends BaseController implements SeriesMptApi {

    private final SeriesAppService seriesAppService;

    /**
     * 分页查询车系信息
     *
     * @param series 车系信息
     * @return 车系信息列表
     */
    @RequiresPermissions("vehicle:product:series:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(SeriesMpt series) {
        logger.info("管理后台用户[{}]分页查询车系信息", SecurityUtils.getUsername());
        startPage();
        List<VehSeriesPo> seriesPoList = seriesAppService.search(series.getPlatformCode(), series.getCode(),
                series.getName(), getBeginTime(series), getEndTime(series));
        List<SeriesMpt> seriesMptList = SeriesMptAssembler.INSTANCE.fromPoList(seriesPoList);
        return getDataTable(seriesPoList, seriesMptList);
    }

    /**
     * 获取指定车辆平台下的所有车系
     *
     * @return 车辆平台信息列表
     */
    @RequiresPermissions("vehicle:product:series:list")
    @Override
    @GetMapping(value = "/listByPlatformCode")
    public List<SeriesMpt> listByPlatformCode(@RequestParam String platformCode) {
        logger.info("管理后台用户[{}]获取指定车辆平台[{}]下的所有车系", SecurityUtils.getUsername(), platformCode);
        List<VehSeriesPo> seriesPoList = seriesAppService.search(platformCode, null, null, null, null);
        return SeriesMptAssembler.INSTANCE.fromPoList(seriesPoList);
    }

    /**
     * 导出车系信息
     *
     * @param response 响应
     * @param series   车系信息
     */
    @Log(title = "车系管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("vehicle:product:series:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeriesMpt series) {
        logger.info("管理后台用户[{}]导出车系信息", SecurityUtils.getUsername());
    }

    /**
     * 根据车系ID获取车系信息
     *
     * @param seriesId 车系ID
     * @return 车系信息
     */
    @RequiresPermissions("vehicle:product:series:query")
    @Override
    @GetMapping(value = "/{seriesId}")
    public AjaxResult getInfo(@PathVariable Long seriesId) {
        logger.info("管理后台用户[{}]根据车系ID[{}]获取车系信息", SecurityUtils.getUsername(), seriesId);
        VehSeriesPo seriesPo = seriesAppService.getSeriesById(seriesId);
        return success(SeriesMptAssembler.INSTANCE.fromPo(seriesPo));
    }

    /**
     * 新增车系信息
     *
     * @param series 车系信息
     * @return 结果
     */
    @Log(title = "车系管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("vehicle:product:series:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SeriesMpt series) {
        logger.info("管理后台用户[{}]新增车系信息[{}]", SecurityUtils.getUsername(), series.getCode());
        if (!seriesAppService.checkCodeUnique(series.getId(), series.getCode())) {
            return error("新增车系'" + series.getCode() + "'失败，车系代码已存在");
        }
        VehSeriesPo seriesPo = SeriesMptAssembler.INSTANCE.toPo(series);
        seriesPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(seriesAppService.createSeries(seriesPo));
    }

    /**
     * 修改保存车系信息
     *
     * @param series 车系信息
     * @return 结果
     */
    @Log(title = "车系管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("vehicle:product:series:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SeriesMpt series) {
        logger.info("管理后台用户[{}]修改保存车系信息[{}]", SecurityUtils.getUsername(), series.getCode());
        if (!seriesAppService.checkCodeUnique(series.getId(), series.getCode())) {
            return error("修改保存车系'" + series.getCode() + "'失败，车系代码已存在");
        }
        VehSeriesPo seriesPo = SeriesMptAssembler.INSTANCE.toPo(series);
        seriesPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(seriesAppService.modifySeries(seriesPo));
    }

    /**
     * 删除车系信息
     *
     * @param seriesIds 车系ID数组
     * @return 结果
     */
    @Log(title = "车系管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("vehicle:product:series:remove")
    @Override
    @DeleteMapping("/{seriesIds}")
    public AjaxResult remove(@PathVariable Long[] seriesIds) {
        logger.info("管理后台用户[{}]删除车系信息[{}]", SecurityUtils.getUsername(), seriesIds);
        for (Long seriesId : seriesIds) {
            if (seriesAppService.checkSeriesModelExist(seriesId)) {
                return error("删除车系'" + seriesId + "'失败，该车系下存在车型");
            }
            if (seriesAppService.checkSeriesModelConfigExist(seriesId)) {
                return error("删除车系'" + seriesId + "'失败，该车系下存在车型配置");
            }
            if (seriesAppService.checkSeriesVehicleExist(seriesId)) {
                return error("删除车系'" + seriesId + "'失败，该车系下存在车辆");
            }
        }
        return toAjax(seriesAppService.deleteSeriesByIds(seriesIds));
    }

}

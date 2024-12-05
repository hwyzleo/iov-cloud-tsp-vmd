package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.SeriesMpt;

/**
 * 车系相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface SeriesMptApi {

    /**
     * 分页查询车系信息
     *
     * @param series 车系信息
     * @return 车系信息列表
     */
    TableDataInfo list(SeriesMpt series);

    /**
     * 导出车系信息
     *
     * @param response 响应
     * @param series   车系信息
     */
    void export(HttpServletResponse response, SeriesMpt series);

    /**
     * 根据车系ID获取车系信息
     *
     * @param seriesId 车系ID
     * @return 车系信息
     */
    AjaxResult getInfo(Long seriesId);

    /**
     * 新增车系信息
     *
     * @param series 车系信息
     * @return 结果
     */
    AjaxResult add(SeriesMpt series);

    /**
     * 修改保存车系信息
     *
     * @param series 车系信息
     * @return 结果
     */
    AjaxResult edit(SeriesMpt series);

    /**
     * 删除车系信息
     *
     * @param seriesIds 车系ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] seriesIds);

}

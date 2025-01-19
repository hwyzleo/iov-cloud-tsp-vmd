package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ExteriorMpt;

import java.util.List;

/**
 * 车身颜色相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface ExteriorMptApi {

    /**
     * 分页查询车身颜色信息
     *
     * @param exterior 车身颜色信息
     * @return 车身颜色信息列表
     */
    TableDataInfo list(ExteriorMpt exterior);

    /**
     * 获取指定车辆平台及车系下的所有车身颜色
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @return 车身颜色信息列表
     */
    List<ExteriorMpt> listByPlatformCodeAndSeriesCode(String platformCode, String seriesCode);

    /**
     * 导出车身颜色信息
     *
     * @param response 响应
     * @param exterior 车型信息
     */
    void export(HttpServletResponse response, ExteriorMpt exterior);

    /**
     * 根据车身颜色ID获取车身颜色信息
     *
     * @param exteriorId 车身颜色ID
     * @return 车身颜色信息
     */
    AjaxResult getInfo(Long exteriorId);

    /**
     * 新增车身颜色信息
     *
     * @param exterior 车身颜色信息
     * @return 结果
     */
    AjaxResult add(ExteriorMpt exterior);

    /**
     * 修改保存车身颜色信息
     *
     * @param exterior 车身颜色信息
     * @return 结果
     */
    AjaxResult edit(ExteriorMpt exterior);

    /**
     * 删除车身颜色信息
     *
     * @param exteriorIds 车身颜色ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] exteriorIds);

}

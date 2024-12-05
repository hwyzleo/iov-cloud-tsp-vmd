package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.PlatformMpt;

/**
 * 车辆平台相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface PlatformMptApi {

    /**
     * 分页查询车辆平台信息
     *
     * @param platform 车辆平台信息
     * @return 车辆平台信息列表
     */
    TableDataInfo list(PlatformMpt platform);

    /**
     * 导出车辆平台信息
     *
     * @param response 响应
     * @param platform 车辆平台信息
     */
    void export(HttpServletResponse response, PlatformMpt platform);

    /**
     * 根据车辆平台ID获取车辆平台信息
     *
     * @param platformId 车辆平台ID
     * @return 车辆平台信息
     */
    AjaxResult getInfo(Long platformId);

    /**
     * 新增车辆平台信息
     *
     * @param platform 车辆平台信息
     * @return 结果
     */
    AjaxResult add(PlatformMpt platform);

    /**
     * 修改保存车辆平台信息
     *
     * @param platform 车辆平台信息
     * @return 结果
     */
    AjaxResult edit(PlatformMpt platform);

    /**
     * 删除车辆平台信息
     *
     * @param platformIds 车辆平台ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] platformIds);

}

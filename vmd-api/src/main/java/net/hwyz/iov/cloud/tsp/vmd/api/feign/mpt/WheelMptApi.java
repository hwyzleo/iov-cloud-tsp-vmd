package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.WheelMpt;

import java.util.List;

/**
 * 轮胎轮毂相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface WheelMptApi {

    /**
     * 分页查询轮胎轮毂信息
     *
     * @param wheel 轮胎轮毂信息
     * @return 轮胎轮毂信息列表
     */
    TableDataInfo list(WheelMpt wheel);

    /**
     * 获取指定车辆平台及车系下的所有轮胎轮毂
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @return 轮胎轮毂信息列表
     */
    List<WheelMpt> listByPlatformCodeAndSeriesCode(String platformCode, String seriesCode);

    /**
     * 导出轮胎轮毂信息
     *
     * @param response 响应
     * @param wheel    轮胎轮毂信息
     */
    void export(HttpServletResponse response, WheelMpt wheel);

    /**
     * 根据轮胎轮毂ID获取轮胎轮毂信息
     *
     * @param wheelId 轮胎轮毂ID
     * @return 轮胎轮毂信息
     */
    AjaxResult getInfo(Long wheelId);

    /**
     * 新增轮胎轮毂信息
     *
     * @param wheel 轮胎轮毂信息
     * @return 结果
     */
    AjaxResult add(WheelMpt wheel);

    /**
     * 修改保存轮胎轮毂信息
     *
     * @param wheel 轮胎轮毂信息
     * @return 结果
     */
    AjaxResult edit(WheelMpt wheel);

    /**
     * 删除轮胎轮毂信息
     *
     * @param wheelIds 轮胎轮毂ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] wheelIds);

}

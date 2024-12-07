package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BrandMpt;

/**
 * 车辆品牌相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface BrandMptApi {

    /**
     * 分页查询车辆品牌信息
     *
     * @param brand 车辆品牌信息
     * @return 车辆品牌信息列表
     */
    TableDataInfo list(BrandMpt brand);

    /**
     * 导出车辆品牌信息
     *
     * @param response 响应
     * @param brand    车辆品牌信息
     */
    void export(HttpServletResponse response, BrandMpt brand);

    /**
     * 根据车辆品牌ID获取车辆品牌信息
     *
     * @param brandId 车辆品牌ID
     * @return 车辆品牌信息
     */
    AjaxResult getInfo(Long brandId);

    /**
     * 新增车辆品牌信息
     *
     * @param brand 车辆品牌信息
     * @return 结果
     */
    AjaxResult add(BrandMpt brand);

    /**
     * 修改保存车辆品牌信息
     *
     * @param brand 车辆品牌信息
     * @return 结果
     */
    AjaxResult edit(BrandMpt brand);

    /**
     * 删除车辆品牌信息
     *
     * @param brandIds 车辆品牌ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] brandIds);

}

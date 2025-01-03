package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ManufacturerMpt;

/**
 * 车辆工厂相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface ManufacturerMptApi {

    /**
     * 分页查询车辆工厂信息
     *
     * @param manufacturer 车辆工厂信息
     * @return 车辆工厂信息列表
     */
    TableDataInfo list(ManufacturerMpt manufacturer);

    /**
     * 导出车辆工厂信息
     *
     * @param response     响应
     * @param manufacturer 车辆工厂信息
     */
    void export(HttpServletResponse response, ManufacturerMpt manufacturer);

    /**
     * 根据车辆工厂ID获取车辆工厂信息
     *
     * @param manufacturerId 车辆工厂ID
     * @return 车辆工厂信息
     */
    AjaxResult getInfo(Long manufacturerId);

    /**
     * 新增车辆工厂信息
     *
     * @param manufacturer 车辆工厂信息
     * @return 结果
     */
    AjaxResult add(ManufacturerMpt manufacturer);

    /**
     * 修改保存车辆工厂信息
     *
     * @param manufacturer 车辆工厂信息
     * @return 结果
     */
    AjaxResult edit(ManufacturerMpt manufacturer);

    /**
     * 删除车辆工厂信息
     *
     * @param manufacturerIds 车辆工厂ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] manufacturerIds);

}

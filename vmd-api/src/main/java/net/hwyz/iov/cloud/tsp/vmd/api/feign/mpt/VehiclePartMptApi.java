package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehiclePartMpt;

/**
 * 车辆零件相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface VehiclePartMptApi {

    /**
     * 分页查询车辆零件
     *
     * @param vehiclePart 车辆零件
     * @return 车辆零件列表
     */
    TableDataInfo list(VehiclePartMpt vehiclePart);

    /**
     * 导出车辆零件
     *
     * @param response    响应
     * @param vehiclePart 车辆零件
     */
    void export(HttpServletResponse response, VehiclePartMpt vehiclePart);

    /**
     * 根据车辆零件ID获取车辆零件
     *
     * @param vehiclePartId 车辆零件ID
     * @return 车辆零件
     */
    AjaxResult getInfo(Long vehiclePartId);

    /**
     * 新增车辆零件
     *
     * @param vehiclePart 车辆零件
     * @return 结果
     */
    AjaxResult add(VehiclePartMpt vehiclePart);

    /**
     * 修改保存车辆零件
     *
     * @param vehiclePart 车辆零件
     * @return 结果
     */
    AjaxResult edit(VehiclePartMpt vehiclePart);

    /**
     * 删除车辆零件
     *
     * @param vehiclePartIds 车辆零件ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] vehiclePartIds);

}

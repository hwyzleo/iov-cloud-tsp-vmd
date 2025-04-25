package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleImportDataMpt;

/**
 * 车辆导入数据相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface VehicleImportDataMptApi {

    /**
     * 分页查询车辆导入数据
     *
     * @param vehicleImportData 车辆导入数据
     * @return 车辆导入数据列表
     */
    TableDataInfo list(VehicleImportDataMpt vehicleImportData);

    /**
     * 导出车辆导入数据
     *
     * @param response          响应
     * @param vehicleImportData 车辆导入数据
     */
    void export(HttpServletResponse response, VehicleImportDataMpt vehicleImportData);

    /**
     * 根据车辆导入数据ID获取车辆导入数据
     *
     * @param vehicleImportDataId 车辆导入数据ID
     * @return 车辆导入数据
     */
    AjaxResult getInfo(Long vehicleImportDataId);

    /**
     * 新增车辆导入数据
     *
     * @param vehicleImportData 车辆导入数据
     * @return 结果
     */
    AjaxResult add(VehicleImportDataMpt vehicleImportData);

    /**
     * 修改保存车辆导入数据
     *
     * @param vehicleImportData 车辆导入数据
     * @return 结果
     */
    AjaxResult edit(VehicleImportDataMpt vehicleImportData);

    /**
     * 删除车辆导入数据
     *
     * @param vehicleImportDataIds 车辆导入数据ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] vehicleImportDataIds);

}

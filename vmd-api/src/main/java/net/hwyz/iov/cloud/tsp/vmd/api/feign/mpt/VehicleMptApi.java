package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleLifecycleMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleMpt;

import java.util.List;

/**
 * 车辆相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface VehicleMptApi {

    /**
     * 分页查询车辆信息
     *
     * @param vehicle 车辆信息
     * @return 车辆信息列表
     */
    TableDataInfo list(VehicleMpt vehicle);

    /**
     * 分页查询可分配车辆信息
     *
     * @param vehicle 车辆信息
     * @return 车辆信息列表
     */
    TableDataInfo listAssignable(VehicleMpt vehicle);

    /**
     * 分页查询车辆生命周期
     *
     * @param vin 车辆VIN号
     * @return 车辆生命周期列表
     */
    List<VehicleLifecycleMpt> listLifecycle(String vin);

    /**
     * 导出车辆信息
     *
     * @param response 响应
     * @param vehicle  车辆信息
     */
    void export(HttpServletResponse response, VehicleMpt vehicle);

    /**
     * 根据车辆ID获取车辆信息
     *
     * @param vehicleId 车辆ID
     * @return 车辆信息
     */
    AjaxResult getInfo(Long vehicleId);

    /**
     * 新增车辆信息
     *
     * @param vehicle 车辆信息
     * @return 结果
     */
    AjaxResult add(VehicleMpt vehicle);

    /**
     * 新增车辆生命周期
     *
     * @param vin              车架号
     * @param vehicleLifecycle 车辆生命周期
     * @return 结果
     */
    AjaxResult addLifecycle(String vin, VehicleLifecycleMpt vehicleLifecycle);

    /**
     * 修改保存车辆信息
     *
     * @param vehicle 车辆信息
     * @return 结果
     */
    AjaxResult edit(VehicleMpt vehicle);

    /**
     * 修改保存车辆生命周期
     *
     * @param vin              车架号
     * @param vehicleLifecycle 车辆生命周期
     * @return 结果
     */
    AjaxResult editLifecycle(String vin, VehicleLifecycleMpt vehicleLifecycle);

    /**
     * 删除车辆信息
     *
     * @param vehicleIds 车辆ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] vehicleIds);

    /**
     * 删除车辆生命周期
     *
     * @param vin          车架号
     * @param lifecycleIds 车辆生命周期ID数组
     * @return 结果
     */
    AjaxResult removeLifecycle(String vin, Long[] lifecycleIds);

}

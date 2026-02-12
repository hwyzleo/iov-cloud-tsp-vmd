package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleConfigItemMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleConfigMpt;

/**
 * 车辆配置相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface VehicleConfigMptApi {

    /**
     * 分页查询车辆配置
     *
     * @param vehicleConfig 车辆配置
     * @return 车辆配置列表
     */
    TableDataInfo list(VehicleConfigMpt vehicleConfig);

    /**
     * 分页查询车辆配置项
     *
     * @param vin               车架号
     * @param vehicleConfigItem 车辆配置项
     * @return 车辆配置列表
     */
    TableDataInfo listConfigItem(String vin, VehicleConfigItemMpt vehicleConfigItem);

    /**
     * 导出车辆配置
     *
     * @param response      响应
     * @param vehicleConfig 车辆配置
     */
    void export(HttpServletResponse response, VehicleConfigMpt vehicleConfig);

    /**
     * 根据车辆配置ID获取车辆配置
     *
     * @param vehicleConfigId 车辆配置ID
     * @return 车辆配置
     */
    AjaxResult getInfo(Long vehicleConfigId);

    /**
     * 根据车辆配置项ID获取车辆配置项
     *
     * @param vin                 车架号
     * @param vehicleConfigItemId 车辆配置项ID
     * @return 车辆配置项
     */
    AjaxResult getConfigItemInfo(String vin, Long vehicleConfigItemId);

}

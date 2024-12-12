package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.MesVehicleDataMpt;

/**
 * 工厂车辆数据相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface MesVehicleDataMptApi {

    /**
     * 分页查询MES车辆数据
     *
     * @param mesVehicleData MES车辆数据
     * @return MES车辆数据列表
     */
    TableDataInfo list(MesVehicleDataMpt mesVehicleData);

    /**
     * 导出MES车辆数据
     *
     * @param response       响应
     * @param mesVehicleData MES车辆数据
     */
    void export(HttpServletResponse response, MesVehicleDataMpt mesVehicleData);

    /**
     * 根据MES车辆数据ID获取MES车辆数据
     *
     * @param mesVehicleDataId MES车辆数据ID
     * @return MES车辆数据
     */
    AjaxResult getInfo(Long mesVehicleDataId);

    /**
     * 新增MES车辆数据
     *
     * @param mesVehicleData MES车辆数据
     * @return 结果
     */
    AjaxResult add(MesVehicleDataMpt mesVehicleData);

    /**
     * 修改保存MES车辆数据
     *
     * @param mesVehicleData MES车辆数据
     * @return 结果
     */
    AjaxResult edit(MesVehicleDataMpt mesVehicleData);

    /**
     * 删除MES车辆数据
     *
     * @param mesVehicleDataIds MES车辆数据ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] mesVehicleDataIds);

}

package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.DeviceMpt;

/**
 * 设备信息相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface DeviceMptApi {

    /**
     * 分页查询设备信息
     *
     * @param device 设备信息
     * @return 设备信息列表
     */
    TableDataInfo list(DeviceMpt device);

    /**
     * 获取所有设备项
     *
     * @return 设备类型列表
     */
    AjaxResult listAllDeviceItem();

    /**
     * 导出设备信息
     *
     * @param response 响应
     * @param device   设备信息
     */
    void export(HttpServletResponse response, DeviceMpt device);

    /**
     * 根据车载设备ID获取设备信息
     *
     * @param deviceId 设备信息ID
     * @return 设备信息
     */
    AjaxResult getInfo(Long deviceId);

    /**
     * 新增设备信息
     *
     * @param device 设备信息
     * @return 结果
     */
    AjaxResult add(DeviceMpt device);

    /**
     * 修改保存设备信息
     *
     * @param device 设备信息
     * @return 结果
     */
    AjaxResult edit(DeviceMpt device);

    /**
     * 删除设备信息
     *
     * @param deviceIds 设备信息ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] deviceIds);

}

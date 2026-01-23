package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.VehicleDomainMpt;

/**
 * 车载域相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface VehicleDomainMptApi {

    /**
     * 分页查询车载域信息
     *
     * @param vehicleDomain 车载域信息
     * @return 车载域信息列表
     */
    TableDataInfo list(VehicleDomainMpt vehicleDomain);

    /**
     * 导出车载域信息
     *
     * @param response      响应
     * @param vehicleDomain 车载域信息
     */
    void export(HttpServletResponse response, VehicleDomainMpt vehicleDomain);

    /**
     * 根据车载域ID获取车载域信息
     *
     * @param vehicleDomainId 车载域ID
     * @return 车载域信息
     */
    AjaxResult getInfo(Long vehicleDomainId);

    /**
     * 新增车载域信息
     *
     * @param vehicleDomain 车载域信息
     * @return 结果
     */
    AjaxResult add(VehicleDomainMpt vehicleDomain);

    /**
     * 修改保存车载域信息
     *
     * @param vehicleDomain 车载域信息
     * @return 结果
     */
    AjaxResult edit(VehicleDomainMpt vehicleDomain);

    /**
     * 删除车载域信息
     *
     * @param vehicleDomainIds 车载域ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] vehicleDomainIds);

}

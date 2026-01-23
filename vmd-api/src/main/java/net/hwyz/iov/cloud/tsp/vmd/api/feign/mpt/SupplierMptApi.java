package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.SupplierMpt;

/**
 * 供应商相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface SupplierMptApi {

    /**
     * 分页查询供应商信息
     *
     * @param supplier 供应商信息
     * @return 供应商信息列表
     */
    TableDataInfo list(SupplierMpt supplier);

    /**
     * 导出供应商信息
     *
     * @param response 响应
     * @param supplier 供应商信息
     */
    void export(HttpServletResponse response, SupplierMpt supplier);

    /**
     * 根据供应商ID获取供应商信息
     *
     * @param supplierId 供应商ID
     * @return 供应商信息
     */
    AjaxResult getInfo(Long supplierId);

    /**
     * 新增供应商信息
     *
     * @param supplier 供应商信息
     * @return 结果
     */
    AjaxResult add(SupplierMpt supplier);

    /**
     * 修改保存供应商信息
     *
     * @param supplier 供应商信息
     * @return 结果
     */
    AjaxResult edit(SupplierMpt supplier);

    /**
     * 删除供应商信息
     *
     * @param supplierIds 供应商ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] supplierIds);

}

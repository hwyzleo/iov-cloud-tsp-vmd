package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.PartMpt;

/**
 * 零件信息相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface PartMptApi {

    /**
     * 分页查询零件信息
     *
     * @param part 零件信息
     * @return 零件信息列表
     */
    TableDataInfo list(PartMpt part);

    /**
     * 导出零件信息
     *
     * @param response 响应
     * @param part     零件信息
     */
    void export(HttpServletResponse response, PartMpt part);

    /**
     * 根据零件信息ID获取设备信息
     *
     * @param partId 零件信息ID
     * @return 零件信息
     */
    AjaxResult getInfo(Long partId);

    /**
     * 新增零件信息
     *
     * @param part 零件信息
     * @return 结果
     */
    AjaxResult add(PartMpt part);

    /**
     * 修改保存零件信息
     *
     * @param part 零件信息
     * @return 结果
     */
    AjaxResult edit(PartMpt part);

    /**
     * 删除零件信息
     *
     * @param partIds 零件信息ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] partIds);

}

package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ModelMpt;

/**
 * 车型相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface ModelMptApi {

    /**
     * 分页查询车型信息
     *
     * @param model 车型信息
     * @return 车型信息列表
     */
    TableDataInfo list(ModelMpt model);

    /**
     * 导出车型信息
     *
     * @param response 响应
     * @param model    车型信息
     */
    void export(HttpServletResponse response, ModelMpt model);

    /**
     * 根据车型ID获取车型信息
     *
     * @param modelId 车型ID
     * @return 车型信息
     */
    AjaxResult getInfo(Long modelId);

    /**
     * 新增车型信息
     *
     * @param model 车型信息
     * @return 结果
     */
    AjaxResult add(ModelMpt model);

    /**
     * 修改保存车型信息
     *
     * @param model 车型信息
     * @return 结果
     */
    AjaxResult edit(ModelMpt model);

    /**
     * 删除车型信息
     *
     * @param modelIds 车型ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] modelIds);

}

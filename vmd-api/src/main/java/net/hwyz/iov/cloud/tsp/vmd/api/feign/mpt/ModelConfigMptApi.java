package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ModelConfigMpt;

/**
 * 车型配置相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface ModelConfigMptApi {

    /**
     * 分页查询车型配置信息
     *
     * @param modelConfig 车型配置信息
     * @return 车型配置信息列表
     */
    TableDataInfo list(ModelConfigMpt modelConfig);

    /**
     * 导出车型配置信息
     *
     * @param response    响应
     * @param modelConfig 车型配置信息
     */
    void export(HttpServletResponse response, ModelConfigMpt modelConfig);

    /**
     * 根据车型配置ID获取车型配置信息
     *
     * @param modelConfigId 车型配置ID
     * @return 车型配置信息
     */
    AjaxResult getInfo(Long modelConfigId);

    /**
     * 新增车型配置信息
     *
     * @param modelConfig 车型配置信息
     * @return 结果
     */
    AjaxResult add(ModelConfigMpt modelConfig);

    /**
     * 修改保存车型配置信息
     *
     * @param modelConfig 车型配置信息
     * @return 结果
     */
    AjaxResult edit(ModelConfigMpt modelConfig);

    /**
     * 删除车型配置信息
     *
     * @param modelConfigIds 车型配置ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] modelConfigIds);

}

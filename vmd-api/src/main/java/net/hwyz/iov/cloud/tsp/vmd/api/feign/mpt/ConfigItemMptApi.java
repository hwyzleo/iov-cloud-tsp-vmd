package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ConfigItemMpt;

/**
 * 配置项相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface ConfigItemMptApi {

    /**
     * 分页查询配置项信息
     *
     * @param configItem 配置项信息
     * @return 配置项信息列表
     */
    TableDataInfo list(ConfigItemMpt configItem);

    /**
     * 导出配置项信息
     *
     * @param response   响应
     * @param configItem 配置项信息
     */
    void export(HttpServletResponse response, ConfigItemMpt configItem);

    /**
     * 根据配置项ID获取配置项信息
     *
     * @param configItemId 配置项ID
     * @return 配置项信息
     */
    AjaxResult getInfo(Long configItemId);

    /**
     * 新增配置项信息
     *
     * @param configItem 配置项信息
     * @return 结果
     */
    AjaxResult add(ConfigItemMpt configItem);

    /**
     * 修改保存配置项信息
     *
     * @param configItem 配置项信息
     * @return 结果
     */
    AjaxResult edit(ConfigItemMpt configItem);

    /**
     * 删除配置项信息
     *
     * @param configItemIds 配置项ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] configItemIds);

}

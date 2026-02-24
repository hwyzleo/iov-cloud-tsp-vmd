package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BuildConfigMpt;

/**
 * 生产配置相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface BuildConfigMptApi {

    /**
     * 分页查询生产配置信息
     *
     * @param buildConfig 生产配置信息
     * @return 生产配置信息列表
     */
    TableDataInfo list(BuildConfigMpt buildConfig);

    /**
     * 导出生产配置信息
     *
     * @param response    响应
     * @param buildConfig 生产配置信息
     */
    void export(HttpServletResponse response, BuildConfigMpt buildConfig);

    /**
     * 根据生产配置ID获取生产配置信息
     *
     * @param buildConfigId 生产配置ID
     * @return 生产配置信息
     */
    AjaxResult getInfo(Long buildConfigId);

    /**
     * 新增生产配置信息
     *
     * @param buildConfig 生产配置信息
     * @return 结果
     */
    AjaxResult add(BuildConfigMpt buildConfig);

    /**
     * 修改保存生产配置信息
     *
     * @param buildConfig 生产配置信息
     * @return 结果
     */
    AjaxResult edit(BuildConfigMpt buildConfig);

    /**
     * 删除生产配置信息
     *
     * @param buildConfigIds 生产配置ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] buildConfigIds);

}

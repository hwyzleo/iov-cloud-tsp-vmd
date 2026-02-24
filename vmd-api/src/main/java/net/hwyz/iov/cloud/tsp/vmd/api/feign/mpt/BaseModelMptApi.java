package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BaseModelFeatureCodeMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BaseModelMpt;

import java.util.List;

/**
 * 基础车型相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface BaseModelMptApi {

    /**
     * 分页查询基础车型信息
     *
     * @param baseModel 基础车型信息
     * @return 基础车型信息列表
     */
    TableDataInfo list(BaseModelMpt baseModel);

    /**
     * 查询基础车型下特征值
     *
     * @param baseModelCode        基础车型编码
     * @param baseModelFeatureCode 基础车型特征值
     * @return 基础车型下特征值列表
     */
    AjaxResult listFeatureCode(String baseModelCode, BaseModelFeatureCodeMpt baseModelFeatureCode);

    /**
     * 获取指定车辆平台及车系及车型下的所有基础车型
     *
     * @param platformCode 车辆平台编码
     * @param seriesCode   车系编码
     * @param modelCode    车型编码
     * @return 基础车型信息列表
     */
    List<BaseModelMpt> listByPlatformCodeAndSeriesCodeAndModelCode(String platformCode, String seriesCode, String modelCode);

    /**
     * 导出基础车型信息
     *
     * @param response  响应
     * @param baseModel 基础车型信息
     */
    void export(HttpServletResponse response, BaseModelMpt baseModel);

    /**
     * 根据基础车型ID获取基础车型信息
     *
     * @param baseModelId 基础车型ID
     * @return 基础车型信息
     */
    AjaxResult getInfo(Long baseModelId);

    /**
     * 根据基础车型特征值ID获取基础车型特征值信息
     *
     * @param baseModelCode          基础车型编码
     * @param baseModelFeatureCodeId 基础车型特征值ID
     * @return 基础车型特征值信息
     */
    AjaxResult getFeatureCodeInfo(String baseModelCode, Long baseModelFeatureCodeId);

    /**
     * 新增基础车型信息
     *
     * @param baseModel 基础车型信息
     * @return 结果
     */
    AjaxResult add(BaseModelMpt baseModel);

    /**
     * 新增基础车型特征值
     *
     * @param baseModelCode        基础车型编码
     * @param baseModelFeatureCode 基础车型特征值
     * @return 结果
     */
    AjaxResult addFeatureCode(String baseModelCode, BaseModelFeatureCodeMpt baseModelFeatureCode);

    /**
     * 修改保存基础车型信息
     *
     * @param baseModel 基础车型信息
     * @return 结果
     */
    AjaxResult edit(BaseModelMpt baseModel);

    /**
     * 修改保存基础车型特征值
     *
     * @param baseModelCode        基础车型编码
     * @param baseModelFeatureCode 基础车型特征值
     * @return 结果
     */
    AjaxResult editFeatureCode(String baseModelCode, BaseModelFeatureCodeMpt baseModelFeatureCode);

    /**
     * 删除基础车型信息
     *
     * @param baseModelIds 基础车型ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] baseModelIds);

    /**
     * 删除基础车型特征值
     *
     * @param baseModelCode           基础车型编码
     * @param baseModelFeatureCodeIds 基础车型特征值ID数组
     * @return 结果
     */
    AjaxResult removeFeatureCode(String baseModelCode, Long[] baseModelFeatureCodeIds);

}

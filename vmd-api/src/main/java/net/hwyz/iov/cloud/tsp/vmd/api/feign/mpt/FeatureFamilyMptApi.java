package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.FeatureCodeMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.FeatureFamilyMpt;

/**
 * 车辆特征族相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface FeatureFamilyMptApi {

    /**
     * 分页查询车辆特征族信息
     *
     * @param featureFamily 车辆特征族信息
     * @return 车辆特征族信息列表
     */
    TableDataInfo list(FeatureFamilyMpt featureFamily);

    /**
     * 分页查询车辆特征族下特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCode     车辆特征值信息
     * @return 车辆特征族信息列表
     */
    TableDataInfo listFeatureCode(Long featureFamilyId, FeatureCodeMpt featureCode);

    /**
     * 获取车辆特征族列表
     *
     * @return 车辆特征族列表
     */
    AjaxResult listAllFeatureFamily();

    /**
     * 获取车辆特征值列表
     *
     * @param familyCode 车辆特征族代码
     * @return 车辆特征值列表
     */
    AjaxResult listAllFeatureCode(String familyCode);

    /**
     * 导出车辆特征族信息
     *
     * @param response      响应
     * @param featureFamily 车辆特征族信息
     */
    void export(HttpServletResponse response, FeatureFamilyMpt featureFamily);

    /**
     * 根据车辆特征族ID获取车辆特征族信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @return 车辆特征族信息
     */
    AjaxResult getInfo(Long featureFamilyId);

    /**
     * 根据车辆特征值ID获取车辆特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCodeId   车辆特征值ID
     * @return 车辆特征值信息
     */
    AjaxResult getFeatureCodeInfo(Long featureFamilyId, Long featureCodeId);

    /**
     * 新增车辆特征族信息
     *
     * @param featureFamily 车辆特征族信息
     * @return 结果
     */
    AjaxResult add(FeatureFamilyMpt featureFamily);

    /**
     * 新增车辆特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCode     车辆特征值信息
     * @return 结果
     */
    AjaxResult addFeatureCode(Long featureFamilyId, FeatureCodeMpt featureCode);

    /**
     * 修改保存车辆特征族信息
     *
     * @param featureFamily 车辆特征族信息
     * @return 结果
     */
    AjaxResult edit(FeatureFamilyMpt featureFamily);

    /**
     * 修改保存车辆特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCode     车辆特征值信息
     * @return 结果
     */
    AjaxResult editFeatureCode(Long featureFamilyId, FeatureCodeMpt featureCode);

    /**
     * 删除车辆特征族信息
     *
     * @param featureFamilyIds 车辆特征族ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] featureFamilyIds);

    /**
     * 删除车辆特征值信息
     *
     * @param featureFamilyId 车辆特征族ID
     * @param featureCodeIds  车辆特征值ID数组
     * @return 结果
     */
    AjaxResult removeFeatureCode(Long featureFamilyId, Long[] featureCodeIds);

}

package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.BasicModelMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ModelMpt;

import java.util.List;

/**
 * 基础车型相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface BasicModelMptApi {

    /**
     * 分页查询基础车型信息
     *
     * @param basicModel 基础车型信息
     * @return 基础车型信息列表
     */
    TableDataInfo list(BasicModelMpt basicModel);

    /**
     * 获取指定车辆平台及车系及车型下的所有基础车型
     *
     * @param platformCode 车辆平台编码
     * @param seriesCode   车系编码
     * @param modelCode    车型编码
     * @return 基础车型信息列表
     */
    List<BasicModelMpt> listByPlatformCodeAndSeriesCodeAndModelCode(String platformCode, String seriesCode, String modelCode);

    /**
     * 导出基础车型信息
     *
     * @param response   响应
     * @param basicModel 基础车型信息
     */
    void export(HttpServletResponse response, BasicModelMpt basicModel);

    /**
     * 根据基础车型ID获取基础车型信息
     *
     * @param basicModelId 基础车型ID
     * @return 基础车型信息
     */
    AjaxResult getInfo(Long basicModelId);

    /**
     * 新增基础车型信息
     *
     * @param basicModel 基础车型信息
     * @return 结果
     */
    AjaxResult add(BasicModelMpt basicModel);

    /**
     * 修改保存基础车型信息
     *
     * @param basicModel 基础车型信息
     * @return 结果
     */
    AjaxResult edit(BasicModelMpt basicModel);

    /**
     * 删除基础车型信息
     *
     * @param basicModelIds 基础车型ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] basicModelIds);

}

package net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt;

import jakarta.servlet.http.HttpServletResponse;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.InteriorMpt;

import java.util.List;

/**
 * 内饰颜色相关管理后台接口
 *
 * @author hwyz_leo
 */
public interface InteriorMptApi {

    /**
     * 分页查询内饰颜色信息
     *
     * @param interior 内饰颜色信息
     * @return 内饰颜色信息列表
     */
    TableDataInfo list(InteriorMpt interior);

    /**
     * 获取指定车辆平台及车系下的所有内饰颜色
     *
     * @param platformCode 车辆平台代码
     * @param seriesCode   车系代码
     * @return 内饰颜色信息列表
     */
    List<InteriorMpt> listByPlatformCodeAndSeriesCode(String platformCode, String seriesCode);

    /**
     * 导出内饰颜色信息
     *
     * @param response 响应
     * @param interior 内饰颜色信息
     */
    void export(HttpServletResponse response, InteriorMpt interior);

    /**
     * 根据内饰颜色ID获取内饰颜色信息
     *
     * @param interiorId 内饰颜色ID
     * @return 内饰颜色信息
     */
    AjaxResult getInfo(Long interiorId);

    /**
     * 新增内饰颜色信息
     *
     * @param interior 内饰颜色信息
     * @return 结果
     */
    AjaxResult add(InteriorMpt interior);

    /**
     * 修改保存内饰颜色信息
     *
     * @param interior 内饰颜色信息
     * @return 结果
     */
    AjaxResult edit(InteriorMpt interior);

    /**
     * 删除内饰颜色信息
     *
     * @param interiorIds 内饰颜色ID数组
     * @return 结果
     */
    AjaxResult remove(Long[] interiorIds);

}

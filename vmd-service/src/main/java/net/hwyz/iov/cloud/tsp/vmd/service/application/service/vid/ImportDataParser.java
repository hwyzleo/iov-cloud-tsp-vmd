package net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid;

import cn.hutool.json.JSONObject;

/**
 * 导入数据解析器
 *
 * @author hwyz_leo
 */
public interface ImportDataParser {

    /**
     * 解析数据
     *
     * @param batchNum 批次号
     * @param dataJson 数据JSON
     */
    void parse(String batchNum, JSONObject dataJson);

}

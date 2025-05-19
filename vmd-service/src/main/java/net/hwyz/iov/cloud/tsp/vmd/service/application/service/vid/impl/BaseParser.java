package net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;

/**
 * 解析器基础类
 *
 * @author hwyz_leo
 */
@Slf4j
public class BaseParser {

    /**
     * 获取头部分
     *
     * @param dataJson 整体数据JSON对象
     * @return 头部分JSON对象
     */
    protected JSONObject getHeader(JSONObject dataJson) {
        JSONObject request = dataJson.getJSONObject("REQUEST");
        return request.getJSONObject("HEAD");
    }

    /**
     * 获取供应商
     *
     * @param dataJson 整体数据JSON对象
     * @return 供应商代码
     */
    protected String getSupplier(JSONObject dataJson) {
        JSONObject head = getHeader(dataJson);
        String supplier = null;
        if (ObjUtil.isNotNull(head)) {
            supplier = head.getStr("ACCOUNT");
        }
        return supplier;
    }

    /**
     * 获取数据部分
     *
     * @param dataJson 整体数据JSON对象
     * @return 数据部分JSON对象
     */
    protected JSONObject getData(JSONObject dataJson) {
        JSONObject request = dataJson.getJSONObject("REQUEST");
        return request.getJSONObject("DATA");
    }

    /**
     * 处理车辆信息数据
     *
     * @param itemJson      车辆JSON数据
     * @param vehicleInfoPo 车辆信息对象
     * @param jsonKey       解析JSON KEY
     * @param propertyName  对象属性名
     * @param keyDesc       KEY描述
     * @param batchNum      批次号
     * @param vin           车架号
     */
    protected void handleVehicleInfo(JSONObject itemJson, Object vehicleInfoPo, String jsonKey, String propertyName,
                                     String keyDesc, String batchNum, String vin) {
        String keyValue = itemJson.getStr(jsonKey);
        if (StrUtil.isNotBlank(keyValue)) {
            Object fieldValue = BeanUtil.getFieldValue(vehicleInfoPo, propertyName);
            if (ObjUtil.isNull(fieldValue) || StrUtil.isBlank(fieldValue.toString())) {
                BeanUtil.setFieldValue(vehicleInfoPo, propertyName, keyValue.trim().toUpperCase());
            } else if (!keyValue.trim().equalsIgnoreCase(fieldValue.toString())) {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]{}[{}]与原数据[{}]不一致", batchNum, vin, keyDesc, keyValue.trim(),
                        fieldValue);
            }
        } else {
            logger.warn("车辆导入数据批次号[{}]车辆[{}]{}为空", batchNum, vin, keyDesc);
        }
    }

}

package net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.enums.EcuType;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.VehiclePartAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.ImportDataParser;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPartPo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 中央计算平台数据解析器V1.0
 *
 * @author hwyz_leo
 */
@Slf4j
@RequiredArgsConstructor
@Component("ccpDataParserV1.0")
public class CcpDataParserV1_0 implements ImportDataParser {

    private final VehiclePartAppService vehiclePartAppService;

    @Override
    public void parse(String batchNum, JSONObject dataJson) {
        JSONObject request = dataJson.getJSONObject("REQUEST");
        JSONObject head = request.getJSONObject("HEAD");
        String supplier = null;
        if (ObjUtil.isNotNull(head)) {
            supplier = head.getStr("ACCOUNT");
            if (StrUtil.isBlank(supplier)) {
                logger.warn("CCP导入数据批次号[{}]供应商代码为空", batchNum);
            }
        } else {
            logger.warn("CCP导入数据批次号[{}]HEAD为空", batchNum);
        }
        JSONObject data = request.getJSONObject("DATA");
        JSONArray items = data.getJSONArray("ITEMS");
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String sn = itemJson.getStr("SN");
            if (StrUtil.isBlank(sn)) {
                logger.warn("CCP导入数据批次号[{}]SN为空", batchNum);
                continue;
            }
            VehPartPo ccpPo = vehiclePartAppService.getPartBySn(EcuType.CCP, sn);
            if (ObjUtil.isNull(ccpPo)) {
                ccpPo = new VehPartPo();
                ccpPo.setEcu(EcuType.CCP.name());
                ccpPo.setSn(sn);
            }
            if (StrUtil.isNotBlank(supplier)) {
                if (StrUtil.isBlank(ccpPo.getSupplierCode())) {
                    ccpPo.setSupplierCode(supplier.trim().toUpperCase());
                } else if (!supplier.trim().equalsIgnoreCase(ccpPo.getSupplierCode())) {
                    logger.warn("CCP导入数据批次号[{}]SN[{}]供应商[{}]与原数据[{}]不一致", batchNum, sn, supplier.trim(), ccpPo.getSupplierCode());
                }
            }
            String no = itemJson.getStr("NO");
            if (StrUtil.isNotBlank(no)) {
                if (StrUtil.isBlank(ccpPo.getNo())) {
                    ccpPo.setNo(no.trim());
                } else if (!no.trim().equalsIgnoreCase(ccpPo.getNo())) {
                    logger.warn("CCP导入数据批次号[{}]SN[{}]零件号[{}]与原数据[{}]不一致", batchNum, sn, no.trim(), ccpPo.getNo());
                }
            } else {
                logger.warn("CCP导入数据批次号[{}]SN[{}]零件号为空", batchNum, sn);
            }
            if (ObjUtil.isNull(ccpPo.getId())) {
                vehiclePartAppService.createPart(ccpPo);
            } else {
                vehiclePartAppService.modifyPart(ccpPo);
            }
        }
    }
}

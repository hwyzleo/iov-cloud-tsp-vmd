package net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.impl;

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

/**
 * 信息娱乐模块数据解析器V1.0
 *
 * @author hwyz_leo
 */
@Slf4j
@RequiredArgsConstructor
@Component("idcmDataParserV1.0")
public class IdcmDataParserV1_0 implements ImportDataParser {

    private final VehiclePartAppService vehiclePartAppService;

    @Override
    public void parse(String batchNum, JSONObject dataJson) {
        JSONObject request = dataJson.getJSONObject("REQUEST");
        JSONObject head = request.getJSONObject("HEAD");
        String supplier = null;
        if (ObjUtil.isNotNull(head)) {
            supplier = head.getStr("ACCOUNT");
            if (StrUtil.isBlank(supplier)) {
                logger.warn("IDCM导入数据批次号[{}]供应商代码为空", batchNum);
            }
        } else {
            logger.warn("IDCM导入数据批次号[{}]HEAD为空", batchNum);
        }
        JSONObject data = request.getJSONObject("DATA");
        JSONArray items = data.getJSONArray("ITEMS");
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String sn = itemJson.getStr("SN");
            if (StrUtil.isBlank(sn)) {
                logger.warn("IDCM导入数据批次号[{}]SN为空", batchNum);
                continue;
            }
            VehPartPo idcmPo = vehiclePartAppService.getPartBySn(EcuType.IDCM, sn);
            if (ObjUtil.isNull(idcmPo)) {
                idcmPo = new VehPartPo();
                idcmPo.setEcu(EcuType.IDCM.name());
                idcmPo.setSn(sn);
            }
            if (StrUtil.isNotBlank(supplier)) {
                if (StrUtil.isBlank(idcmPo.getSupplierCode())) {
                    idcmPo.setSupplierCode(supplier.trim().toUpperCase());
                } else if (!supplier.trim().equalsIgnoreCase(idcmPo.getSupplierCode())) {
                    logger.warn("IDCM导入数据批次号[{}]SN[{}]供应商[{}]与原数据[{}]不一致", batchNum, sn, supplier.trim(), idcmPo.getSupplierCode());
                }
            }
            String no = itemJson.getStr("NO");
            if (StrUtil.isNotBlank(no)) {
                if (StrUtil.isBlank(idcmPo.getNo())) {
                    idcmPo.setNo(no.trim());
                } else if (!no.trim().equalsIgnoreCase(idcmPo.getNo())) {
                    logger.warn("IDCM导入数据批次号[{}]SN[{}]零件号[{}]与原数据[{}]不一致", batchNum, sn, no.trim(), idcmPo.getNo());
                }
            } else {
                logger.warn("IDCM导入数据批次号[{}]SN[{}]零件号为空", batchNum, sn);
            }
            if (ObjUtil.isNull(idcmPo.getId())) {
                vehiclePartAppService.createPart(idcmPo);
            } else {
                vehiclePartAppService.modifyPart(idcmPo);
            }
        }
    }
}

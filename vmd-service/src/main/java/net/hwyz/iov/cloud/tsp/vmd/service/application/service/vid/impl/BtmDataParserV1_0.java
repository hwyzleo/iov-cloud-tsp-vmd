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
 * 蓝牙模块数据解析器V1.0
 *
 * @author hwyz_leo
 */
@Slf4j
@RequiredArgsConstructor
@Component("btmDataParserV1.0")
public class BtmDataParserV1_0 implements ImportDataParser {

    private final VehiclePartAppService vehiclePartAppService;

    @Override
    public void parse(String batchNum, JSONObject dataJson) {
        JSONObject request = dataJson.getJSONObject("REQUEST");
        JSONObject head = request.getJSONObject("HEAD");
        String supplier = null;
        if (ObjUtil.isNotNull(head)) {
            supplier = head.getStr("ACCOUNT");
            if (StrUtil.isBlank(supplier)) {
                logger.warn("BTM导入数据批次号[{}]供应商代码为空", batchNum);
            }
        } else {
            logger.warn("BTM导入数据批次号[{}]HEAD为空", batchNum);
        }
        JSONObject data = request.getJSONObject("DATA");
        JSONArray items = data.getJSONArray("ITEMS");
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String sn = itemJson.getStr("SN");
            if (StrUtil.isBlank(sn)) {
                logger.warn("BTM导入数据批次号[{}]SN为空", batchNum);
                continue;
            }
            VehPartPo btmPo = vehiclePartAppService.getPartBySn(EcuType.BTM, sn);
            if (ObjUtil.isNull(btmPo)) {
                btmPo = new VehPartPo();
                btmPo.setEcu(EcuType.BTM.name());
                btmPo.setSn(sn);
            }
            if (StrUtil.isNotBlank(supplier)) {
                if (StrUtil.isBlank(btmPo.getSupplierCode())) {
                    btmPo.setSupplierCode(supplier.trim().toUpperCase());
                } else if (!supplier.trim().equalsIgnoreCase(btmPo.getSupplierCode())) {
                    logger.warn("BTM导入数据批次号[{}]SN[{}]供应商[{}]与原数据[{}]不一致", batchNum, sn, supplier.trim(), btmPo.getSupplierCode());
                }
            }
            String no = itemJson.getStr("NO");
            if (StrUtil.isNotBlank(no)) {
                if (StrUtil.isBlank(btmPo.getNo())) {
                    btmPo.setNo(no.trim());
                } else if (!no.trim().equalsIgnoreCase(btmPo.getNo())) {
                    logger.warn("BTM导入数据批次号[{}]SN[{}]零件号[{}]与原数据[{}]不一致", batchNum, sn, no.trim(), btmPo.getNo());
                }
            } else {
                logger.warn("BTM导入数据批次号[{}]SN[{}]零件号为空", batchNum, sn);
            }
            String hsm = itemJson.getStr("HSM");
            if (StrUtil.isNotBlank(hsm)) {
                if (StrUtil.isBlank(btmPo.getHsm())) {
                    btmPo.setHsm(hsm.trim());
                } else if (!hsm.trim().equalsIgnoreCase(btmPo.getHsm())) {
                    logger.warn("BTM导入数据批次号[{}]SN[{}]硬件安全模块[{}]与原数据[{}]不一致", batchNum, sn, hsm.trim(), btmPo.getHsm());
                }
            } else {
                logger.warn("BTM导入数据批次号[{}]SN[{}]硬件安全模块为空", batchNum, sn);
            }
            String mac = itemJson.getStr("MAC");
            if (StrUtil.isNotBlank(mac)) {
                if (StrUtil.isBlank(btmPo.getMac())) {
                    btmPo.setMac(mac.trim().toUpperCase());
                } else if (!mac.trim().equalsIgnoreCase(btmPo.getMac())) {
                    logger.warn("BTM导入数据批次号[{}]SN[{}]MAC[{}]与原数据[{}]不一致", batchNum, sn, hsm.trim(), btmPo.getMac());
                }
            } else {
                logger.warn("BTM导入数据批次号[{}]SN[{}]MAC为空", batchNum, sn);
            }
            if (ObjUtil.isNull(btmPo.getId())) {
                vehiclePartAppService.createPart(btmPo);
            } else {
                vehiclePartAppService.modifyPart(btmPo);
            }
        }
    }
}

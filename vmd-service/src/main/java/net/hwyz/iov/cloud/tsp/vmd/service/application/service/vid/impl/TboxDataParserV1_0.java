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
 * 车联终端数据解析器V1.0
 *
 * @author hwyz_leo
 */
@Slf4j
@RequiredArgsConstructor
@Component("tboxDataParserV1.0")
public class TboxDataParserV1_0 implements ImportDataParser {

    private final VehiclePartAppService vehiclePartAppService;

    @Override
    public void parse(String batchNum, JSONObject dataJson) {
        JSONObject request = dataJson.getJSONObject("REQUEST");
        JSONObject head = request.getJSONObject("HEAD");
        String supplier = null;
        if (ObjUtil.isNotNull(head)) {
            supplier = head.getStr("ACCOUNT");
            if (StrUtil.isBlank(supplier)) {
                logger.warn("TBox导入数据批次号[{}]供应商代码为空", batchNum);
            }
        } else {
            logger.warn("TBox导入数据批次号[{}]HEAD为空", batchNum);
        }
        JSONObject data = request.getJSONObject("DATA");
        JSONArray items = data.getJSONArray("ITEMS");
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String sn = itemJson.getStr("SN");
            if (StrUtil.isBlank(sn)) {
                logger.warn("TBox导入数据批次号[{}]SN为空", batchNum);
                continue;
            }
            VehPartPo tboxPo = vehiclePartAppService.getPartBySn(EcuType.TBOX, sn);
            if (ObjUtil.isNull(tboxPo)) {
                tboxPo = new VehPartPo();
                tboxPo.setEcu(EcuType.TBOX.name());
                tboxPo.setSn(sn);
            }
            if (StrUtil.isNotBlank(supplier)) {
                if (StrUtil.isBlank(tboxPo.getSupplierCode())) {
                    tboxPo.setSupplierCode(supplier.trim().toUpperCase());
                } else if (!supplier.trim().equalsIgnoreCase(tboxPo.getSupplierCode())) {
                    logger.warn("TBox导入数据批次号[{}]SN[{}]供应商[{}]与原数据[{}]不一致", batchNum, sn, supplier.trim(), tboxPo.getSupplierCode());
                }
            }
            String no = itemJson.getStr("NO");
            if (StrUtil.isNotBlank(no)) {
                if (StrUtil.isBlank(tboxPo.getNo())) {
                    tboxPo.setNo(no.trim());
                } else if (!no.trim().equalsIgnoreCase(tboxPo.getNo())) {
                    logger.warn("TBox导入数据批次号[{}]SN[{}]零件号[{}]与原数据[{}]不一致", batchNum, sn, no.trim(), tboxPo.getNo());
                }
            } else {
                logger.warn("TBox导入数据批次号[{}]SN[{}]零件号为空", batchNum, sn);
            }
            String hsm = itemJson.getStr("HSM");
            if (StrUtil.isNotBlank(hsm)) {
                if (StrUtil.isBlank(tboxPo.getHsm())) {
                    tboxPo.setHsm(hsm.trim());
                } else if (!hsm.trim().equalsIgnoreCase(tboxPo.getHsm())) {
                    logger.warn("TBox导入数据批次号[{}]SN[{}]硬件安全模块[{}]与原数据[{}]不一致", batchNum, sn, hsm.trim(), tboxPo.getHsm());
                }
            } else {
                logger.warn("TBox导入数据批次号[{}]SN[{}]硬件安全模块为空", batchNum, sn);
            }
            String imei = itemJson.getStr("IMEI");
            String iccid1 = itemJson.getStr("ICCID1");
            String iccid2 = itemJson.getStr("ICCID2");
            if (StrUtil.isNotBlank(imei) || StrUtil.isNotBlank(iccid1) || StrUtil.isNotBlank(iccid2)) {
                String extra = tboxPo.getExtra();
                Map<String, String> extraMap;
                if (StrUtil.isBlank(extra)) {
                    extraMap = new HashMap<>();
                    if (StrUtil.isNotBlank(imei)) {
                        extraMap.put("IMEI", imei.trim());
                    }
                    if (StrUtil.isNotBlank(iccid1)) {
                        extraMap.put("ICCID1", iccid1.trim());
                    }
                    if (StrUtil.isNotBlank(iccid2)) {
                        extraMap.put("ICCID2", iccid2.trim());
                    }
                } else {
                    extraMap = JSONUtil.toBean(extra, new TypeReference<>() {
                    }, true);
                    if (StrUtil.isNotBlank(imei)) {
                        if (StrUtil.isBlank(extraMap.get("IMEI"))) {
                            extraMap.put("IMEI", imei.trim());
                        } else if (!imei.trim().equalsIgnoreCase(extraMap.get("IMEI"))) {
                            logger.warn("TBox导入数据批次号[{}]SN[{}]IMEI[{}]与原数据[{}]不一致", batchNum, sn, imei.trim(), extraMap.get("IMEI"));
                        }
                    }
                    if (StrUtil.isNotBlank(iccid1)) {
                        if (StrUtil.isBlank(extraMap.get("ICCID1"))) {
                            extraMap.put("ICCID1", iccid1.trim());
                        } else if (!iccid1.trim().equalsIgnoreCase(extraMap.get("ICCID1"))) {
                            logger.warn("TBox导入数据批次号[{}]SN[{}]ICCID1[{}]与原数据[{}]不一致", batchNum, sn, iccid1.trim(), extraMap.get("ICCID1"));
                        }
                    }
                    if (StrUtil.isNotBlank(iccid2)) {
                        if (StrUtil.isBlank(extraMap.get("ICCID2"))) {
                            extraMap.put("ICCID2", iccid2.trim());
                        } else if (!iccid2.trim().equalsIgnoreCase(extraMap.get("ICCID2"))) {
                            logger.warn("TBox导入数据批次号[{}]SN[{}]ICCID2[{}]与原数据[{}]不一致", batchNum, sn, iccid2.trim(), extraMap.get("ICCID2"));
                        }
                    }
                }
                tboxPo.setExtra(JSONUtil.toJsonStr(extraMap));
            }
            if (ObjUtil.isNull(tboxPo.getId())) {
                vehiclePartAppService.createPart(tboxPo);
            } else {
                vehiclePartAppService.modifyPart(tboxPo);
            }
        }
    }
}

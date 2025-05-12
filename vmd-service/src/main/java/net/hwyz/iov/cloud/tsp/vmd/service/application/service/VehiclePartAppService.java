package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.enums.EcuType;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.tsp.mno.api.contract.SimExService;
import net.hwyz.iov.cloud.tsp.mno.api.contract.enums.MnoType;
import net.hwyz.iov.cloud.tsp.mno.api.contract.request.BatchImportSimRequest;
import net.hwyz.iov.cloud.tsp.mno.api.feign.service.ExSimService;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleImportDataException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehPartDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPartPo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆零部件应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehiclePartAppService {

    private final VehPartDao vehPartDao;
    private final ExSimService expSimService;

    /**
     * 根据零部件类型及序列号获取零部件
     *
     * @param ecuType 零部件类型
     * @param sn      序列号
     * @return 零部件
     */
    public VehPartPo getPartBySn(EcuType ecuType, String sn) {
        return vehPartDao.selectPoBySn(ecuType.name(), sn);
    }

    /**
     * 根据零部件类型及车架号获取零部件
     *
     * @param ecuType 零部件类型
     * @param vin     车架号
     * @return 零部件
     */
    public VehPartPo getPartByVin(EcuType ecuType, String vin) {
        return vehPartDao.selectPoByVin(ecuType.name(), vin);
    }

    /**
     * 新增车辆零部件
     *
     * @param vehPartPo 车辆零部件
     * @return 结果
     */
    public int createPart(VehPartPo vehPartPo) {
        if (ObjUtil.isNull(vehPartPo.getSort())) {
            vehPartPo.setSort(99);
        }
        return vehPartDao.insertPo(vehPartPo);
    }

    /**
     * 修改车辆零部件
     *
     * @param vehPartPo 车辆零部件
     * @return 结果
     */
    public int modifyPart(VehPartPo vehPartPo) {
        return vehPartDao.updatePo(vehPartPo);
    }

    /**
     * 解析SIM卡导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson TBox导入数据
     */
    public void parseSimImportData(String batchNum, String version, JSONObject dataJson) {
        // SIM卡导入数据现在没有多版本，暂时不用关心version
        JSONObject request = dataJson.getJSONObject("REQUEST");
        JSONObject head = request.getJSONObject("HEAD");
        String supplier = null;
        if (ObjUtil.isNotNull(head)) {
            supplier = head.getStr("ACCOUNT");
            if (StrUtil.isBlank(supplier)) {
                logger.warn("SIM卡导入数据批次号[{}]供应商代码为空", batchNum);
            }
        } else {
            logger.warn("SIM卡导入数据批次号[{}]HEAD为空", batchNum);
        }
        JSONObject data = request.getJSONObject("DATA");
        String mno = data.getStr("MNO");
        if (StrUtil.isBlank(mno)) {
            throw new VehicleImportDataException(batchNum, "SIM卡导入数据运营商为空");
        }
        MnoType mnoType = MnoType.valOf(mno.toUpperCase());
        if (ObjUtil.isNull(mnoType)) {
            throw new VehicleImportDataException(batchNum, "SIM卡导入数据运营商[" + mno + "]未识别");
        }
        BatchImportSimRequest req = new BatchImportSimRequest();
        req.setMnoType(mnoType);
        List<SimExService> simList = new ArrayList<>();
        JSONArray items = data.getJSONArray("ITEMS");
        int simInvalidCount = 0;
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String iccid = itemJson.getStr("ICCID");
            String imsi = itemJson.getStr("IMSI");
            String msisdn = itemJson.getStr("MSISDN");
            if (StrUtil.isBlank(iccid) && StrUtil.isBlank(imsi) && StrUtil.isBlank(msisdn)) {
                simInvalidCount++;
                continue;
            }
            simList.add(SimExService.builder().iccid(iccid).imsi(imsi).msisdn(msisdn).build());
        }
        if (simInvalidCount > 0) {
            logger.warn("SIM卡导入数据批次号[{}]存在无效SIM卡数据[{}]", batchNum, simInvalidCount);
        }
        req.setSimList(simList);
        expSimService.batchImport(req);
    }

    /**
     * 解析TBox导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson TBox导入数据
     */
    public void parseTboxImportData(String batchNum, String version, JSONObject dataJson) {
        // TBox导入数据现在没有多版本，暂时不用关心version
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
            VehPartPo tboxPo = getPartBySn(EcuType.TBOX, sn);
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
                createPart(tboxPo);
            } else {
                modifyPart(tboxPo);
            }
        }
    }

    /**
     * 解析CCP导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson CCP导入数据
     */
    public void parseCcpImportData(String batchNum, String version, JSONObject dataJson) {
        // CCP导入数据现在没有多版本，暂时不用关心version
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
            VehPartPo ccpPo = getPartBySn(EcuType.CCP, sn);
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
                createPart(ccpPo);
            } else {
                modifyPart(ccpPo);
            }
        }
    }

    /**
     * 解析BTM导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson BTM导入数据
     */
    public void parseBtmImportData(String batchNum, String version, JSONObject dataJson) {
        // BTM导入数据现在没有多版本，暂时不用关心version
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
            VehPartPo btmPo = getPartBySn(EcuType.BTM, sn);
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
                createPart(btmPo);
            } else {
                modifyPart(btmPo);
            }
        }
    }

    /**
     * 解析IDCM导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson IDCM导入数据
     */
    public void parseIdcmImportData(String batchNum, String version, JSONObject dataJson) {
        // IDCM导入数据现在没有多版本，暂时不用关心version
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
            VehPartPo idcmPo = getPartBySn(EcuType.IDCM, sn);
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
                createPart(idcmPo);
            } else {
                modifyPart(idcmPo);
            }
        }
    }

}

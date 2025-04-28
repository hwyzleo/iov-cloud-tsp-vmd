package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.enums.EcuType;
import net.hwyz.iov.cloud.framework.common.util.DateUtil;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.tsp.vmd.service.application.event.publish.VehiclePublish;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.VehicleLifecycleNode;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehImportDataDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehLifecycleDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehImportDataPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehLifecyclePo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPartPo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleAppService {

    private final VehiclePublish vehiclePublish;
    private final VehBasicInfoDao vehBasicInfoDao;
    private final VehLifecycleDao vehLifecycleDao;
    private final VehImportDataDao vehImportDataDao;
    private final VehiclePartAppService vehiclePartAppService;

    /**
     * 查询车辆信息
     *
     * @param vin             车架号
     * @param modelConfigCode 车型配置代码
     * @param beginTime       开始时间
     * @param endTime         结束时间
     * @param isEol           是否下线
     * @param isOrder         是否有订单
     * @return 车辆平台列表
     */
    public List<VehBasicInfoPo> search(String vin, String modelConfigCode, Date beginTime, Date endTime, Boolean isEol, Boolean isOrder) {
        Map<String, Object> map = new HashMap<>();
        map.put("vin", ParamHelper.fuzzyQueryParam(vin));
        map.put("modelConfigCode", modelConfigCode);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        map.put("isEol", isEol);
        map.put("isOrder", isOrder);
        return vehBasicInfoDao.selectPoByMap(map);
    }

    /**
     * 查询车辆生命周期
     *
     * @param vin 车架号
     * @return 车辆生命周期列表
     */
    public List<VehLifecyclePo> listLifecycle(String vin) {
        return vehLifecycleDao.selectPoByExample(VehLifecyclePo.builder().vin(vin).build());
    }

    /**
     * 检查车架号是否唯一
     *
     * @param vehicleId 车辆ID
     * @param vin       车架号
     * @return 结果
     */
    public Boolean checkVinUnique(Long vehicleId, String vin) {
        if (ObjUtil.isNull(vehicleId)) {
            vehicleId = -1L;
        }
        VehBasicInfoPo vehBasicInfoPo = getVehicleByVin(vin);
        return !ObjUtil.isNotNull(vehBasicInfoPo) || vehBasicInfoPo.getId().longValue() == vehicleId.longValue();
    }

    /**
     * 根据主键ID获取车辆信息
     *
     * @param id 主键ID
     * @return 车辆信息
     */
    public VehBasicInfoPo getVehicleById(Long id) {
        return vehBasicInfoDao.selectPoById(id);
    }

    /**
     * 根据车架号获取车辆信息
     *
     * @param vin 车架号
     * @return 车辆信息
     */
    public VehBasicInfoPo getVehicleByVin(String vin) {
        return vehBasicInfoDao.selectPoByVin(vin);
    }

    /**
     * 新增车辆
     *
     * @param vehBasicInfo 车辆信息
     * @return 结果
     */
    public int createVehicle(VehBasicInfoPo vehBasicInfo) {
        return vehBasicInfoDao.insertPo(vehBasicInfo);
    }

    /**
     * 新增车辆生命周期
     *
     * @param vehLifecyclePo 车辆生命周期
     * @return 结果
     */
    public int createVehicleLifecycle(VehLifecyclePo vehLifecyclePo) {
        return vehLifecycleDao.insertPo(vehLifecyclePo);
    }

    /**
     * 修改车辆
     *
     * @param vehBasicInfo 车辆信息
     * @return 结果
     */
    public int modifyVehicle(VehBasicInfoPo vehBasicInfo) {
        return vehBasicInfoDao.updatePo(vehBasicInfo);
    }

    /**
     * 修改车辆生命周期
     *
     * @param vehLifecyclePo 车辆生命周期
     * @return 结果
     */
    public int modifyVehicleLifecycle(VehLifecyclePo vehLifecyclePo) {
        return vehLifecycleDao.updatePo(vehLifecyclePo);
    }

    /**
     * 批量删除车辆
     *
     * @param ids 车辆ID数组
     * @return 结果
     */
    public int deleteVehicleByIds(Long[] ids) {
        for (Long id : ids) {
            VehBasicInfoPo vehiclePo = getVehicleById(id);
            if (ObjUtil.isNotNull(vehiclePo)) {
                vehLifecycleDao.batchPhysicalDeletePoByVin(vehiclePo.getVin());
            }
        }
        return vehBasicInfoDao.batchPhysicalDeletePo(ids);
    }

    /**
     * 批量删除车辆生命周期
     *
     * @param ids 车辆生命周期ID数组
     * @return 结果
     */
    public int deleteVehicleLifecycleByIds(Long[] ids) {
        return vehLifecycleDao.batchPhysicalDeletePo(ids);
    }

    /**
     * 解析车辆导入数据
     *
     * @param batchNum 批次号
     */
    @Async
    public void parseVehicleImportData(String batchNum) {
        VehImportDataPo vehImportDataPo = vehImportDataDao.selectPoByBatchNum(batchNum);
        if (ObjUtil.isNull(vehImportDataPo)) {
            logger.warn("车辆导入数据批次号[{}]不存在", batchNum);
            return;
        }
        if (vehImportDataPo.getHandle()) {
            logger.warn("车辆导入数据批次号[{}]已处理", batchNum);
            return;
        }
        String dataStr = vehImportDataPo.getData();
        if (StrUtil.isBlank(dataStr)) {
            logger.warn("车辆导入数据批次号[{}]数据为空", batchNum);
            return;
        }
        String type = vehImportDataPo.getType();
        if (StrUtil.isBlank(type)) {
            logger.warn("车辆导入数据批次号[{}]数据类型为空", batchNum);
            return;
        }
        String version = vehImportDataPo.getVersion();
        if (StrUtil.isBlank(version)) {
            logger.warn("车辆导入数据批次号[{}]版本为空", batchNum);
            return;
        }
        JSONObject dataJson = JSONUtil.parseObj(dataStr);
        vehImportDataPo.setHandle(true);
        vehImportDataPo.setDescription("");
        try {
            switch (type.toUpperCase()) {
                case "TBOX" -> parseTboxImportData(batchNum, version, dataJson);
                case "CCP" -> parseCcpImportData(batchNum, version, dataJson);
                case "BTM" -> parseBtmImportData(batchNum, version, dataJson);
                case "PRODUCE" -> parseVehicleProduceImportData(batchNum, version, dataJson);
                case "EOL" -> parseVehicleEolImportData(batchNum, version, dataJson);
                default -> {
                    logger.warn("车辆导入数据批次号[{}]类型[{}]暂未处理", batchNum, vehImportDataPo.getType());
                    vehImportDataPo.setHandle(false);
                    vehImportDataPo.setDescription("未知类型：" + vehImportDataPo.getType());
                }
            }
        } catch (Exception e) {
            logger.error("车辆导入数据批次号[{}]处理失败", batchNum, e);
            vehImportDataPo.setHandle(false);
            vehImportDataPo.setDescription("车辆导入数据批次号[" + batchNum + "]处理失败:" + e.getMessage());
        }
        vehImportDataDao.updatePo(vehImportDataPo);
    }

    /**
     * 解析TBox导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson TBox导入数据
     */
    private void parseTboxImportData(String batchNum, String version, JSONObject dataJson) {
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

    /**
     * 解析CCP导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson CCP导入数据
     */
    private void parseCcpImportData(String batchNum, String version, JSONObject dataJson) {
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

    /**
     * 解析BTM导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson BTM导入数据
     */
    private void parseBtmImportData(String batchNum, String version, JSONObject dataJson) {
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

    /**
     * 解析车辆生产导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson 车辆生产导入数据
     */
    private void parseVehicleProduceImportData(String batchNum, String version, JSONObject dataJson) {
        // 生产数据现在没有多版本，暂时不用关心
        JSONObject request = dataJson.getJSONObject("REQUEST");
        JSONObject data = request.getJSONObject("DATA");
        JSONArray items = data.getJSONArray("ITEMS");
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String vin = itemJson.getStr("VIN");
            if (StrUtil.isBlank(vin)) {
                logger.warn("车辆导入数据批次号[{}]车架号为空", batchNum);
                continue;
            }
            VehBasicInfoPo vehBasicInfoPo = getVehicleByVin(vin);
            if (ObjUtil.isNull(vehBasicInfoPo)) {
                vehBasicInfoPo = new VehBasicInfoPo();
                vehBasicInfoPo.setVin(vin);
            }
            String manufacturer = itemJson.getStr("MANUFACTURER");
            if (StrUtil.isNotBlank(manufacturer)) {
                if (StrUtil.isBlank(vehBasicInfoPo.getManufacturerCode())) {
                    vehBasicInfoPo.setManufacturerCode(manufacturer.trim().toUpperCase());
                } else if (!manufacturer.trim().equalsIgnoreCase(vehBasicInfoPo.getManufacturerCode())) {
                    logger.warn("车辆导入数据批次号[{}]车辆[{}]工厂数据[{}]与原数据[{}]不一致", batchNum, vin, manufacturer.trim(), vehBasicInfoPo.getManufacturerCode());
                }
            } else {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]工厂为空", batchNum, vin);
            }
            String brand = itemJson.getStr("BRAND");
            if (StrUtil.isNotBlank(brand)) {
                if (StrUtil.isBlank(vehBasicInfoPo.getBrandCode())) {
                    vehBasicInfoPo.setBrandCode(brand.trim().toUpperCase());
                } else if (!brand.trim().equalsIgnoreCase(vehBasicInfoPo.getBrandCode())) {
                    logger.warn("车辆导入数据批次号[{}]车辆[{}]品牌数据[{}]与原数据[{}]不一致", batchNum, vin, brand.trim(), vehBasicInfoPo.getBrandCode());
                }
            } else {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]品牌为空", batchNum, vin);
            }
            String platform = itemJson.getStr("PLATFORM");
            if (StrUtil.isNotBlank(platform)) {
                if (StrUtil.isBlank(vehBasicInfoPo.getPlatformCode())) {
                    vehBasicInfoPo.setPlatformCode(platform.trim().toUpperCase());
                } else if (!platform.trim().equalsIgnoreCase(vehBasicInfoPo.getPlatformCode())) {
                    logger.warn("车辆导入数据批次号[{}]车辆[{}]平台数据[{}]与原数据[{}]不一致", batchNum, vin, platform.trim(), vehBasicInfoPo.getPlatformCode());
                }
            } else {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]平台为空", batchNum, vin);
            }
            String series = itemJson.getStr("SERIES");
            if (StrUtil.isNotBlank(series)) {
                if (StrUtil.isBlank(vehBasicInfoPo.getSeriesCode())) {
                    vehBasicInfoPo.setSeriesCode(series.trim().toUpperCase());
                } else if (!series.trim().equalsIgnoreCase(vehBasicInfoPo.getSeriesCode())) {
                    logger.warn("车辆导入数据批次号[{}]车辆[{}]车系数据[{}]与原数据[{}]不一致", batchNum, vin, series.trim(), vehBasicInfoPo.getSeriesCode());
                }
            } else {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]车系为空", batchNum, vin);
            }
            String model = itemJson.getStr("MODEL");
            if (StrUtil.isNotBlank(model)) {
                if (StrUtil.isBlank(vehBasicInfoPo.getModelCode())) {
                    vehBasicInfoPo.setModelCode(model.trim().toUpperCase());
                } else if (!model.trim().equalsIgnoreCase(vehBasicInfoPo.getModelCode())) {
                    logger.warn("车辆导入数据批次号[{}]车辆[{}]车型数据[{}]与原数据[{}]不一致", batchNum, vin, model.trim(), vehBasicInfoPo.getModelCode());
                }
            } else {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]车型为空", batchNum, vin);
            }
            String basicModel = itemJson.getStr("BASIC_MODEL");
            if (StrUtil.isNotBlank(basicModel)) {
                if (StrUtil.isBlank(vehBasicInfoPo.getBasicModelCode())) {
                    vehBasicInfoPo.setBasicModelCode(basicModel.trim().toUpperCase());
                } else if (!basicModel.trim().equalsIgnoreCase(vehBasicInfoPo.getBasicModelCode())) {
                    logger.warn("车辆导入数据批次号[{}]车辆[{}]基础车型数据[{}]与原数据[{}]不一致", batchNum, vin, basicModel.trim(), vehBasicInfoPo.getBasicModelCode());
                }
            } else {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]基础车型为空", batchNum, vin);
            }
            String modelConfig = itemJson.getStr("MODEL_CONFIG");
            if (StrUtil.isNotBlank(modelConfig)) {
                if (StrUtil.isBlank(vehBasicInfoPo.getModelConfigCode())) {
                    vehBasicInfoPo.setModelConfigCode(modelConfig.trim().toUpperCase());
                } else if (!modelConfig.trim().equalsIgnoreCase(vehBasicInfoPo.getModelConfigCode())) {
                    logger.warn("车辆导入数据批次号[{}]车辆[{}]车型配置数据[{}]与原数据[{}]不一致", batchNum, vin, modelConfig.trim(), vehBasicInfoPo.getModelConfigCode());
                }
            } else {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]车型配置为空", batchNum, vin);
            }
            if (ObjUtil.isNull(vehBasicInfoPo.getId())) {
                vehBasicInfoDao.insertPo(vehBasicInfoPo);
            } else {
                vehBasicInfoDao.updatePo(vehBasicInfoPo);
            }
            // 发布事件
            vehiclePublish.produce(vin);
        }
    }

    /**
     * 解析车辆下线导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson 车辆导入数据
     */
    private void parseVehicleEolImportData(String batchNum, String version, JSONObject dataJson) {
        // 下线数据现在没有多版本，暂时不用关心
        String vin = dataJson.getByPath("$.REQUEST.DATA.ITEM.VIN", String[].class)[0];
        if (StrUtil.isBlank(vin)) {
            logger.warn("车辆导入数据批次号[{}]车架号为空", batchNum);
            return;
        }
        VehBasicInfoPo vehBasicInfoPo = getVehicleByVin(vin);
        if (ObjUtil.isNull(vehBasicInfoPo)) {
            vehBasicInfoPo = new VehBasicInfoPo();
            vehBasicInfoPo.setVin(vin);
        }
        String manufacturer = dataJson.getByPath("$.REQUEST.DATA.ITEM.MANUFACTURER", String[].class)[0];
        if (StrUtil.isNotBlank(manufacturer)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getManufacturerCode())) {
                vehBasicInfoPo.setManufacturerCode(manufacturer.trim().toUpperCase());
            } else if (!manufacturer.trim().equalsIgnoreCase(vehBasicInfoPo.getManufacturerCode())) {
                logger.warn("车辆导入数据批次号[{}]工厂数据[{}]与原数据[{}]不一致", batchNum, manufacturer.trim(), vehBasicInfoPo.getManufacturerCode());
            }
        } else {
            logger.warn("车辆导入数据批次号[{}]工厂为空", batchNum);
        }
        String brand = dataJson.getByPath("$.REQUEST.DATA.ITEM.BRAND", String[].class)[0];
        if (StrUtil.isNotBlank(brand)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getBrandCode())) {
                vehBasicInfoPo.setBrandCode(brand.trim().toUpperCase());
            } else if (!brand.trim().equalsIgnoreCase(vehBasicInfoPo.getBrandCode())) {
                logger.warn("车辆导入数据批次号[{}]品牌数据[{}]与原数据[{}]不一致", batchNum, brand.trim(), vehBasicInfoPo.getBrandCode());
            }
        } else {
            logger.warn("车辆导入数据批次号[{}]品牌为空", batchNum);
        }
        String platform = dataJson.getByPath("$.REQUEST.DATA.ITEM.PLATFORM", String[].class)[0];
        if (StrUtil.isNotBlank(platform)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getPlatformCode())) {
                vehBasicInfoPo.setPlatformCode(platform.trim().toUpperCase());
            } else if (!platform.trim().equalsIgnoreCase(vehBasicInfoPo.getPlatformCode())) {
                logger.warn("车辆导入数据批次号[{}]平台数据[{}]与原数据[{}]不一致", batchNum, platform.trim(), vehBasicInfoPo.getPlatformCode());
            }
        } else {
            logger.warn("车辆导入数据批次号[{}]平台为空", batchNum);
        }
        String series = dataJson.getByPath("$.REQUEST.DATA.ITEM.SERIES", String[].class)[0];
        if (StrUtil.isNotBlank(series)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getSeriesCode())) {
                vehBasicInfoPo.setSeriesCode(series.trim().toUpperCase());
            } else if (!series.trim().equalsIgnoreCase(vehBasicInfoPo.getSeriesCode())) {
                logger.warn("车辆导入数据批次号[{}]车系数据[{}]与原数据[{}]不一致", batchNum, series.trim(), vehBasicInfoPo.getSeriesCode());
            }
        } else {
            logger.warn("车辆导入数据批次号[{}]车系为空", batchNum);
        }
        String model = dataJson.getByPath("$.REQUEST.DATA.ITEM.MODEL", String[].class)[0];
        if (StrUtil.isNotBlank(model)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getModelCode())) {
                vehBasicInfoPo.setModelCode(model.trim().toUpperCase());
            } else if (!model.trim().equalsIgnoreCase(vehBasicInfoPo.getModelCode())) {
                logger.warn("车辆导入数据批次号[{}]车型数据[{}]与原数据[{}]不一致", batchNum, model.trim(), vehBasicInfoPo.getModelCode());
            }
        } else {
            logger.warn("车辆导入数据批次号[{}]车型为空", batchNum);
        }
        String basicModel = dataJson.getByPath("$.REQUEST.DATA.ITEM.BASIC_MODEL", String[].class)[0];
        if (StrUtil.isNotBlank(basicModel)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getBasicModelCode())) {
                vehBasicInfoPo.setBasicModelCode(basicModel.trim().toUpperCase());
            } else if (!basicModel.trim().equalsIgnoreCase(vehBasicInfoPo.getBasicModelCode())) {
                logger.warn("车辆导入数据批次号[{}]基础车型数据[{}]与原数据[{}]不一致", batchNum, basicModel.trim(), vehBasicInfoPo.getBasicModelCode());
            }
        } else {
            logger.warn("车辆导入数据批次号[{}]基础车型为空", batchNum);
        }
        String modelConfig = dataJson.getByPath("$.REQUEST.DATA.ITEM.MODEL_CONFIG", String[].class)[0];
        if (StrUtil.isNotBlank(modelConfig)) {
            if (StrUtil.isBlank(vehBasicInfoPo.getModelConfigCode())) {
                vehBasicInfoPo.setModelConfigCode(modelConfig.trim().toUpperCase());
            } else if (!modelConfig.trim().equalsIgnoreCase(vehBasicInfoPo.getModelConfigCode())) {
                logger.warn("车辆导入数据批次号[{}]车型配置数据[{}]与原数据[{}]不一致", batchNum, modelConfig.trim(), vehBasicInfoPo.getModelConfigCode());
            }
        } else {
            logger.warn("车辆导入数据批次号[{}]车型配置为空", batchNum);
        }
        String eolDateStr = dataJson.getByPath("$.REQUEST.DATA.ITEM.EOL_DATE", String[].class)[0];
        if (StrUtil.isNotBlank(eolDateStr)) {
            DateTime eolDate = DateUtil.parse(eolDateStr, "yyyyMMdd");
            if (ObjUtil.isNull(vehBasicInfoPo.getEolTime())) {
                vehBasicInfoPo.setEolTime(eolDate);
                // 同时产生生命周期节点
                VehLifecyclePo vehLifecyclePo = new VehLifecyclePo();
                vehLifecyclePo.setVin(vin);
                vehLifecyclePo.setNode(VehicleLifecycleNode.EOL.name());
                vehLifecyclePo.setReachTime(eolDate);
                vehLifecyclePo.setSort(99);
                vehLifecycleDao.insertPo(vehLifecyclePo);
            } else if (eolDate.isBefore(vehBasicInfoPo.getEolTime()) || eolDate.isAfter(vehBasicInfoPo.getEolTime())) {
                logger.warn("车辆导入数据批次号[{}]下线日期数据[{}]与原数据[{}]不一致", batchNum, eolDateStr, DateUtil.formatDate(vehBasicInfoPo.getEolTime()));
            } else {
                logger.warn("车辆导入数据批次号[{}]下线日期为空", batchNum);
            }
        }
        if (ObjUtil.isNull(vehBasicInfoPo.getId())) {
            vehBasicInfoDao.insertPo(vehBasicInfoPo);
        } else {
            vehBasicInfoDao.updatePo(vehBasicInfoPo);
        }
    }
}
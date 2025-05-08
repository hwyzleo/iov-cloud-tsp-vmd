package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.bean.BeanUtil;
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
import net.hwyz.iov.cloud.tsp.account.api.contract.Account;
import net.hwyz.iov.cloud.tsp.account.api.feign.service.ExAccountService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.event.publish.VehiclePublish;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.VehicleLifecycleNode;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.repository.VehicleRepository;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleHasBindOrderException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehiclePresetOwnerNotMatchException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.exception.VehicleWithoutPresetOwnerException;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehBasicInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehDetailInfoDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehImportDataDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehPresetOwnerDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.*;
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
    private final VehImportDataDao vehImportDataDao;
    private final VehDetailInfoDao vehDetailInfoDao;
    private final ExAccountService exAccountService;
    private final VehicleRepository vehicleRepository;
    private final VehPresetOwnerDao vehPresetOwnerDao;
    private final VehiclePartAppService vehiclePartAppService;
    private final VehicleLifecycleAppService vehicleLifecycleAppService;

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
     * 根据车架号获取车辆详细信息
     *
     * @param vin 车架号
     * @return 车辆详细信息
     */
    public VehDetailInfoPo getVehicleDetailByVin(String vin) {
        return vehDetailInfoDao.selectPoByVin(vin);
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
     * 修改车辆
     *
     * @param vehBasicInfo 车辆信息
     * @return 结果
     */
    public int modifyVehicle(VehBasicInfoPo vehBasicInfo) {
        return vehBasicInfoDao.updatePo(vehBasicInfo);
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
                vehicleLifecycleAppService.deleteVehicleLifecycleByVin(vehiclePo.getVin());
            }
        }
        return vehBasicInfoDao.batchPhysicalDeletePo(ids);
    }

    /**
     * 绑定订单
     *
     * @param vin      车架号
     * @param orderNum 订单编号
     */
    public void bindOrder(String vin, String orderNum) {
        logger.info("车辆[{}]绑定订单[{}]", vin, orderNum);
        VehicleDo vehicleDo = vehicleRepository.getByVin(vin);
        if (vehicleDo.hasOrder()) {
            throw new VehicleHasBindOrderException(vin, vehicleDo.getOrderNum());
        }
        vehicleDo.bindOrder(orderNum);
        vehicleRepository.save(vehicleDo);
        vehicleLifecycleAppService.recordBindOrderNode(vin, orderNum);
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
                case "IDCM" -> parseIdcmImportData(batchNum, version, dataJson);
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
     * 解析IDCM导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson IDCM导入数据
     */
    private void parseIdcmImportData(String batchNum, String version, JSONObject dataJson) {
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
            handleVehicleInfo(itemJson, vehBasicInfoPo, "MANUFACTURER", "manufacturerCode", "工厂数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "BRAND", "brandCode", "品牌数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "PLATFORM", "platformCode", "平台数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "SERIES", "seriesCode", "车系数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "MODEL", "modelCode", "车型数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "BASIC_MODEL", "basicModelCode", "基础车型数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "MODEL_CONFIG", "modelConfigCode", "车型配置数据", batchNum, vin);
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
            VehDetailInfoPo vehDetailInfoPo = getVehicleDetailByVin(vin);
            if (ObjUtil.isNull(vehBasicInfoPo)) {
                vehBasicInfoPo = new VehBasicInfoPo();
                vehBasicInfoPo.setVin(vin);
            }
            if (ObjUtil.isNull(vehDetailInfoPo)) {
                vehDetailInfoPo = new VehDetailInfoPo();
                vehDetailInfoPo.setVin(vin);
            }
            handleVehicleInfo(itemJson, vehBasicInfoPo, "MANUFACTURER", "manufacturerCode", "工厂数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "BRAND", "brandCode", "品牌数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "PLATFORM", "platformCode", "平台数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "SERIES", "seriesCode", "车系数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "MODEL", "modelCode", "车型数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "BASIC_MODEL", "basicModelCode", "基础车型数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "MODEL_CONFIG", "modelConfigCode", "车型配置数据", batchNum, vin);
            handleVehicleInfo(itemJson, vehBasicInfoPo, "VEHICLE_BASE_VERSION", "vehicleBaseVersion", "车辆基线版本", batchNum, vin);
            String eolDateStr = itemJson.getStr("EOL_DATE");
            DateTime eolDate;
            boolean firstEol = false;
            if (StrUtil.isNotBlank(eolDateStr)) {
                eolDate = DateUtil.parse(eolDateStr, "yyyyMMdd");
            } else {
                eolDate = new DateTime();
            }
            if (ObjUtil.isNull(vehBasicInfoPo.getEolTime())) {
                vehBasicInfoPo.setEolTime(eolDate);
                firstEol = true;
            } else if (eolDate.isBefore(vehBasicInfoPo.getEolTime()) || eolDate.isAfter(vehBasicInfoPo.getEolTime())) {
                logger.warn("车辆导入数据批次号[{}]下线日期数据[{}]与原数据[{}]不一致", batchNum, eolDateStr, DateUtil.formatDate(vehBasicInfoPo.getEolTime()));
            }
            handleVehicleInfo(itemJson, vehDetailInfoPo, "PRODUCTION_ORDER", "productionOrder", "生产订单", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "MATNR", "matnr", "整车物料编码", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "PROJECT", "project", "车型项目", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "SALES_AREA", "salesArea", "销售区域", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "BODY_FORM", "bodyForm", "车身形式", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "CONFIG_LEVEL", "configLevel", "配置等级", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "MODEL_YEAR", "modelYear", "车型年份", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "STEERING_WHEEL_POSITION", "steeringWheelPosition", "方向盘位置", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "INTERIOR_TYPE", "interiorType", "内饰风格", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "EXTERIOR_COLOR", "exteriorColor", "外饰颜色", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "DRIVE_FORM", "driveForm", "驾驶形式", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "WHEEL", "wheel", "车轮", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "WHEEL_COLOR", "wheelColor", "车轮颜色", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "SEAT_TYPE", "seatType", "座椅类型", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "ASSISTED_DRIVING", "assistedDriving", "辅助驾驶", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "ETC_SYSTEM", "etcSystem", "ETC系统", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "REAR_TOW_BAR", "rearTowBar", "后牵引杆", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "ENGINE_NO", "engineNo", "发动机编码", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "ENGINE_TYPE", "engineType", "发动机类型", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "FRONT_DRIVE_MOTOR_NO", "frontDriveMotorNo", "前驱电机编码", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "FRONT_DRIVE_MOTOR_TYPE", "frontDriveMotorType", "前驱电机类型", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "REAR_DRIVE_MOTOR_NO", "rearDriveMotorNo", "后驱电机编码", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "REAR_DRIVE_MOTOR_TYPE", "rearDriveMotorType", "后驱电机类型", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "GENERATOR_NO", "generatorNo", "发电机编码", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "GENERATOR_TYPE", "generatorType", "发电机类型", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "POWER_BATTERY_PACK_NO", "powerBatteryPackNo", "动力电池包编码", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "POWER_BATTERY_TYPE", "powerBatteryType", "动力电池类型", batchNum, vin);
            handleVehicleInfo(itemJson, vehDetailInfoPo, "POWER_BATTERY_FACTORY", "powerBatteryFactory", "动力电池厂商", batchNum, vin);
            if (ObjUtil.isNull(vehBasicInfoPo.getId())) {
                vehBasicInfoDao.insertPo(vehBasicInfoPo);
                // 如果车辆是新生成，则补发车辆生产事件
                vehiclePublish.produce(vin);
            } else {
                vehBasicInfoDao.updatePo(vehBasicInfoPo);
            }
            if (firstEol) {
                vehiclePublish.eol(vin, eolDate);
            }
            String certDateStr = itemJson.getStr("CERT_DATE");
            if (StrUtil.isNotBlank(certDateStr) && ObjUtil.isNull(vehicleLifecycleAppService.getLifecycle(vin, VehicleLifecycleNode.CERTIFICATE))) {
                DateTime certDate = DateUtil.parse(certDateStr, "yyyyMMdd");
                if (ObjUtil.isNotNull(certDate)) {
                    vehicleLifecycleAppService.recordCertificateNode(vin, certDate);
                }
            }
            JSONArray parts = itemJson.getJSONArray("PARTS");
            for (Object part : parts) {
                JSONObject partJson = JSONUtil.parseObj(part);
                String ecuType = partJson.getStr("ECU_TYPE");
                if (StrUtil.isBlank(ecuType)) {
                    logger.warn("车辆导入数据批次号[{}]车架号[{}]零部件类型为空", batchNum, vin);
                    continue;
                }
                String partVin = partJson.getStr("VIN");
                if (!vin.equalsIgnoreCase(partVin)) {
                    logger.warn("车辆导入数据批次号[{}]车架号[{}]零部件[{}]车架号[{}]不一致", batchNum, vin, ecuType, partVin);
                    continue;
                }
                String sn = partJson.getStr("PART_NO");
                VehPartPo partPo;
                EcuType ecuTypeEnum = EcuType.valOf(ecuType);
                if (ObjUtil.isNull(ecuTypeEnum)) {
                    logger.warn("车辆导入数据批次号[{}]车架号[{}]零部件类型[{}]异常", batchNum, vin, ecuType);
                }
                if (StrUtil.isNotBlank(sn)) {
                    partPo = vehiclePartAppService.getPartBySn(ecuTypeEnum, sn);
                } else {
                    partPo = vehiclePartAppService.getPartByVin(ecuTypeEnum, vin);
                }
                if (ObjUtil.isNull(partPo)) {
                    partPo = new VehPartPo();
                    partPo.setEcu(ecuType);
                    partPo.setSn(sn);
                }
                handlePartInfo(partJson, partPo, "HARDWARE_VERSION", "hardwareVer", "硬件版本号", batchNum, vin, ecuType);
                handlePartInfo(partJson, partPo, "SOFTWARE_VERSION", "softwareVer", "软件版本号", batchNum, vin, ecuType);
                handlePartInfo(partJson, partPo, "HARDWARE_NO", "hardwareNo", "硬件零件号", batchNum, vin, ecuType);
                handlePartInfo(partJson, partPo, "SOFTWARE_NO", "softwareNo", "软件零件号", batchNum, vin, ecuType);
                handlePartInfo(partJson, partPo, "SUPPLIER_CODE", "supplierCode", "供应商编码", batchNum, vin, ecuType);
                handlePartInfoOptional(partJson, partPo, "CONFIG_WORD", "ecuConfigWord", "ECU配置字", batchNum, vin, ecuType);
                handlePartInfoOptional(partJson, partPo, "PART_SN", "no", "零件号", batchNum, vin, ecuType);
                handlePartInfoOptional(partJson, partPo, "SECURITY_CHIP_NO", "hsm", "硬件安全模块", batchNum, vin, ecuType);
                handlePartInfoOptional(partJson, partPo, "MAC_ADDRESS", "mac", "MAC地址", batchNum, vin, ecuType);
                if (EcuType.TBOX.name().equalsIgnoreCase(ecuType)) {
                    String extra = partPo.getExtra();
                    if (StrUtil.isNotBlank(extra)) {
                        JSONObject extraJson = JSONUtil.parseObj(extra);
                        String iccid1 = itemJson.getStr("ICCID1");
                        if (StrUtil.isNotBlank(iccid1)) {
                            if (StrUtil.isBlank(extraJson.getStr("iccid1"))) {
                                extraJson.set("iccid1", iccid1);
                            } else if (!iccid1.trim().equalsIgnoreCase(extraJson.getStr("iccid1"))) {
                                logger.warn("车辆导入数据批次号[{}]车辆[{}]零部件[TBOX]ICCID1[{}]与原数据[{}]不一致", batchNum,
                                        vin, iccid1.trim(), extraJson.getStr("iccid1"));
                            }
                        } else {
                            logger.warn("车辆导入数据批次号[{}]车辆[{}]零部件[TBOX]ICCID1为空", batchNum, vin);
                        }
                        String iccid2 = itemJson.getStr("ICCID2");
                        if (StrUtil.isNotBlank(iccid2)) {
                            if (StrUtil.isBlank(extraJson.getStr("iccid2"))) {
                                extraJson.set("iccid2", iccid2);
                            } else if (!iccid2.trim().equalsIgnoreCase(extraJson.getStr("iccid2"))) {
                                logger.warn("车辆导入数据批次号[{}]车辆[{}]零部件[TBOX]ICCID2[{}]与原数据[{}]不一致", batchNum,
                                        vin, iccid2.trim(), extraJson.getStr("iccid2"));
                            }
                        } else {
                            logger.warn("车辆导入数据批次号[{}]车辆[{}]零部件[TBOX]ICCID2为空", batchNum, vin);
                        }
                        String imei = itemJson.getStr("IMEI");
                        if (StrUtil.isNotBlank(imei)) {
                            if (StrUtil.isBlank(extraJson.getStr("imei"))) {
                                extraJson.set("imei", imei);
                            } else if (!imei.trim().equalsIgnoreCase(extraJson.getStr("imei"))) {
                                logger.warn("车辆导入数据批次号[{}]车辆[{}]零部件[TBOX]ICCID1[{}]与原数据[{}]不一致", batchNum,
                                        vin, imei.trim(), extraJson.getStr("imei"));
                            }
                        } else {
                            logger.warn("车辆导入数据批次号[{}]车辆[{}]零部件[TBOX]IMEI为空", batchNum, vin);
                        }
                    }
                }
            }
        }
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
    private void handleVehicleInfo(JSONObject itemJson, Object vehicleInfoPo, String jsonKey, String propertyName,
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

    /**
     * 处理零部件信息数据
     *
     * @param partJson     零部件JSON数据
     * @param partPo       零部件对象
     * @param jsonKey      解析JSON KEY
     * @param propertyName 对象属性名
     * @param keyDesc      KEY描述
     * @param batchNum     批次号
     * @param vin          车架号
     * @param ecuType      零部件
     */
    private void handlePartInfo(JSONObject partJson, VehPartPo partPo, String jsonKey, String propertyName, String keyDesc,
                                String batchNum, String vin, String ecuType) {
        String keyValue = partJson.getStr(jsonKey);
        if (StrUtil.isNotBlank(keyValue)) {
            Object fieldValue = BeanUtil.getFieldValue(partPo, propertyName);
            if (ObjUtil.isNull(fieldValue) || StrUtil.isBlank(fieldValue.toString())) {
                BeanUtil.setFieldValue(partPo, propertyName, keyValue.trim().toUpperCase());
            } else if (!keyValue.trim().equalsIgnoreCase(fieldValue.toString())) {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]零部件[{}]{}[{}]与原数据[{}]不一致", batchNum, vin, ecuType, keyDesc,
                        keyValue.trim(), fieldValue);
            }
        } else {
            logger.warn("车辆导入数据批次号[{}]车辆[{}]零部件[{}]{}为空", batchNum, vin, ecuType, keyDesc);
        }
    }

    /**
     * 处理零部件信息数据（可选）
     *
     * @param partJson     零部件JSON数据
     * @param partPo       零部件对象
     * @param jsonKey      解析JSON KEY
     * @param propertyName 对象属性名
     * @param keyDesc      KEY描述
     * @param batchNum     批次号
     * @param vin          车架号
     * @param ecuType      零部件
     */
    private void handlePartInfoOptional(JSONObject partJson, VehPartPo partPo, String jsonKey, String propertyName, String keyDesc,
                                        String batchNum, String vin, String ecuType) {
        String keyValue = partJson.getStr(jsonKey);
        if (StrUtil.isNotBlank(keyValue)) {
            Object fieldValue = BeanUtil.getFieldValue(partPo, propertyName);
            if (ObjUtil.isNull(fieldValue) || StrUtil.isBlank(fieldValue.toString())) {
                BeanUtil.setFieldValue(partPo, propertyName, keyValue.trim().toUpperCase());
            } else if (!keyValue.trim().equalsIgnoreCase(fieldValue.toString())) {
                logger.warn("车辆导入数据批次号[{}]车辆[{}]零部件[{}]{}[{}]与原数据[{}]不一致", batchNum, vin, ecuType, keyDesc,
                        keyValue.trim(), fieldValue);
            }
        }
    }

    /**
     * 检查车辆预设车主
     *
     * @param vin       车架号
     * @param accountId 账号ID
     */
    public void checkVehiclePresetOwner(String vin, String accountId) {
        List<VehPresetOwnerPo> vehPresetOwnerPoList = vehPresetOwnerDao.selectPoByExample(VehPresetOwnerPo.builder().vin(vin).build());
        if (vehPresetOwnerPoList.isEmpty()) {
            throw new VehicleWithoutPresetOwnerException(vin);
        }
        VehPresetOwnerPo vehPresetOwnerPo = vehPresetOwnerPoList.get(0);
        Account account = exAccountService.getAccountInfo(accountId);
        if (!vehPresetOwnerPo.getMobile().equals(account.getMobile()) ||
                !vehPresetOwnerPo.getCountryRegionCode().equals(account.getCountryRegionCode())) {
            throw new VehiclePresetOwnerNotMatchException(vin, account.getCountryRegionCode(), account.getMobile(),
                    vehPresetOwnerPo.getCountryRegionCode(), vehPresetOwnerPo.getMobile());
        }
    }
}
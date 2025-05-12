package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
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
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehPresetOwnerDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehDetailInfoPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPartPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPresetOwnerPo;
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
     * 解析车辆生产导入数据
     *
     * @param batchNum 批次号
     * @param version  版本
     * @param dataJson 车辆生产导入数据
     */
    public void parseVehicleProduceImportData(String batchNum, String version, JSONObject dataJson) {
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
    public void parseVehicleEolImportData(String batchNum, String version, JSONObject dataJson) {
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
                    partPo.setVin(vin);
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
                if (ObjUtil.isNull(partPo.getId())) {
                    vehiclePartAppService.createPart(partPo);
                } else {
                    vehiclePartAppService.modifyPart(partPo);
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
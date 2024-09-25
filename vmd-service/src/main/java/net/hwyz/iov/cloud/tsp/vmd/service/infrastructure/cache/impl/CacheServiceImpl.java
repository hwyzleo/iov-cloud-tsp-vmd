package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.cache.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.enums.QrcodeState;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.enums.QrcodeType;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.VehicleLifecycleNode;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.qrcode.model.QrcodeDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleDo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.vehicle.model.VehicleLifecycleNodeDo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.cache.CacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Redis Key前缀：二维码
     */
    private static final String REDIS_KEY_PREFIX_QRCODE = "vmd:qrcode:";
    /**
     * Redis Key前缀：车辆
     */
    private static final String REDIS_KEY_PREFIX_VEHICLE = "vmd:vehicle:";

    @Override
    public Optional<VehicleDo> getVehicle(String vin) {
        String vehicleDoJson = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX_VEHICLE + vin);
        if (StrUtil.isBlank(vehicleDoJson)) {
            return Optional.empty();
        }
        JSONObject jsonObject = JSONUtil.parseObj(vehicleDoJson);
        List<VehicleLifecycleNodeDo> allNodeList = new ArrayList<>();
        for (Object o : jsonObject.getJSONArray("allNodeList")) {
            JSONObject node = JSONUtil.parseObj(o, true);
            VehicleLifecycleNodeDo nodeDo = VehicleLifecycleNodeDo.builder()
                    .vin(node.getStr("vin"))
                    .node(VehicleLifecycleNode.valOf(node.getStr("node")))
                    .reachTime(new Date(node.getLong("reachTime")))
                    .sort(node.getInt("sort"))
                    .build();
            nodeDo.stateLoad();
            allNodeList.add(nodeDo);
        }
        return Optional.ofNullable(VehicleDo.builder()
                .vin(jsonObject.getStr("vin"))
                .allNodeList(allNodeList)
                .nodeTimeMap(jsonObject.getJSONObject("nodeTimeMap").toBean(new TypeReference<>() {
                }))
                .build());
    }

    @Override
    public void setVehicle(VehicleDo vehicle) {
        logger.debug("设置车辆[{}]领域对象缓存", vehicle.getVin());
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX_VEHICLE + vehicle.getVin(), JSONUtil.parse(vehicle).toJSONString(0));
    }

    @Override
    public Optional<QrcodeDo> getQrcode(String qrcode) {
        String qrcodeDoJson = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX_QRCODE + qrcode);
        if (StrUtil.isBlank(qrcodeDoJson)) {
            return Optional.empty();
        }
        JSONObject jsonObject = JSONUtil.parseObj(qrcodeDoJson);
        return Optional.ofNullable(QrcodeDo.builder()
                .vin(jsonObject.getStr("vin"))
                .sn(jsonObject.getStr("sn"))
                .qrcode(jsonObject.getStr("qrcode"))
                .type(QrcodeType.valOf(jsonObject.getStr("type")))
                .qrcodeState(QrcodeState.valOf(jsonObject.getStr("qrcodeState")))
                .createTime(new Date(jsonObject.getLong("createTime")))
                .build());
    }

    @Override
    public void setQrcode(QrcodeDo qrcode) {
        logger.debug("设置车辆[{}]二维码[{}]领域对象缓存", qrcode.getVin(), qrcode.getType());
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX_QRCODE + qrcode.getQrcode(),
                JSONUtil.parse(qrcode).toJSONString(0), 1, TimeUnit.HOURS);
    }
}

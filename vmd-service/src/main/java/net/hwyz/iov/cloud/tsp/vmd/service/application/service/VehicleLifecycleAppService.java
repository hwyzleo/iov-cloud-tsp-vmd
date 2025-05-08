package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.contract.enums.VehicleLifecycleNode;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.VehLifecycleDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehLifecyclePo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 车辆生命周期应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleLifecycleAppService {

    private final VehLifecycleDao vehLifecycleDao;

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
     * 查询车辆生命周期
     *
     * @param vin  车架号
     * @param node 生命周期节点
     * @return 车辆生命周期节点
     */
    public VehLifecyclePo getLifecycle(String vin, VehicleLifecycleNode node) {
        return vehLifecycleDao.selectPoByExample(VehLifecyclePo.builder().vin(vin).node(node.name()).build()).get(0);
    }

    /**
     * 新增车辆生命周期
     *
     * @param vin  车架号
     * @param node 生命周期节点
     */
    public void createVehicleLifecycle(String vin, VehicleLifecycleNode node) {
        createVehicleLifecycle(vin, node, new Date());
    }

    /**
     * 新增车辆生命周期
     *
     * @param vin       车架号
     * @param node      生命周期节点
     * @param reachTime 达到时间
     */
    public void createVehicleLifecycle(String vin, VehicleLifecycleNode node, Date reachTime) {
        createVehicleLifecycle(VehLifecyclePo.builder()
                .vin(vin)
                .node(node.name())
                .reachTime(reachTime)
                .sort(99)
                .build());
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
     * 修改车辆生命周期
     *
     * @param vehLifecyclePo 车辆生命周期
     * @return 结果
     */
    public int modifyVehicleLifecycle(VehLifecyclePo vehLifecyclePo) {
        return vehLifecycleDao.updatePo(vehLifecyclePo);
    }

    /**
     * 批量删除车辆生命周期
     *
     * @param vin 车架号
     */
    public void deleteVehicleLifecycleByVin(String vin) {
        vehLifecycleDao.batchPhysicalDeletePoByVin(vin);
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
     * 记录生产车辆节点
     *
     * @param vin 车架号
     */
    public void recordProduceNode(String vin) {
        logger.info("记录车辆[{}]生产节点", vin);
        createVehicleLifecycle(vin, VehicleLifecycleNode.PRODUCE);
    }

    /**
     * 记录生成车辆密钥节点
     *
     * @param vin 车架号
     */
    public void recordGenerateVehicleSkNode(String vin) {
        logger.info("记录车辆[{}]生成车辆密钥节点", vin);
        createVehicleLifecycle(vin, VehicleLifecycleNode.IMMO_SK);
    }

    /**
     * 记录第一次申请车联终端证书节点
     *
     * @param vin 车架号
     */
    public void recordFirstApplyTboxCertNode(String vin) {
        logger.info("记录车辆[{}]第一次申请车联终端证书节点", vin);
        createVehicleLifecycle(vin, VehicleLifecycleNode.TBOX_CERT);
    }

    /**
     * 记录第一次申请中央计算平台证书节点
     *
     * @param vin 车架号
     */
    public void recordFirstApplyCcpCertNode(String vin) {
        logger.info("记录车辆[{}]第一次申请中央计算平台证书节点", vin);
        createVehicleLifecycle(vin, VehicleLifecycleNode.CCP_CERT);
    }

    /**
     * 记录第一次申请信息娱乐模块证书节点
     *
     * @param vin 车架号
     */
    public void recordFirstApplyIdcmCertNode(String vin) {
        logger.info("记录车辆[{}]第一次申请信息娱乐模块证书节点", vin);
        createVehicleLifecycle(vin, VehicleLifecycleNode.IDCM_CERT);
    }

    /**
     * 记录第一次申请智驾模块证书节点
     *
     * @param vin 车架号
     */
    public void recordFirstApplyAdcmCertNode(String vin) {
        logger.info("记录车辆[{}]第一次申请智驾模块证书节点", vin);
        createVehicleLifecycle(vin, VehicleLifecycleNode.ADCM_CERT);
    }

    /**
     * 记录车辆下线节点
     *
     * @param vin     车架号
     * @param eolTime 下线时间
     */
    public void recordEolNode(String vin, Date eolTime) {
        logger.info("记录车辆[{}]下线节点", vin);
        createVehicleLifecycle(vin, VehicleLifecycleNode.EOL, eolTime);
    }

    /**
     * 记录车辆打印合格证节点
     *
     * @param vin      车架号
     * @param certTime 打印合格证时间
     */
    public void recordCertificateNode(String vin, Date certTime) {
        logger.info("记录车辆[{}]打印合格证节点", vin);
        createVehicleLifecycle(vin, VehicleLifecycleNode.CERTIFICATE, certTime);
    }

    /**
     * 绑定订单
     *
     * @param vin      车架号
     * @param orderNum 订单编号
     */
    public void recordBindOrderNode(String vin, String orderNum) {
        logger.info("记录车辆[{}]绑定订单[{}]节点", vin, orderNum);
        createVehicleLifecycle(vin, VehicleLifecycleNode.ORDER_BIND);
    }

    /**
     * 记录车辆激活节点
     *
     * @param vin       车架号
     * @param accountId 账户ID
     */
    public void recordVehicleActiveNode(String vin, String accountId) {
        logger.info("记录用户[{}]激活车辆[{}]节点", accountId, vin);
        createVehicleLifecycle(vin, VehicleLifecycleNode.VEHICLE_ACTIVE);
    }

}

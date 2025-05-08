package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPartPo;
import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆零部件表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2025-04-27
 */
@Mapper
public interface VehPartDao extends BaseDao<VehPartPo, Long> {

    /**
     * 根据ECU与序列号查询对应零部件
     *
     * @param ecu ECU
     * @param sn  序列号
     * @return 零部件
     */
    VehPartPo selectPoBySn(String ecu, String sn);

    /**
     * 根据ECU与车架号查询对应零部件
     *
     * @param ecu ECU
     * @param vin 车架号
     * @return 零部件
     */
    VehPartPo selectPoByVin(String ecu, String vin);

}

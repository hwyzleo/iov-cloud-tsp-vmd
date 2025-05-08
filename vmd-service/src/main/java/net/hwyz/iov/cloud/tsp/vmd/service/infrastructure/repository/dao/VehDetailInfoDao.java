package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehDetailInfoPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆详细信息表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2025-05-07
 */
@Mapper
public interface VehDetailInfoDao extends BaseDao<VehDetailInfoPo, Long> {

    /**
     * 根据车架号查询车辆详细信息
     *
     * @param vin 车架号
     * @return 车辆详细信息
     */
    VehDetailInfoPo selectPoByVin(String vin);

}

package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPlatformPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆基础信息表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-09-24
 */
@Mapper
public interface VehBasicInfoDao extends BaseDao<VehBasicInfoPo, Long> {

    /**
     * 根据车架号查询车辆基础信息
     *
     * @param vin 车架号
     * @return 车辆基础信息
     */
    VehBasicInfoPo selectPoByVin(String vin);

    /**
     * 批量物理删除车辆信息
     *
     * @param ids 车辆id数组
     * @return 影响行数
     */
    int batchPhysicalDeletePo(Long[] ids);

}

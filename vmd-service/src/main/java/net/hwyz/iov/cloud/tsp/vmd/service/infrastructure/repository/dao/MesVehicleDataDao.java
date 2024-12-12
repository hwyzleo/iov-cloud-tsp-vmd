package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.MesVehicleDataPo;
import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicInfoPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * MES车辆数据表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-12-12
 */
@Mapper
public interface MesVehicleDataDao extends BaseDao<MesVehicleDataPo, Long> {

    /**
     * 根据批次号查询MES车辆数据
     *
     * @param batchNum 批次号
     * @return MES车辆数据
     */
    MesVehicleDataPo selectPoByBatchNum(String batchNum);

    /**
     * 批量物理删除车辆信息
     *
     * @param ids 车辆id数组
     * @return 影响行数
     */
    int batchPhysicalDeletePo(Long[] ids);

}

package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehSeriesPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆车型表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-09-24
 */
@Mapper
public interface VehModelDao extends BaseDao<VehModelPo, Long> {

    /**
     * 通过code查询车型信息
     *
     * @param code 车型编码
     * @return 车型信息
     */
    VehModelPo selectPoByCode(String code);

    /**
     * 批量物理删除车型信息
     *
     * @param ids 车型id数组
     * @return 影响行数
     */
    int batchPhysicalDeletePo(Long[] ids);

}

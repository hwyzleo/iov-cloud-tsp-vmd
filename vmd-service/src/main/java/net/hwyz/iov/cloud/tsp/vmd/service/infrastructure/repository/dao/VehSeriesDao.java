package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPlatformPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehSeriesPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆车系表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-09-24
 */
@Mapper
public interface VehSeriesDao extends BaseDao<VehSeriesPo, Long> {

    /**
     * 通过code查询车系信息
     *
     * @param code 车系编码
     * @return 车系信息
     */
    VehSeriesPo selectPoByCode(String code);

    /**
     * 批量物理删除车系信息
     *
     * @param ids 车系id数组
     * @return 影响行数
     */
    int batchPhysicalDeletePo(Long[] ids);

}

package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehExteriorPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆外饰表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-10-11
 */
@Mapper
public interface VehExteriorDao extends BaseDao<VehExteriorPo, Long> {

}

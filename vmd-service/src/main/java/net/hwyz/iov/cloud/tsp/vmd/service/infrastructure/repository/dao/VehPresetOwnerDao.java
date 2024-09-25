package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPresetOwnerPo;
import net.hwyz.iov.cloud.tsp.framework.mysql.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆预设车主表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-09-25
 */
@Mapper
public interface VehPresetOwnerDao extends BaseDao<VehPresetOwnerPo, Long> {

}

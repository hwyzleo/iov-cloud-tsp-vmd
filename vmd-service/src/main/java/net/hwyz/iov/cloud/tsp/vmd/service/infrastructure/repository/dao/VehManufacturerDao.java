package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehManufacturerPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆生产厂商表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-09-23
 */
@Mapper
public interface VehManufacturerDao extends BaseDao<VehManufacturerPo, Long> {

}

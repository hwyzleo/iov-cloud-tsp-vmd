package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehFeatureFamilyPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆特征族表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2026-02-06
 */
@Mapper
public interface VehFeatureFamilyDao extends BaseDao<VehFeatureFamilyPo, Long> {

    /**
     * 根据车辆特征族代码获取车辆特征族信息
     *
     * @param code 车辆特征族代码
     * @return 车辆特征族信息
     */
    VehFeatureFamilyPo selectPoByCode(String code);

}

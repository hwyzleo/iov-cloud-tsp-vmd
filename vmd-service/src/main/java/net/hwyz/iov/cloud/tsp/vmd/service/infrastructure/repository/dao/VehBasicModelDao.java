package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBasicModelPo;
import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆基础车型表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2025-01-19
 */
@Mapper
public interface VehBasicModelDao extends BaseDao<VehBasicModelPo, Long> {

    /**
     * 通过code查询基础车型信息
     *
     * @param code 基础车型编码
     * @return 基础车型信息
     */
    VehBasicModelPo selectPoByCode(String code);

}

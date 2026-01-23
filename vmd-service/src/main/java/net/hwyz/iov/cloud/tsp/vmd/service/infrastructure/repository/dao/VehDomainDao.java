package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehDomainPo;
import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车载域表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2026-01-23
 */
@Mapper
public interface VehDomainDao extends BaseDao<VehDomainPo, Long> {

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 车载域信息
     */
    VehDomainPo selectPoByCode(String code);

}

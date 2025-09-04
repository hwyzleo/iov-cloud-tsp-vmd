package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelConfigPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆车型配置表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-10-11
 */
@Mapper
public interface VehModelConfigDao extends BaseDao<VehModelConfigPo, Long> {

    /**
     * 通过code查询车型配置信息
     *
     * @param code 车型配置编码
     * @return 车型配置信息
     */
    VehModelConfigPo selectPoByCode(String code);

}

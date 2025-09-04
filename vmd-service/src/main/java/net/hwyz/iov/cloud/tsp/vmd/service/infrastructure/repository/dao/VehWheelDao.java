package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehWheelPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆车轮表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-10-11
 */
@Mapper
public interface VehWheelDao extends BaseDao<VehWheelPo, Long> {

    /**
     * 通过code查询轮胎轮毂信息
     *
     * @param code 轮胎轮毂编码
     * @return 轮胎轮毂信息
     */
    VehWheelPo selectPoByCode(String code);

}

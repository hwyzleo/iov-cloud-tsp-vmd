package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehInteriorPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆内饰表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-10-11
 */
@Mapper
public interface VehInteriorDao extends BaseDao<VehInteriorPo, Long> {

    /**
     * 通过code查询内饰颜色信息
     *
     * @param code 内饰颜色编码
     * @return 内饰颜色信息
     */
    VehInteriorPo selectPoByCode(String code);

    /**
     * 批量物理删除内饰颜色信息
     *
     * @param ids 内饰颜色id数组
     * @return 影响行数
     */
    int batchPhysicalDeletePo(Long[] ids);

}

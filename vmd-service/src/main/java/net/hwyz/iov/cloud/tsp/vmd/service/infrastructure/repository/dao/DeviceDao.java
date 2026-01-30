package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.DevicePo;
import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 设备信息表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2026-01-26
 */
@Mapper
public interface DeviceDao extends BaseDao<DevicePo, Long> {

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 数据对象
     */
    DevicePo selectPoByCode(String code);

    /**
     * 查询所有
     *
     * @return 数据对象列表
     */
    List<DevicePo> selectAllPo();

}

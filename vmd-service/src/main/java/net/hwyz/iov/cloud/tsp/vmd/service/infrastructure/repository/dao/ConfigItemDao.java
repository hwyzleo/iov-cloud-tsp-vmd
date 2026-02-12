package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.ConfigItemPo;
import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 配置项表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2026-02-11
 */
@Mapper
public interface ConfigItemDao extends BaseDao<ConfigItemPo, Long> {

    /**
     * 根据配置项代码获取配置项信息
     *
     * @param code 配置项代码
     * @return 配置项信息
     */
    ConfigItemPo selectPoByCode(String code);

}

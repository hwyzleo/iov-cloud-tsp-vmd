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
     * 根据编码查询配置项信息
     *
     * @param code 编码
     * @return 配置项信息
     */
    ConfigItemPo selectPoByCode(String code);

}

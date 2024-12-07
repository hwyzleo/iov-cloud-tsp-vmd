package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.framework.mysql.dao.BaseDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBrandPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehManufacturerPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 车辆品牌表 DAO
 * </p>
 *
 * @author hwyz_leo
 * @since 2024-09-24
 */
@Mapper
public interface VehBrandDao extends BaseDao<VehBrandPo, Long> {

    /**
     * 通过code查询车辆品牌信息
     *
     * @param code 车辆品牌编码
     * @return 车辆品牌信息
     */
    VehBrandPo selectPoByCode(String code);

    /**
     * 批量物理删除车辆品牌信息
     *
     * @param ids 车辆平台id数组
     * @return 影响行数
     */
    int batchPhysicalDeletePo(Long[] ids);

}

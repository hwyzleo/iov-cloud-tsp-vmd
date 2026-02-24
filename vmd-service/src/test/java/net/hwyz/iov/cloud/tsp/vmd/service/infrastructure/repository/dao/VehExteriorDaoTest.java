package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.BaseTest;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehExteriorPo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 车辆外饰表 DAO 测试类
 *
 * @author hwyz_leo
 */
public class VehExteriorDaoTest extends BaseTest {

    @Autowired
    private VehExteriorDao vehExteriorDao;

    @Test
    @Order(1)
    @DisplayName("新增一条记录")
    public void testInsertPo() throws Exception {
        VehExteriorPo vehExteriorPo = VehExteriorPo.builder()
                .seriesCode("H01")
                .code("WS06")
                .name("冰川白")
                .enable(true)
                .sort(99)
                .build();
        vehExteriorDao.insertPo(vehExteriorPo);
    }

}

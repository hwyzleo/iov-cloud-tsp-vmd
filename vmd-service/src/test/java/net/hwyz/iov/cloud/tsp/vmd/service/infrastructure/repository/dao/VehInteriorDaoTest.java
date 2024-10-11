package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.BaseTest;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehExteriorPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehInteriorPo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 车辆内饰表 DAO 测试类
 *
 * @author hwyz_leo
 */
public class VehInteriorDaoTest extends BaseTest {

    @Autowired
    private VehInteriorDao vehInteriorDao;

    @Test
    @Order(1)
    @DisplayName("新增一条记录")
    public void testInsertPo() throws Exception {
        VehInteriorPo vehInteriorPo = VehInteriorPo.builder()
                .seriesCode("H01")
                .code("NS03")
                .name("霜雪白")
                .enable(true)
                .sort(99)
                .build();
        vehInteriorDao.insertPo(vehInteriorPo);
    }

}

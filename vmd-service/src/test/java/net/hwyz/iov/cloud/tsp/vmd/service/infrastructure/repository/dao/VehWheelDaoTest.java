package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.BaseTest;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehInteriorPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehWheelPo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 车辆车轮表 DAO 测试类
 *
 * @author hwyz_leo
 */
public class VehWheelDaoTest extends BaseTest {

    @Autowired
    private VehWheelDao vehWheelDao;

    @Test
    @Order(1)
    @DisplayName("新增一条记录")
    public void testInsertPo() throws Exception {
        VehWheelPo vehWheelPo = VehWheelPo.builder()
                .seriesCode("H01")
                .code("CL04")
                .name("21寸轮毂(四季胎)枪灰色")
                .enable(true)
                .sort(99)
                .build();
        vehWheelDao.insertPo(vehWheelPo);
    }

}

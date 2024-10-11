package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.BaseTest;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehOptionalPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehWheelPo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 车辆选装表 DAO 测试类
 *
 * @author hwyz_leo
 */
public class VehOptionalDaoTest extends BaseTest {

    @Autowired
    private VehOptionalDao vehOptionalDao;

    @Test
    @Order(1)
    @DisplayName("新增一条记录")
    public void testInsertPo() throws Exception {
        VehOptionalPo vehOptionalPo = VehOptionalPo.builder()
                .seriesCode("H01")
                .code("XZ03")
                .name("后拖挂牵引杠")
                .enable(true)
                .sort(99)
                .build();
        vehOptionalDao.insertPo(vehOptionalPo);
    }

}

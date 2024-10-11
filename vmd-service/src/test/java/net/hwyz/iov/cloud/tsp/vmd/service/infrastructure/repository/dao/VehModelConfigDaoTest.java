package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.BaseTest;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehModelConfigPo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 车辆车型配置表 DAO 测试类
 *
 * @author hwyz_leo
 */
public class VehModelConfigDaoTest extends BaseTest {

    @Autowired
    private VehModelConfigDao vehModelConfigDao;

    @Test
    @Order(1)
    @DisplayName("新增一条记录")
    public void testInsertPo() throws Exception {
        VehModelConfigPo vehModelConfigPo = VehModelConfigPo.builder()
                .platformCode("H")
                .seriesCode("H01")
                .modelCode("H0106")
                .code("H01060103030102")
                .name("寒01低配六座版 墨玉黑 霜雪白 21寸轮毂（四季胎）高亮黑 有备胎 高阶智驾")
                .exteriorCode("WS01")
                .interiorCode("NS03")
                .wheelCode("CL03")
                .spareTireCode("XZ01")
                .adasCode("XZ02")
                .enable(true)
                .sort(99)
                .build();
        vehModelConfigDao.insertPo(vehModelConfigPo);
    }

}

package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.BaseTest;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehBrandPo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 车辆品牌表 DAO 测试类
 *
 * @author hwyz_leo
 */
public class VehBrandDaoTest extends BaseTest {

    @Autowired
    private VehBrandDao vehBrandDao;

    @Test
    @Order(1)
    @DisplayName("新增一条记录")
    public void testInsertPo() throws Exception {
        VehBrandPo vehBrandPo = VehBrandPo.builder()
                .code("HWYZ")
                .name("寒微雅致")
                .nameEn("HWYZ")
                .enable(true)
                .sort(0)
                .build();
        vehBrandDao.insertPo(vehBrandPo);
    }

}

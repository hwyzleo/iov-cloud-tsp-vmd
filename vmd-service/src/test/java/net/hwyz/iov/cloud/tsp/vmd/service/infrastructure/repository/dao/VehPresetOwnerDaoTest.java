package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao;

import net.hwyz.iov.cloud.tsp.vmd.service.BaseTest;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.VehPresetOwnerPo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 车辆预设车主表 DAO 测试类
 *
 * @author hwyz_leo
 */
public class VehPresetOwnerDaoTest extends BaseTest {

    @Autowired
    private VehPresetOwnerDao vehPresetOwnerDao;

    @Test
    @Order(1)
    @DisplayName("新增一条记录")
    public void testInsertPo() throws Exception {
        VehPresetOwnerPo vehPresetOwnerPo = VehPresetOwnerPo.builder()
                .vin("HWYZTEST000000001")
                .realName("hwyz_leo")
                .countryRegionCode("+86")
                .mobile("13917288107")
                .build();
        vehPresetOwnerDao.insertPo(vehPresetOwnerPo);
    }

}

package net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.tsp.idk.api.contract.BtmExService;
import net.hwyz.iov.cloud.tsp.idk.api.contract.request.BatchImportBtmRequest;
import net.hwyz.iov.cloud.tsp.idk.api.feign.service.ExBtmInfoService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.ImportDataParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 蓝牙模块数据解析器V1.0
 *
 * @author hwyz_leo
 */
@Slf4j
@RequiredArgsConstructor
@Component("btmDataParserV1.0")
public class BtmDataParserV1_0 extends BaseParser implements ImportDataParser {

    private final ExBtmInfoService exBtmInfoService;

    @Override
    public void parse(String batchNum, JSONObject dataJson) {
        String supplier = getSupplier(dataJson);
        if (StrUtil.isBlank(supplier)) {
            logger.warn("蓝牙模块导入数据批次号[{}]供应商代码为空", batchNum);
        }
        JSONObject data = getData(dataJson);
        JSONArray items = data.getJSONArray("ITEMS");
        int btmInvalidCount = 0;
        BatchImportBtmRequest request = new BatchImportBtmRequest();
        request.setBatchNum(batchNum);
        request.setSupplierCode(supplier);
        List<BtmExService> btmList = new ArrayList<>();
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String sn = itemJson.getStr("SN");
            if (StrUtil.isBlank(sn)) {
                btmInvalidCount++;
                continue;
            }
            btmList.add(BtmExService.builder()
                    .sn(sn)
                    .no(itemJson.getStr("NO"))
                    .hsm(itemJson.getStr("HSM"))
                    .mac(itemJson.getStr("MAC"))
                    .build());
        }
        if (btmInvalidCount > 0) {
            logger.warn("蓝牙模块导入数据批次号[{}]存在无效蓝牙模块数据[{}]", batchNum, btmInvalidCount);
        }
        request.setBtmList(btmList);
        exBtmInfoService.batchImport(request);
    }
}

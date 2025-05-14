package net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.TboxExService;
import net.hwyz.iov.cloud.tsp.tbox.api.contract.request.BatchImportTboxRequest;
import net.hwyz.iov.cloud.tsp.tbox.api.feign.service.ExTboxInfoService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.ImportDataParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 车联终端数据解析器V1.0
 *
 * @author hwyz_leo
 */
@Slf4j
@RequiredArgsConstructor
@Component("tboxDataParserV1.0")
public class TboxDataParserV1_0 extends BaseParser implements ImportDataParser {

    private final ExTboxInfoService exTboxInfoService;

    @Override
    public void parse(String batchNum, JSONObject dataJson) {
        String supplier = getSupplier(dataJson);
        if (StrUtil.isBlank(supplier)) {
            logger.warn("车联终端导入数据批次号[{}]供应商代码为空", batchNum);
        }
        JSONObject data = getData(dataJson);
        JSONArray items = data.getJSONArray("ITEMS");
        int tboxInvalidCount = 0;
        BatchImportTboxRequest request = new BatchImportTboxRequest();
        request.setBatchNum(batchNum);
        request.setSupplierCode(supplier);
        List<TboxExService> tboxList = new ArrayList<>();
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String sn = itemJson.getStr("SN");
            String iccid1 = itemJson.getStr("ICCID1");
            if (StrUtil.isBlank(sn) || StrUtil.isBlank(iccid1)) {
                tboxInvalidCount++;
                continue;
            }
            tboxList.add(TboxExService.builder()
                    .sn(sn)
                    .no(itemJson.getStr("NO"))
                    .hsm(itemJson.getStr("HSM"))
                    .imei(itemJson.getStr("IMEI"))
                    .iccid1(iccid1)
                    .iccid2(itemJson.getStr("ICCID2"))
                    .build());
        }
        if (tboxInvalidCount > 0) {
            logger.warn("车联终端导入数据批次号[{}]存在无效车联终端数据[{}]", batchNum, tboxInvalidCount);
        }
        request.setTboxList(tboxList);
        exTboxInfoService.batchImport(request);
    }
}

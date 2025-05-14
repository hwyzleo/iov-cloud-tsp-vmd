package net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.tsp.ccp.api.contract.CcpExService;
import net.hwyz.iov.cloud.tsp.ccp.api.contract.request.BatchImportCcpRequest;
import net.hwyz.iov.cloud.tsp.ccp.api.feign.service.ExCcpInfoService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.ImportDataParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 中央计算平台数据解析器V1.0
 *
 * @author hwyz_leo
 */
@Slf4j
@RequiredArgsConstructor
@Component("ccpDataParserV1.0")
public class CcpDataParserV1_0 extends BaseParser implements ImportDataParser {

    private final ExCcpInfoService exCcpInfoService;

    @Override
    public void parse(String batchNum, JSONObject dataJson) {
        String supplier = getSupplier(dataJson);
        if (StrUtil.isBlank(supplier)) {
            logger.warn("车联终端导入数据批次号[{}]供应商代码为空", batchNum);
        }
        JSONObject data = getData(dataJson);
        JSONArray items = data.getJSONArray("ITEMS");
        int ccpInvalidCount = 0;
        BatchImportCcpRequest request = new BatchImportCcpRequest();
        request.setBatchNum(batchNum);
        request.setSupplierCode(supplier);
        List<CcpExService> ccpList = new ArrayList<>();
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String sn = itemJson.getStr("SN");
            if (StrUtil.isBlank(sn)) {
                ccpInvalidCount++;
                continue;
            }
            ccpList.add(CcpExService.builder()
                    .sn(sn)
                    .no(itemJson.getStr("NO"))
                    .hsm(itemJson.getStr("HSM"))
                    .build());
        }
        if (ccpInvalidCount > 0) {
            logger.warn("中央计算平台导入数据批次号[{}]存在无效中央计算平台数据[{}]", batchNum, ccpInvalidCount);
        }
        request.setCcpList(ccpList);
        exCcpInfoService.batchImport(request);
    }
}

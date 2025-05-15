package net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.StrUtil;
import net.hwyz.iov.cloud.tsp.idcm.api.contract.IdcmExService;
import net.hwyz.iov.cloud.tsp.idcm.api.contract.request.BatchImportIdcmRequest;
import net.hwyz.iov.cloud.tsp.idcm.api.feign.service.ExIdcmInfoService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.vid.ImportDataParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息娱乐模块数据解析器V1.0
 *
 * @author hwyz_leo
 */
@Slf4j
@RequiredArgsConstructor
@Component("idcmDataParserV1.0")
public class IdcmDataParserV1_0 extends BaseParser implements ImportDataParser {

    private final ExIdcmInfoService exIdcmInfoService;

    @Override
    public void parse(String batchNum, JSONObject dataJson) {
        String supplier = getSupplier(dataJson);
        if (StrUtil.isBlank(supplier)) {
            logger.warn("信息娱乐模块导入数据批次号[{}]供应商代码为空", batchNum);
        }
        JSONObject data = getData(dataJson);
        JSONArray items = data.getJSONArray("ITEMS");
        int idcmInvalidCount = 0;
        BatchImportIdcmRequest request = new BatchImportIdcmRequest();
        request.setBatchNum(batchNum);
        request.setSupplierCode(supplier);
        List<IdcmExService> idcmList = new ArrayList<>();
        for (Object item : items) {
            JSONObject itemJson = JSONUtil.parseObj(item);
            String sn = itemJson.getStr("SN");
            if (StrUtil.isBlank(sn)) {
                idcmInvalidCount++;
                continue;
            }
            idcmList.add(IdcmExService.builder()
                    .sn(sn)
                    .no(itemJson.getStr("NO"))
                    .hsm(itemJson.getStr("HSM"))
                    .mac(itemJson.getStr("MAC"))
                    .build());
        }
        if (idcmInvalidCount > 0) {
            logger.warn("信息娱乐模块导入数据批次号[{}]存在无效信息娱乐模块数据[{}]", batchNum, idcmInvalidCount);
        }
        request.setIdcmList(idcmList);
        exIdcmInfoService.batchImport(request);
    }
}

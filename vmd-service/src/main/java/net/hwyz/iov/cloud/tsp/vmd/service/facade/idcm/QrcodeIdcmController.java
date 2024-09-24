package net.hwyz.iov.cloud.tsp.vmd.service.facade.idcm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.framework.commons.bean.Response;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.response.QrcodeResponse;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.idcm.QrcodeIdcmApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.QrcodeAppService;
import org.springframework.web.bind.annotation.*;

/**
 * 二维码相关车机接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/idcm/qrcode")
public class QrcodeIdcmController implements QrcodeIdcmApi {

    private final QrcodeAppService qrcodeAppService;

    /**
     * 生成激活二维码
     *
     * @param vin      车架号
     * @param clientId 客户端ID
     * @return 二维码返回
     */
    @Override
    @PostMapping("/action/generateActiveQrcode")
    public Response<QrcodeResponse> generateActiveQrcode(@RequestHeader String vin, @RequestHeader String clientId) {
        logger.info("车辆[{}]车机[{}]生成激活二维码", vin, clientId);
        return new Response<>(qrcodeAppService.generateActiveQrcode(vin, clientId));
    }

    /**
     * 获取激活二维码状态
     *
     * @param vin      车架号
     * @param clientId 客户端ID
     * @return 二维码返回
     */
    @Override
    @GetMapping("")
    public Response<QrcodeResponse> getActiveQrcodeState(@RequestHeader String vin, @RequestHeader String clientId) {
        logger.info("车辆[{}]车机[{}]获取激活二维码状态", vin, clientId);
        return new Response<>(qrcodeAppService.getActiveQrcodeState(vin, clientId));
    }
}

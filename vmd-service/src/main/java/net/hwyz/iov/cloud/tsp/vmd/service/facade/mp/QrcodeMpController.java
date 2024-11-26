package net.hwyz.iov.cloud.tsp.vmd.service.facade.mp;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.bean.ClientAccount;
import net.hwyz.iov.cloud.framework.common.bean.Response;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.request.QrcodeRequest;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.response.QrcodeResponse;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mp.QrcodeMpApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.QrcodeAppService;
import org.springframework.web.bind.annotation.*;

/**
 * 二维码相关手机接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mp/qrcode")
public class QrcodeMpController implements QrcodeMpApi {

    private final QrcodeAppService qrcodeAppService;

    /**
     * 扫描验证二维码
     *
     * @param request       二维码请求
     * @param clientAccount 终端用户
     * @return 验证结果
     */
    @Override
    @PostMapping("/action/validateQrcode")
    public Response<QrcodeResponse> validateQrcode(@RequestBody @Valid QrcodeRequest request,
                                                   @RequestHeader ClientAccount clientAccount) {
        logger.info("手机客户端[{}]验证车辆二维码", ParamHelper.getClientAccountInfo(clientAccount));
        return new Response<>(qrcodeAppService.validateQrcode(request.getQrcode(), clientAccount.getAccountId()));
    }

    /**
     * 确认二维码
     *
     * @param request       二维码请求
     * @param clientAccount 终端用户
     * @return 确认结果
     */
    @Override
    @PostMapping("/action/confirmQrcode")
    public Response<QrcodeResponse> confirmQrcode(@RequestBody @Valid QrcodeRequest request,
                                                  @RequestHeader ClientAccount clientAccount) {
        logger.info("手机客户端[{}]确认车辆二维码", ParamHelper.getClientAccountInfo(clientAccount));
        return new Response<>(qrcodeAppService.confirmQrcode(request.getQrcode(), clientAccount.getAccountId()));
    }

}

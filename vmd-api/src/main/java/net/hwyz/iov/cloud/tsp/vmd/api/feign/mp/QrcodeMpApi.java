package net.hwyz.iov.cloud.tsp.vmd.api.feign.mp;

import net.hwyz.iov.cloud.framework.common.bean.ClientAccount;
import net.hwyz.iov.cloud.framework.common.bean.Response;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.request.QrcodeRequest;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.response.QrcodeResponse;

/**
 * 二维码相关手机接口
 *
 * @author hwyz_leo
 */
public interface QrcodeMpApi {

    /**
     * 扫描验证二维码
     *
     * @param request       二维码请求
     * @param clientAccount 终端用户
     * @return 验证结果
     */
    Response<QrcodeResponse> validateQrcode(QrcodeRequest request, ClientAccount clientAccount);

    /**
     * 确认二维码
     *
     * @param request       二维码请求
     * @param clientAccount 终端用户
     * @return 确认结果
     */
    Response<QrcodeResponse> confirmQrcode(QrcodeRequest request, ClientAccount clientAccount);

}

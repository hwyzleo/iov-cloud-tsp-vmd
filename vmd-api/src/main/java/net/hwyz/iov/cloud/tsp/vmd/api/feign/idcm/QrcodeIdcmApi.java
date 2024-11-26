package net.hwyz.iov.cloud.tsp.vmd.api.feign.idcm;

import net.hwyz.iov.cloud.framework.common.bean.Response;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.response.QrcodeResponse;

/**
 * 二维码相关车机接口
 *
 * @author hwyz_leo
 */
public interface QrcodeIdcmApi {

    /**
     * 生成车辆激活二维码
     *
     * @param vin      车架号
     * @param clientId 客户端ID
     * @return 二维码返回
     */
    Response<QrcodeResponse> generateActiveQrcode(String vin, String clientId);

    /**
     * 获取车辆激活二维码状态
     *
     * @param qrcode   二维码
     * @param vin      车架号
     * @param clientId 客户端ID
     * @return 二维码返回
     */
    Response<QrcodeResponse> getActiveQrcodeState(String qrcode, String vin, String clientId);

}

package net.hwyz.iov.cloud.tsp.vmd.api.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.enums.QrcodeState;

/**
 * 二维码响应
 *
 * @author hwyz_leo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrcodeResponse {

    /**
     * 二维码
     */
    private String qrcode;
    /**
     * 二维码状态
     */
    private QrcodeState state;

}

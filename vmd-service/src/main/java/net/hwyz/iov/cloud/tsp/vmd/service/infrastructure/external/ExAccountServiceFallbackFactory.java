package net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.external;

import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.account.api.contract.AccountInfo;
import net.hwyz.iov.cloud.tsp.vmd.service.domain.external.service.ExAccountService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

/**
 * 外部账号业务服务回退处理
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
public class ExAccountServiceFallbackFactory implements FallbackFactory<ExAccountService> {

    @Override
    public ExAccountService create(Throwable cause) {
        return new ExAccountService() {

            @Override
            public AccountInfo getAccountInfo(String accountId) {
                if (logger.isDebugEnabled()) {
                    logger.warn("获取账号信息异常", cause);
                } else {
                    logger.warn("获取账号信息异常:[{}]", cause.getMessage());
                }
                return null;
            }

        };
    }

}

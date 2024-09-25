package net.hwyz.iov.cloud.tsp.vmd.service.domain.external.service;

import net.hwyz.iov.cloud.tsp.account.api.contract.AccountInfo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.external.ExAccountServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 外部账号业务服务
 *
 * @author hwyz_leo
 */
@FeignClient(name = "account-service", path = "/service/account", fallbackFactory = ExAccountServiceFallbackFactory.class)
public interface ExAccountService {

    /**
     * 获取账号信息
     *
     * @param accountId 账号ID
     * @return 账号信息
     */
    @GetMapping(value = "/{accountId}")
    AccountInfo getAccountInfo(@PathVariable("accountId") String accountId);

}

package net.hwyz.iov.cloud.tsp.vmd.service.facade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.PartExService;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.PartAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.PartExServiceAssembler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 零件相关服务接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/service/part")
public class PartServiceController {

    private final PartAppService partAppService;

    /**
     * 根据零件号查询零件信息
     *
     * @param pn 零件号
     * @return 零件信息
     */
    @GetMapping("/{pn}")
    public PartExService getByPn(@PathVariable String pn) {
        logger.info("根据零件号[{}]查询零件信息", pn);
        return PartExServiceAssembler.INSTANCE.fromPo(partAppService.getPartByPn(pn));
    }

    /**
     * 获取所有FOTA升级零件信息
     *
     * @return 零件信息
     */
    @GetMapping("/listAllFota")
    public List<PartExService> listAllFota() {
        logger.info("获取所有FOTA升级零件信息");
        return PartExServiceAssembler.INSTANCE.fromPoList(partAppService.listAllFota());
    }

}

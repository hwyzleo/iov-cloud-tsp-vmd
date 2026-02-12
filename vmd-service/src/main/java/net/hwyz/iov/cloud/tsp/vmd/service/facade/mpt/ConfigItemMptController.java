package net.hwyz.iov.cloud.tsp.vmd.service.facade.mpt;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.audit.annotation.Log;
import net.hwyz.iov.cloud.framework.audit.enums.BusinessType;
import net.hwyz.iov.cloud.framework.common.web.controller.BaseController;
import net.hwyz.iov.cloud.framework.common.web.domain.AjaxResult;
import net.hwyz.iov.cloud.framework.common.web.page.TableDataInfo;
import net.hwyz.iov.cloud.framework.security.annotation.RequiresPermissions;
import net.hwyz.iov.cloud.framework.security.util.SecurityUtils;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ConfigItemMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.contract.ConfigItemOptionMpt;
import net.hwyz.iov.cloud.tsp.vmd.api.feign.mpt.ConfigItemMptApi;
import net.hwyz.iov.cloud.tsp.vmd.service.application.service.ConfigItemAppService;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.ConfigItemMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.facade.assembler.ConfigItemOptionMptAssembler;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.ConfigItemOptionPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.ConfigItemPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配置项相关管理接口实现类
 *
 * @author hwyz_leo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpt/configItem")
public class ConfigItemMptController extends BaseController implements ConfigItemMptApi {

    private final ConfigItemAppService configItemAppService;

    /**
     * 分页查询配置项信息
     *
     * @param configItem 配置项信息
     * @return 配置项信息列表
     */
    @RequiresPermissions("iov:configCenter:configItem:list")
    @Override
    @GetMapping(value = "/list")
    public TableDataInfo list(ConfigItemMpt configItem) {
        logger.info("管理后台用户[{}]分页查询配置项信息", SecurityUtils.getUsername());
        startPage();
        List<ConfigItemPo> configItemPoList = configItemAppService.search(configItem.getCode(), configItem.getName(),
                getBeginTime(configItem), getEndTime(configItem));
        List<ConfigItemMpt> configItemMptList = ConfigItemMptAssembler.INSTANCE.fromPoList(configItemPoList);
        return getDataTable(configItemPoList, configItemMptList);
    }

    /**
     * 查询配置项枚举值信息
     *
     * @param configItemCode   配置项代码
     * @param configItemOption 配置项枚举值信息
     * @return 配置项枚举值信息列表
     */
    @RequiresPermissions("iov:configCenter:configItem:list")
    @Override
    @GetMapping(value = "/{configItemCode}/option/list")
    public AjaxResult listOption(@PathVariable String configItemCode, ConfigItemOptionMpt configItemOption) {
        logger.info("管理后台用户[{}]查询配置项[{}]枚举值信息", SecurityUtils.getUsername(), configItemCode);
        List<ConfigItemOptionPo> configItemOptionPoList = configItemAppService.searchOption(configItemCode, configItemOption.getCode(),
                configItemOption.getName(), getBeginTime(configItemOption), getEndTime(configItemOption));
        return success(ConfigItemOptionMptAssembler.INSTANCE.fromPoList(configItemOptionPoList));
    }

    /**
     * 导出配置项信息
     *
     * @param response   响应
     * @param configItem 配置项信息
     */
    @Log(title = "配置项管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("iov:configCenter:configItem:export")
    @Override
    @PostMapping("/export")
    public void export(HttpServletResponse response, ConfigItemMpt configItem) {
        logger.info("管理后台用户[{}]导出配置项信息", SecurityUtils.getUsername());
    }

    /**
     * 根据配置项ID获取配置项信息
     *
     * @param configItemId 配置项ID
     * @return 配置项信息
     */
    @RequiresPermissions("iov:configCenter:configItem:query")
    @Override
    @GetMapping(value = "/{configItemId}")
    public AjaxResult getInfo(@PathVariable Long configItemId) {
        logger.info("管理后台用户[{}]根据配置项ID[{}]获取配置项信息", SecurityUtils.getUsername(), configItemId);
        ConfigItemPo configItemPo = configItemAppService.getConfigItemById(configItemId);
        return success(ConfigItemMptAssembler.INSTANCE.fromPo(configItemPo));
    }

    /**
     * 根据配置项枚举值ID获取配置项枚举值信息
     *
     * @param configItemCode     配置项代码
     * @param configItemOptionId 配置项枚举值ID
     * @return 配置项枚举值信息
     */
    @RequiresPermissions("iov:configCenter:configItem:query")
    @Override
    @GetMapping(value = "/{configItemCode}/option/{configItemOptionId}")
    public AjaxResult getOptionInfo(@PathVariable String configItemCode, @PathVariable Long configItemOptionId) {
        logger.info("管理后台用户[{}]根据配置项[{}]枚举值ID[{}]获取配置项枚举值信息", SecurityUtils.getUsername(), configItemCode, configItemOptionId);
        ConfigItemOptionPo configItemOptionPo = configItemAppService.getConfigItemOptionById(configItemCode, configItemOptionId);
        return success(ConfigItemOptionMptAssembler.INSTANCE.fromPo(configItemOptionPo));
    }

    /**
     * 新增配置项信息
     *
     * @param configItem 配置项信息
     * @return 结果
     */
    @Log(title = "配置项管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("iov:configCenter:configItem:add")
    @Override
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ConfigItemMpt configItem) {
        logger.info("管理后台用户[{}]新增配置项信息[{}]", SecurityUtils.getUsername(), configItem.getCode());
        if (!configItemAppService.checkCodeUnique(configItem.getId(), configItem.getCode())) {
            return error("新增配置项'" + configItem.getCode() + "'失败，配置项代码已存在");
        }
        ConfigItemPo configItemPo = ConfigItemMptAssembler.INSTANCE.toPo(configItem);
        configItemPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(configItemAppService.createConfigItem(configItemPo));
    }

    /**
     * 新增配置项枚举值信息
     *
     * @param configItemCode   配置项代码
     * @param configItemOption 配置项枚举值信息
     * @return 结果
     */
    @Log(title = "配置项管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("iov:configCenter:configItem:edit")
    @Override
    @PostMapping("/{configItemCode}/option")
    public AjaxResult addOption(@PathVariable String configItemCode, @Validated @RequestBody ConfigItemOptionMpt configItemOption) {
        logger.info("管理后台用户[{}]新增配置项[{}]枚举值信息[{}]", SecurityUtils.getUsername(), configItemCode, configItemOption.getCode());
        if (!configItemAppService.checkOptionCodeUnique(configItemOption.getId(), configItemCode, configItemOption.getCode())) {
            return error("新增配置项枚举值'" + configItemOption.getCode() + "'失败，配置项枚举值代码已存在");
        }
        ConfigItemOptionPo configItemOptionPo = ConfigItemOptionMptAssembler.INSTANCE.toPo(configItemOption);
        configItemOptionPo.setCreateBy(SecurityUtils.getUserId().toString());
        return toAjax(configItemAppService.createConfigItemOption(configItemCode, configItemOptionPo));
    }

    /**
     * 修改保存配置项信息
     *
     * @param configItem 配置项信息
     * @return 结果
     */
    @Log(title = "配置项管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("iov:configCenter:configItem:edit")
    @Override
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ConfigItemMpt configItem) {
        logger.info("管理后台用户[{}]修改保存配置项信息[{}]", SecurityUtils.getUsername(), configItem.getCode());
        if (!configItemAppService.checkCodeUnique(configItem.getId(), configItem.getCode())) {
            return error("修改保存配置项'" + configItem.getCode() + "'失败，配置项代码已存在");
        }
        ConfigItemPo configItemPo = ConfigItemMptAssembler.INSTANCE.toPo(configItem);
        configItemPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(configItemAppService.modifyConfigItem(configItemPo));
    }

    /**
     * 修改保存配置项枚举值信息
     *
     * @param configItemCode   配置项代码
     * @param configItemOption 配置项枚举值信息
     * @return 结果
     */
    @Log(title = "配置项管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("iov:configCenter:configItem:edit")
    @Override
    @PutMapping("/{configItemCode}/option")
    public AjaxResult editOption(@PathVariable String configItemCode, @Validated @RequestBody ConfigItemOptionMpt configItemOption) {
        logger.info("管理后台用户[{}]修改保存配置项[{}]枚举值信息[{}]", SecurityUtils.getUsername(), configItemCode, configItemOption.getCode());
        if (!configItemAppService.checkOptionCodeUnique(configItemOption.getId(), configItemCode, configItemOption.getCode())) {
            return error("修改保存配置项枚举值'" + configItemOption.getCode() + "'失败，配置项枚举值代码已存在");
        }
        ConfigItemOptionPo configItemOptionPo = ConfigItemOptionMptAssembler.INSTANCE.toPo(configItemOption);
        configItemOptionPo.setModifyBy(SecurityUtils.getUserId().toString());
        return toAjax(configItemAppService.modifyConfigItemOption(configItemCode, configItemOptionPo));
    }

    /**
     * 删除配置项信息
     *
     * @param configItemIds 配置项ID数组
     * @return 结果
     */
    @Log(title = "配置项管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("iov:configCenter:configItem:remove")
    @Override
    @DeleteMapping("/{configItemIds}")
    public AjaxResult remove(@PathVariable Long[] configItemIds) {
        logger.info("管理后台用户[{}]删除配置项信息[{}]", SecurityUtils.getUsername(), configItemIds);
        return toAjax(configItemAppService.deleteConfigItemByIds(configItemIds));
    }

    /**
     * 删除配置项枚举值信息
     *
     * @param configItemCode      配置项代码
     * @param configItemOptionIds 配置项枚举值ID数组
     * @return 结果
     */
    @Log(title = "配置项管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("iov:configCenter:configItem:edit")
    @Override
    @DeleteMapping("/{configItemCode}/option/{configItemOptionIds}")
    public AjaxResult removeOption(@PathVariable String configItemCode, @PathVariable Long[] configItemOptionIds) {
        logger.info("管理后台用户[{}]删除配置项[{}]枚举值信息[{}]", SecurityUtils.getUsername(), configItemCode, configItemOptionIds);
        return toAjax(configItemAppService.deleteConfigItemOptionByIds(configItemCode, configItemOptionIds));
    }
}

package net.hwyz.iov.cloud.tsp.vmd.service.application.service;

import cn.hutool.core.util.ObjUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.util.ParamHelper;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.ConfigItemDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.dao.ConfigItemOptionDao;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.ConfigItemOptionPo;
import net.hwyz.iov.cloud.tsp.vmd.service.infrastructure.repository.po.ConfigItemPo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置项应用服务类
 *
 * @author hwyz_leo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigItemAppService {

    private final ConfigItemDao configItemDao;
    private final ConfigItemOptionDao configItemOptionDao;

    /**
     * 查询配置项信息
     *
     * @param code      配置项代码
     * @param name      配置项名称
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 配置项列表
     */
    public List<ConfigItemPo> search(String code, String name, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return configItemDao.selectPoByMap(map);
    }

    /**
     * 查询配置项枚举值信息
     *
     * @param configItemCode 配置项代码
     * @param code           枚举值代码
     * @param name           枚举值名称
     * @param beginTime      开始时间
     * @param endTime        结束时间
     * @return 配置项列表
     */
    public List<ConfigItemOptionPo> searchOption(String configItemCode, String code, String name, Date beginTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("configItemCode", configItemCode);
        map.put("code", code);
        map.put("name", ParamHelper.fuzzyQueryParam(name));
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return configItemOptionDao.selectPoByMap(map);
    }

    /**
     * 检查配置项代码是否唯一
     *
     * @param configItemId 配置项ID
     * @param code         配置项代码
     * @return 结果
     */
    public Boolean checkCodeUnique(Long configItemId, String code) {
        if (ObjUtil.isNull(configItemId)) {
            configItemId = -1L;
        }
        ConfigItemPo configItemPo = getConfigItemByCode(code);
        return !ObjUtil.isNotNull(configItemPo) || configItemPo.getId().longValue() == configItemId.longValue();
    }

    /**
     * 检查配置项枚举值代码是否唯一
     *
     * @param configItemOptionId 配置项ID
     * @param configItemCode     配置项代码
     * @param code               配置项代码
     * @return 结果
     */
    public Boolean checkOptionCodeUnique(Long configItemOptionId, String configItemCode, String code) {
        if (ObjUtil.isNull(configItemOptionId)) {
            configItemOptionId = -1L;
        }
        ConfigItemOptionPo configItemOptionPo = getConfigItemOptionByCode(configItemCode, code);
        return !ObjUtil.isNotNull(configItemOptionPo) || configItemOptionPo.getId().longValue() == configItemOptionId.longValue();
    }

    /**
     * 根据主键ID获取配置项信息
     *
     * @param id 主键ID
     * @return 配置项信息
     */
    public ConfigItemPo getConfigItemById(Long id) {
        return configItemDao.selectPoById(id);
    }

    /**
     * 根据主键ID获取配置项枚举值信息
     *
     * @param configItemCode 配置项代码
     * @param id             主键ID
     * @return 配置项枚举值信息
     */
    public ConfigItemOptionPo getConfigItemOptionById(String configItemCode, Long id) {
        return configItemOptionDao.selectPoById(id);
    }

    /**
     * 根据配置项代码获取配置项信息
     *
     * @param code 配置项代码
     * @return 配置项信息
     */
    public ConfigItemPo getConfigItemByCode(String code) {
        return configItemDao.selectPoByCode(code);
    }

    /**
     * 根据配置项枚举值代码获取配置项枚举值信息
     *
     * @param configItemCode 配置项代码
     * @param code           配置项枚举值代码
     * @return 配置项枚举值信息
     */
    public ConfigItemOptionPo getConfigItemOptionByCode(String configItemCode, String code) {
        return configItemOptionDao.selectPoByCode(configItemCode, code);
    }

    /**
     * 新增配置项
     *
     * @param configItem 配置项信息
     * @return 结果
     */
    public int createConfigItem(ConfigItemPo configItem) {
        return configItemDao.insertPo(configItem);
    }

    /**
     * 新增配置项枚举值
     *
     * @param configItemCode   配置项代码
     * @param configItemOption 配置项枚举值信息
     * @return 结果
     */
    public int createConfigItemOption(String configItemCode, ConfigItemOptionPo configItemOption) {
        return configItemOptionDao.insertPo(configItemOption);
    }

    /**
     * 修改配置项
     *
     * @param configItem 配置项信息
     * @return 结果
     */
    public int modifyConfigItem(ConfigItemPo configItem) {
        return configItemDao.updatePo(configItem);
    }

    /**
     * 修改配置项枚举值
     *
     * @param configItemCode   配置项代码
     * @param configItemOption 配置项枚举值信息
     * @return 结果
     */
    public int modifyConfigItemOption(String configItemCode, ConfigItemOptionPo configItemOption) {
        return configItemOptionDao.updatePo(configItemOption);
    }

    /**
     * 批量删除配置项
     *
     * @param ids 配置项ID数组
     * @return 结果
     */
    public int deleteConfigItemByIds(Long[] ids) {
        return configItemDao.batchPhysicalDeletePo(ids);
    }

    /**
     * 批量删除配置项
     *
     * @param configItemCode 配置项代码
     * @param ids            配置项ID数组
     * @return 结果
     */
    public int deleteConfigItemOptionByIds(String configItemCode, Long[] ids) {
        return configItemOptionDao.batchPhysicalDeletePo(ids);
    }

}

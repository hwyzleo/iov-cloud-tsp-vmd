package net.hwyz.iov.cloud.tsp.vmd.api.feign.service;

/**
 * 车辆车系车型配置相关服务接口
 *
 * @author hwyz_leo
 */
public interface VehicleModelConfigServiceApi {

    /**
     * 根据车型配置类型得到匹配的车型配置代码
     *
     * @param modelCode     车型代码
     * @param exteriorCode  外饰代码
     * @param interiorCode  内饰代码
     * @param wheelCode     车轮代码
     * @param spareTireCode 备胎代码
     * @param adasCode      智驾代码
     * @return 车型配置代码
     */
    String getVehicleModeConfigCode(String modelCode, String exteriorCode, String interiorCode, String wheelCode, String spareTireCode, String adasCode);

}

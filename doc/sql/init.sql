DROP TABLE IF EXISTS `db_vmd`.`tb_veh_basic_info`;
CREATE TABLE `db_vmd`.`tb_veh_basic_info`
(
    `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `vin`                  VARCHAR(20)  NOT NULL COMMENT '车架号',
    `manufacturer_code`    VARCHAR(255) NOT NULL COMMENT '工厂代码',
    `brand_code`           VARCHAR(255) NOT NULL COMMENT '品牌代码',
    `platform_code`        VARCHAR(255) NOT NULL COMMENT '平台代码',
    `series_code`          VARCHAR(255) NOT NULL COMMENT '车系代码',
    `model_code`           VARCHAR(255) NOT NULL COMMENT '车型代码',
    `basic_model_code`     VARCHAR(255) NOT NULL COMMENT '基础车型代码',
    `model_config_code`    VARCHAR(255) NOT NULL COMMENT '车型配置代码',
    `eol_time`             TIMESTAMP             DEFAULT NULL COMMENT '车辆下线时间',
    `pdi_time`             TIMESTAMP             DEFAULT NULL COMMENT '最后一次PDI时间',
    `order_num`            VARCHAR(50)           DEFAULT NULL COMMENT '订单编码',
    `vehicle_base_version` VARCHAR(255)          DEFAULT NULL COMMENT '整车基线版本',
    `description`          VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`            VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`            VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`          INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`            TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`vin`),
    INDEX `idx_order_num` (`order_num`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆基础信息表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_detail_info`;
CREATE TABLE `db_vmd`.`tb_veh_detail_info`
(
    `id`                      BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `vin`                     VARCHAR(20) NOT NULL COMMENT '车架号',
    `production_order`        VARCHAR(255)         DEFAULT NULL COMMENT '生产订单',
    `matnr`                   VARCHAR(255)         DEFAULT NULL COMMENT '整车物料编码',
    `project`                 VARCHAR(255)         DEFAULT NULL COMMENT '车型项目',
    `sales_area`              VARCHAR(255)         DEFAULT NULL COMMENT '销售区域',
    `body_form`               VARCHAR(255)         DEFAULT NULL COMMENT '车身形式',
    `config_level`            VARCHAR(255)         DEFAULT NULL COMMENT '配置等级',
    `model_year`              VARCHAR(255)         DEFAULT NULL COMMENT '车型年代',
    `steering_wheel_position` VARCHAR(255)         DEFAULT NULL COMMENT '方向盘位置',
    `interior_type`           VARCHAR(255)         DEFAULT NULL COMMENT '内饰风格',
    `exterior_color`          VARCHAR(255)         DEFAULT NULL COMMENT '外饰颜色',
    `drive_form`              VARCHAR(255)         DEFAULT NULL COMMENT '驱动形式',
    `wheel`                   VARCHAR(255)         DEFAULT NULL COMMENT '车轮',
    `wheel_color`             VARCHAR(255)         DEFAULT NULL COMMENT '车轮颜色',
    `seat_type`               VARCHAR(255)         DEFAULT NULL COMMENT '座椅类型',
    `assisted_driving`        VARCHAR(255)         DEFAULT NULL COMMENT '辅助驾驶',
    `etc_system`              VARCHAR(255)         DEFAULT NULL COMMENT 'ETC系统',
    `rear_tow_bar`            VARCHAR(255)         DEFAULT NULL COMMENT '后牵引杆',
    `engine_no`               VARCHAR(255)         DEFAULT NULL COMMENT '发动机编码',
    `engine_type`             VARCHAR(255)         DEFAULT NULL COMMENT '发动机类型',
    `front_drive_motor_no`    VARCHAR(255)         DEFAULT NULL COMMENT '前驱电机编码',
    `front_drive_motor_type`  VARCHAR(255)         DEFAULT NULL COMMENT '前驱电机类型',
    `rear_drive_motor_no`     VARCHAR(255)         DEFAULT NULL COMMENT '后驱电机编码',
    `rear_drive_motor_type`   VARCHAR(255)         DEFAULT NULL COMMENT '后驱电机类型',
    `generator_no`            VARCHAR(255)         DEFAULT NULL COMMENT '发电机编码',
    `generator_type`          VARCHAR(255)         DEFAULT NULL COMMENT '发电机类型',
    `power_battery_pack_no`   VARCHAR(255)         DEFAULT NULL COMMENT '动力电池包编码',
    `power_battery_type`      VARCHAR(255)         DEFAULT NULL COMMENT '动力电池类型',
    `power_battery_factory`   VARCHAR(255)         DEFAULT NULL COMMENT '动力电池厂商',
    `description`             VARCHAR(255)         DEFAULT NULL COMMENT '备注',
    `create_time`             TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`               VARCHAR(64)          DEFAULT NULL COMMENT '创建者',
    `modify_time`             TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`               VARCHAR(64)          DEFAULT NULL COMMENT '修改者',
    `row_version`             INT                  DEFAULT 1 COMMENT '记录版本',
    `row_valid`               TINYINT              DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`vin`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆详细信息表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_manufacturer`;
CREATE TABLE `db_vmd`.`tb_veh_manufacturer`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`        VARCHAR(255) NOT NULL COMMENT '工厂代码',
    `name`        VARCHAR(255) NOT NULL COMMENT '工厂名称',
    `name_en`     VARCHAR(255)          DEFAULT NULL COMMENT '工厂英文名称',
    `enable`      TINYINT      NOT NULL COMMENT '是否启用',
    `sort`        INT          NOT NULL COMMENT '排序',
    `description` VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`   VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version` INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`   TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆生产厂商表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_brand`;
CREATE TABLE `db_vmd`.`tb_veh_brand`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`        VARCHAR(255) NOT NULL COMMENT '品牌代码',
    `name`        VARCHAR(255) NOT NULL COMMENT '品牌名称',
    `name_en`     VARCHAR(255)          DEFAULT NULL COMMENT '品牌英文名称',
    `enable`      TINYINT      NOT NULL COMMENT '是否启用',
    `sort`        INT          NOT NULL COMMENT '排序',
    `description` VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`   VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version` INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`   TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆品牌表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_platform`;
CREATE TABLE `db_vmd`.`tb_veh_platform`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`        VARCHAR(255) NOT NULL COMMENT '平台代码',
    `name`        VARCHAR(255) NOT NULL COMMENT '平台名称',
    `name_en`     VARCHAR(255)          DEFAULT NULL COMMENT '平台英文名称',
    `enable`      TINYINT      NOT NULL COMMENT '是否启用',
    `sort`        INT          NOT NULL COMMENT '排序',
    `description` VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`   VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version` INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`   TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆平台表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_series`;
CREATE TABLE `db_vmd`.`tb_veh_series`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform_code` VARCHAR(255) NOT NULL COMMENT '平台代码',
    `code`          VARCHAR(255) NOT NULL COMMENT '车系代码',
    `name`          VARCHAR(255) NOT NULL COMMENT '车系名称',
    `name_en`       VARCHAR(255)          DEFAULT NULL COMMENT '车系英文名称',
    `enable`        TINYINT      NOT NULL COMMENT '是否启用',
    `sort`          INT          NOT NULL COMMENT '排序',
    `description`   VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`     VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`   INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`     TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆车系表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_model`;
CREATE TABLE `db_vmd`.`tb_veh_model`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform_code` VARCHAR(255) NOT NULL COMMENT '平台代码',
    `series_code`   VARCHAR(255) NOT NULL COMMENT '车系代码',
    `code`          VARCHAR(255) NOT NULL COMMENT '车型代码',
    `name`          VARCHAR(255) NOT NULL COMMENT '车型名称',
    `name_en`       VARCHAR(255)          DEFAULT NULL COMMENT '车型英文名称',
    `enable`        TINYINT      NOT NULL COMMENT '是否启用',
    `sort`          INT          NOT NULL COMMENT '排序',
    `description`   VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`     VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`   INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`     TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆车型表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_basic_model`;
CREATE TABLE `db_vmd`.`tb_veh_basic_model`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform_code` VARCHAR(255) NOT NULL COMMENT '平台代码',
    `series_code`   VARCHAR(255) NOT NULL COMMENT '车系代码',
    `model_code`    VARCHAR(255) NOT NULL COMMENT '车型代码',
    `code`          VARCHAR(255) NOT NULL COMMENT '基础车型代码',
    `name`          VARCHAR(255) NOT NULL COMMENT '基础车型名称',
    `name_en`       VARCHAR(255)          DEFAULT NULL COMMENT '基础车型英文名称',
    `enable`        TINYINT      NOT NULL COMMENT '是否启用',
    `sort`          INT          NOT NULL COMMENT '排序',
    `description`   VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`     VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`   INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`     TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆基础车型表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_model_config`;
CREATE TABLE `db_vmd`.`tb_veh_model_config`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform_code`    VARCHAR(255) NOT NULL COMMENT '平台代码',
    `series_code`      VARCHAR(255) NOT NULL COMMENT '车系代码',
    `model_code`       VARCHAR(255) NOT NULL COMMENT '车型代码',
    `basic_model_code` VARCHAR(255) NOT NULL COMMENT '基础车型代码',
    `code`             VARCHAR(255) NOT NULL COMMENT '车型配置代码：车型代码+外饰代码(2)+内饰代码(2)+车轮代码(2)+备胎代码(2)+智驾代码(2)',
    `name`             VARCHAR(255) NOT NULL COMMENT '车型配置名称',
    `name_en`          VARCHAR(255)          DEFAULT NULL COMMENT '车型配置英文名称',
    `exterior_code`    VARCHAR(50)           DEFAULT NULL COMMENT '外饰代码',
    `interior_code`    VARCHAR(50)           DEFAULT NULL COMMENT '内饰代码',
    `wheel_code`       VARCHAR(50)           DEFAULT NULL COMMENT '车轮代码',
    `spare_tire_code`  VARCHAR(50)           DEFAULT NULL COMMENT '备胎代码',
    `adas_code`        VARCHAR(50)           DEFAULT NULL COMMENT '智驾代码',
    `seat_code`        VARCHAR(50)           DEFAULT NULL COMMENT '座椅代码',
    `enable`           TINYINT               DEFAULT 1 COMMENT '是否启用',
    `sort`             INT                   DEFAULT 99 COMMENT '排序',
    `description`      VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`        VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`        VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`      INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`        TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆车型配置表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_lifecycle`;
CREATE TABLE `db_vmd`.`tb_veh_lifecycle`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `vin`         VARCHAR(20)  NOT NULL COMMENT '车架号',
    `node`        VARCHAR(255) NOT NULL COMMENT '生命周期节点',
    `reach_time`  TIMESTAMP             DEFAULT NULL COMMENT '触达时间',
    `sort`        INT          NOT NULL COMMENT '排序',
    `description` VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`   VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version` INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`   TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    INDEX `idx_vin` (`vin`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆生命周期表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_preset_owner`;
CREATE TABLE `db_vmd`.`tb_veh_preset_owner`
(
    `id`                  BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `vin`                 VARCHAR(20) NOT NULL COMMENT '车架号',
    `real_name`           VARCHAR(100)         DEFAULT NULL COMMENT '车主真实姓名',
    `country_region_code` VARCHAR(20) NOT NULL COMMENT '手机所属国家或地区',
    `mobile`              VARCHAR(15) NOT NULL COMMENT '手机号',
    `description`         VARCHAR(255)         DEFAULT NULL COMMENT '备注',
    `create_time`         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`           VARCHAR(64)          DEFAULT NULL COMMENT '创建者',
    `modify_time`         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`           VARCHAR(64)          DEFAULT NULL COMMENT '修改者',
    `row_version`         INT                  DEFAULT 1 COMMENT '记录版本',
    `row_valid`           TINYINT              DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    INDEX `idx_vin` (`vin`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆预设车主表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_exterior`;
CREATE TABLE `db_vmd`.`tb_veh_exterior`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform_code` VARCHAR(255) NOT NULL COMMENT '平台代码',
    `series_code`   VARCHAR(255) NOT NULL COMMENT '车系代码',
    `code`          VARCHAR(100) NOT NULL COMMENT '外饰代码',
    `name`          VARCHAR(255) NOT NULL COMMENT '外饰名称',
    `name_en`       VARCHAR(255)          DEFAULT NULL COMMENT '外饰英文名称',
    `enable`        TINYINT               DEFAULT 1 COMMENT '是否启用',
    `sort`          INT                   DEFAULT 99 COMMENT '排序',
    `description`   VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`     VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`   INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`     TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车身颜色表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_interior`;
CREATE TABLE `db_vmd`.`tb_veh_interior`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform_code` VARCHAR(255) NOT NULL COMMENT '平台代码',
    `series_code`   VARCHAR(255) NOT NULL COMMENT '车系代码',
    `code`          VARCHAR(100) NOT NULL COMMENT '内饰代码',
    `name`          VARCHAR(255) NOT NULL COMMENT '内饰名称',
    `name_en`       VARCHAR(255)          DEFAULT NULL COMMENT '内饰英文名称',
    `enable`        TINYINT               DEFAULT 1 COMMENT '是否启用',
    `sort`          INT                   DEFAULT 99 COMMENT '排序',
    `description`   VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`     VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`   INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`     TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆内饰表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_wheel`;
CREATE TABLE `db_vmd`.`tb_veh_wheel`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform_code` VARCHAR(255) NOT NULL COMMENT '平台代码',
    `series_code`   VARCHAR(255) NOT NULL COMMENT '车系代码',
    `code`          VARCHAR(100) NOT NULL COMMENT '车轮代码',
    `name`          VARCHAR(255) NOT NULL COMMENT '车轮名称',
    `name_en`       VARCHAR(255)          DEFAULT NULL COMMENT '车轮英文名称',
    `enable`        TINYINT               DEFAULT 1 COMMENT '是否启用',
    `sort`          INT                   DEFAULT 99 COMMENT '排序',
    `description`   VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`     VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`   INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`     TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='轮胎轮毂表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_optional`;
CREATE TABLE `db_vmd`.`tb_veh_optional`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `series_code` VARCHAR(255) NOT NULL COMMENT '车系代码',
    `code`        VARCHAR(100) NOT NULL COMMENT '选装代码',
    `name`        VARCHAR(255) NOT NULL COMMENT '选装名称',
    `name_en`     VARCHAR(255)          DEFAULT NULL COMMENT '选装英文名称',
    `enable`      TINYINT               DEFAULT 1 COMMENT '是否启用',
    `sort`        INT                   DEFAULT 99 COMMENT '排序',
    `description` VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`   VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version` INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`   TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆选装表';

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_import_data`;
CREATE TABLE `db_vmd`.`tb_veh_import_data`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `batch_num`   VARCHAR(255) NOT NULL COMMENT '批次号',
    `type`        VARCHAR(100) NOT NULL COMMENT '数据类型',
    `version`     VARCHAR(100) NOT NULL COMMENT '数据版本',
    `data`        TEXT         NOT NULL COMMENT '车辆导入数据',
    `handle`      TINYINT               DEFAULT 0 COMMENT '是否处理',
    `description` VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`   VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version` INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`   TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`batch_num`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆导入数据表';
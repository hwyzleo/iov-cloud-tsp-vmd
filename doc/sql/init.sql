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

DROP TABLE IF EXISTS `db_vmd`.`tb_supplier`;
CREATE TABLE `db_vmd`.`tb_supplier`
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`               VARCHAR(255) NOT NULL COMMENT '供应商代码',
    `name`               VARCHAR(255) NOT NULL COMMENT '供应商名称',
    `name_short`         VARCHAR(255)          DEFAULT NULL COMMENT '供应商名称缩写',
    `name_en`            VARCHAR(255)          DEFAULT NULL COMMENT '供应商英文名称',
    `type`               SMALLINT              DEFAULT NULL COMMENT '供应商类型',
    `province`           VARCHAR(255)          DEFAULT NULL COMMENT '省',
    `city`               VARCHAR(255)          DEFAULT NULL COMMENT '市',
    `county`             VARCHAR(255)          DEFAULT NULL COMMENT '区',
    `subdistrict`        VARCHAR(255)          DEFAULT NULL COMMENT '街道',
    `address`            VARCHAR(255)          DEFAULT NULL COMMENT '地址',
    `zipcode`            VARCHAR(20)           DEFAULT NULL COMMENT '邮编',
    `fax`                VARCHAR(50)           DEFAULT NULL COMMENT '供应商传真',
    `tel`                VARCHAR(50)           DEFAULT NULL COMMENT '供应商电话',
    `website`            VARCHAR(255)          DEFAULT NULL COMMENT '供应商网站',
    `email`              VARCHAR(255)          DEFAULT NULL COMMENT '供应商邮箱',
    `contact_person`     VARCHAR(255)          DEFAULT NULL COMMENT '联系人',
    `contact_person_tel` VARCHAR(50)           DEFAULT NULL COMMENT '联系人电话',
    `legal_person`       VARCHAR(255)          DEFAULT NULL COMMENT '法人',
    `bank_name`          VARCHAR(255)          DEFAULT NULL COMMENT '供应商银行',
    `account_no`         VARCHAR(255)          DEFAULT NULL COMMENT '供应商账号',
    `tax_no`             VARCHAR(255)          DEFAULT NULL COMMENT '供应商税号',
    `enable`             TINYINT      NOT NULL COMMENT '是否启用',
    `sort`               INT          NOT NULL COMMENT '排序',
    `description`        VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`          VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`          VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`        INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`          TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='供应商表';

DROP TABLE IF EXISTS `db_vmd`.`tb_device`;
CREATE TABLE `db_vmd`.`tb_device`
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`               VARCHAR(20)  NOT NULL COMMENT '设备编码',
    `name`               VARCHAR(255) NOT NULL COMMENT '设备名称',
    `name_en`            VARCHAR(255)          DEFAULT NULL COMMENT '设备英文名称',
    `type`               VARCHAR(20)  NOT NULL COMMENT '设备类型',
    `ecu_type`           VARCHAR(20)  NOT NULL COMMENT 'ECU类型',
    `func_domain`        VARCHAR(20)  NOT NULL COMMENT '功能域',
    `node_type`          VARCHAR(100) NOT NULL COMMENT '节点类型',
    `ota_support`        VARCHAR(20)  NOT NULL COMMENT 'OTA支持类型',
    `link_config_source` VARCHAR(50)           DEFAULT NULL COMMENT '链路配置源',
    `link_flash_target`  VARCHAR(50)           DEFAULT NULL COMMENT '链路生效目标',
    `comm_protocol`      VARCHAR(50)           DEFAULT NULL COMMENT '通信协议',
    `flash_protocol`     VARCHAR(50)           DEFAULT NULL COMMENT '刷写协议',
    `can_tx_id`          VARCHAR(20)           DEFAULT NULL COMMENT 'CAN/CANFD总线发送标识',
    `can_rx_id`          VARCHAR(20)           DEFAULT NULL COMMENT 'CAN/CANFD总线接收标识',
    `ethernet_ip`        VARCHAR(20)           DEFAULT NULL COMMENT '以太网的业务IP',
    `doip_gateway_id`    VARCHAR(20)           DEFAULT NULL COMMENT 'DoIP协议网关标识',
    `doip_entity_id`     VARCHAR(20)           DEFAULT NULL COMMENT 'DoIP协议设备标识',
    `core`               TINYINT               DEFAULT 0 COMMENT '是否核心设备',
    `sort`               INT          NOT NULL COMMENT '排序',
    `description`        VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`          VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`          VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`        INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`          TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='设备信息表';


DROP TABLE IF EXISTS `db_vmd`.`tb_part`;
CREATE TABLE `db_vmd`.`tb_part`
(
    `id`                     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pn`                     VARCHAR(20)  NOT NULL COMMENT '零件号',
    `name`                   VARCHAR(255) NOT NULL COMMENT '零件中文名称',
    `name_en`                VARCHAR(255)          DEFAULT NULL COMMENT '零件英文名称',
    `type`                   VARCHAR(20)           DEFAULT NULL COMMENT '零件类型',
    `ffa`                    VARCHAR(20)           DEFAULT NULL COMMENT '零件分类',
    `status`                 VARCHAR(20)           DEFAULT NULL COMMENT '零件状态：PRODUCTION-量产，TRIAL-试生产，DISCONTINUE-停用',
    `digital_model`          VARCHAR(20)           DEFAULT NULL COMMENT '数字模型：NO-无，PART-零件级，PRODUCT-总成级',
    `unit`                   VARCHAR(10)           DEFAULT NULL COMMENT '单位：EA-个，KG-千克，ML-毫升，M2-平方米，M-米',
    `frame_part`             TINYINT               DEFAULT 0 COMMENT '是否是架构件',
    `nature_part`            TINYINT               DEFAULT 0 COMMENT '是否是本色件',
    `color_area`             VARCHAR(20)           DEFAULT NULL COMMENT '颜色区域：INTERNAL-内部，EXTERNAL-外部',
    `nature_pn`              VARCHAR(20)           DEFAULT NULL COMMENT '本色件零件号',
    `regulatory_part`        TINYINT               DEFAULT 0 COMMENT '是否是法规件',
    `key_part`               VARCHAR(20)           DEFAULT NULL COMMENT '关键程度：KEY-关键，MAJOR-主要，SIMPLE-普通',
    `accurately_traced`      TINYINT               DEFAULT 0 COMMENT '是否精准追溯',
    `aftersale_part`         TINYINT               DEFAULT 0 COMMENT '是否是配件',
    `standard_part_class`    VARCHAR(20)           DEFAULT NULL COMMENT '标准件分类',
    `wrench_type`            VARCHAR(20)           DEFAULT NULL COMMENT '扳拧形式',
    `rod_type`               VARCHAR(20)           DEFAULT NULL COMMENT '杆部形式',
    `head_shape`             VARCHAR(20)           DEFAULT NULL COMMENT '头部形状',
    `end_shape`              VARCHAR(20)           DEFAULT NULL COMMENT '末端形状',
    `washer`                 TINYINT               DEFAULT 0 COMMENT '是否带垫圈',
    `washer_type`            VARCHAR(20)           DEFAULT NULL COMMENT '垫圈类型',
    `diameter`               VARCHAR(20)           DEFAULT NULL COMMENT '直径',
    `length`                 VARCHAR(20)           DEFAULT NULL COMMENT '长度',
    `pitch`                  VARCHAR(20)           DEFAULT NULL COMMENT '螺距',
    `dental_form`            VARCHAR(20)           DEFAULT NULL COMMENT '牙型',
    `strength_grade`         VARCHAR(20)           DEFAULT NULL COMMENT '强度等级',
    `mechanical_property`    VARCHAR(20)           DEFAULT NULL COMMENT '机械性能',
    `surface_treatment`      VARCHAR(20)           DEFAULT NULL COMMENT '表面处理',
    `structure_character`    VARCHAR(20)           DEFAULT NULL COMMENT '结构特征',
    `ecu_form`               VARCHAR(20)           DEFAULT NULL COMMENT 'ECU形态',
    `ecu_type`               VARCHAR(20)           DEFAULT NULL COMMENT 'ECU类型',
    `designer`               VARCHAR(20)           DEFAULT NULL COMMENT '设计工程师',
    `designer_dept`          VARCHAR(20)           DEFAULT NULL COMMENT '设计工程师部门',
    `non_repair_reason`      VARCHAR(20)           DEFAULT NULL COMMENT '不作为备件原因',
    `color_repair`           TINYINT               DEFAULT 0 COMMENT '是否颜色件维修',
    `primer_repair`          TINYINT               DEFAULT 0 COMMENT '是否底漆件维修',
    `electrophoresis_repair` TINYINT               DEFAULT 0 COMMENT '是否电泳件维修',
    `production_code`        VARCHAR(20)           DEFAULT NULL COMMENT '对应生产件号',
    `spare_property`         VARCHAR(20)           DEFAULT NULL COMMENT '售后配件属性',
    `sale_note`              VARCHAR(20)           DEFAULT NULL COMMENT '售后备注',
    `first_production_date`  VARCHAR(20)           DEFAULT NULL COMMENT '首次投产时间',
    `initial_model`          VARCHAR(20)           DEFAULT NULL COMMENT '初始车型',
    `description`            VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`              VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`              VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`            INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`              TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`pn`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='零件信息表';
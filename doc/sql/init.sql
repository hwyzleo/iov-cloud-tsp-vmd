DROP TABLE IF EXISTS `db_vmd`.`tb_veh_basic_info`;
CREATE TABLE `db_vmd`.`tb_veh_basic_info`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `vin`               VARCHAR(20)  NOT NULL COMMENT '车架号',
    `manufacturer_code` VARCHAR(255) NOT NULL COMMENT '工厂代码',
    `brand_code`        VARCHAR(255) NOT NULL COMMENT '品牌代码',
    `platform_code`     VARCHAR(255) NOT NULL COMMENT '平台代码',
    `series_code`       VARCHAR(255) NOT NULL COMMENT '车系代码',
    `model_code`        VARCHAR(255) NOT NULL COMMENT '车型代码',
    `model_config_code` VARCHAR(255) NOT NULL COMMENT '车型配置代码',
    `description`       VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`         VARCHAR(64)           DEFAULT NULL COMMENT '创建者',
    `modify_time`       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`         VARCHAR(64)           DEFAULT NULL COMMENT '修改者',
    `row_version`       INT                   DEFAULT 1 COMMENT '记录版本',
    `row_valid`         TINYINT               DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`vin`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='车辆基础信息表';

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

DROP TABLE IF EXISTS `db_vmd`.`tb_veh_model_config`;
CREATE TABLE `db_vmd`.`tb_veh_model_config`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform_code` VARCHAR(255) NOT NULL COMMENT '平台代码',
    `series_code`   VARCHAR(255) NOT NULL COMMENT '车系代码',
    `model_code`    VARCHAR(255) NOT NULL COMMENT '车型代码',
    `code`          VARCHAR(255) NOT NULL COMMENT '车型配置代码',
    `name`          VARCHAR(255) NOT NULL COMMENT '车型配置名称',
    `name_en`       VARCHAR(255)          DEFAULT NULL COMMENT '车型配置英文名称',
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
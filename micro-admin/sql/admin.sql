CREATE TABLE sys_menu (
 `id`  BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
 `name` VARCHAR(256),
 `parent_id` BIGINT,
 `path` VARCHAR(1000),
 `component` VARCHAR(256),
 `icon` VARCHAR(100),
 `sort` INT,
 `visible` INT,
 `redirect` VARCHAR(100),
 `create_time` DATE DEFAULT NULL,
 `update_time` DATE DEFAULT NULL
) ENGINE=INNODB AUTO_INCREMENT 1000000 CHARSET=utf8 ;

CREATE TABLE sys_oauth_client (
  `client_id` VARCHAR(48) NOT NULL,
  `resource_ids` VARCHAR(256) DEFAULT NULL,
  `client_secret` VARCHAR(256) DEFAULT NULL,
  `scope` VARCHAR(256) DEFAULT NULL,
  `authorized_grant_types` VARCHAR(256) DEFAULT NULL,
  `web_server_redirect_uri` VARCHAR(256) DEFAULT NULL,
  `authorities` VARCHAR(256) DEFAULT NULL,
  `access_token_validity` INT(11) DEFAULT NULL,
  `refresh_token_validity` INT(11) DEFAULT NULL,
  `additional_information` VARCHAR(4096) DEFAULT NULL,
  `autoapprove` VARCHAR(256) DEFAULT NULL,
  `create_time` DATE DEFAULT NULL,
  `update_time` DATE DEFAULT NULL,
  PRIMARY KEY (`client_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE sys_permission (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `menu_id` BIGINT NOT NULL,
  `url_perm` VARCHAR(256) NOT NULL,
  `create_time` DATE,
  `update_time`	DATE,
  `create_by` BIGINT,
  `update_by` BIGINT
)ENGINE=INNODB AUTO_INCREMENT 2000000 CHARSET=utf8 ;

CREATE TABLE sys_role (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `code` VARCHAR(256) NOT NULL,
  `sort` INT NOT NULL,
  `status` INT NOT NULL,
  `deleted` INT DEFAULT 0,
  `create_time` DATE,
  `update_time` DATE,
  `create_by` BIGINT,
  `update-by` BIGINT
)ENGINE=INNODB AUTO_INCREMENT 3000000 CHARSET=utf8;

CREATE TABLE sys_role_menu (
  `role_id` BIGINT NOT NULL,
  `menu_id` BIGINT NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE sys_user_role (
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE sys_role_permission (
  `permission_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE sys_user (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(256) NOT NULL,
  `nickname` VARCHAR(256),
  `gender` INT NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `avatar` VARCHAR(1000) DEFAULT NULL,
  `mobile` VARCHAR(60),
  `status` INT NOT NULL,
  `email` VARCHAR(256),
  `deleted` INT,
  `create_time` DATE,
  `update_time` DATE,
  `create_by` BIGINT,
  `update_by` BIGINT
)ENGINE=INNODB AUTO_INCREMENT 4000000 CHARSET=utf8;
CREATE TABLE user
(
    id       BIGINT       not null primary key auto_increment COMMENT 'user id',
    username VARCHAR(128) not null COMMENT '用户名',
    password VARCHAR(128) not null COMMENT '密码',
    balance  DOUBLE       not null COMMENT '账户余额'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_username ON user (username);


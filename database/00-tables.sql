create table t_sequence(
  name  varchar(100) primary key             comment '序列名称',
  code  varchar(5) not null default '00000'  comment '序列编号（用于区分不同序列种类）',
  value bigint     not null default 0        comment '当前值',
  min   bigint     not null default 0        comment '最小值',
  max   bigint     not null default 99999999 comment '最大值，当 value 达到最大值时重新从 min 开始',
  step  int        not null default 1000     comment '步长，每次取序列时缓存多少，步长越大，数据库访问频率越低'
);

CREATE TABLE `t_hym_user`
(
  `hym_user_id`      bigint        primary key     comment '主键',
  `user_name`        varchar(50)   unique not null comment '用户名',
  `password`         varchar(100)                  comment '加盐密码',
  `mobile`           varchar(20)   unique          comment '手机号',
  `email`            varchar(50)   unique          comment '邮箱',
  `avatar`           varchar(255)                  comment '头像',
  `status`           tinyint       default 1       comment '状态，1=正常，2=禁用',
  `last_login_time`  datetime                      comment '最后登录时间',
  `last_login_ip`    varchar(20)                   comment '最后登录IP',
  `created_at`       datetime default current_timestamp() not null comment '创建时间',
  `updated_at`       datetime default current_timestamp() not null on update current_timestamp() comment '修改时间'
) comment '用户表';
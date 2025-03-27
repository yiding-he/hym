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
  `hym_user_id`       bigint        primary key     comment '主键',
  `user_name`         varchar(50)   unique not null comment '用户名',
  `password`          varchar(100)                  comment '加盐密码',
  `mobile`            varchar(20)   unique          comment '手机号',
  `email`             varchar(50)   unique          comment '邮箱',
  `avatar`            varchar(255)                  comment '头像',
  `status`            tinyint default 1             comment '状态，1=正常，2=禁用',
  `role_id`           bigint                        comment '角色ID',
  `last_login_time`   datetime                      comment '最后登录时间',
  `last_login_ip`     varchar(20)                   comment '最后登录IP',
  `created_at`        datetime default current_timestamp() not null comment '创建时间',
  `updated_at`        datetime default current_timestamp() not null on update current_timestamp() comment '修改时间'
) comment '用户表';


CREATE TABLE `t_hym_function_cat`
(
  `hym_function_cat_id` bigint        primary key     comment '主键',
  `category_name`       varchar(50)   unique not null comment '分类名称',
  `visible`             tinyint default 1            comment '是否可见，0=不可见，1=可见',
  `created_at`          datetime default current_timestamp() not null comment '创建时间',
  `updated_at`          datetime default current_timestamp() not null on update current_timestamp() comment '修改时间'
) comment '功能分类表';

CREATE TABLE `t_hym_function`
(
  `hym_function_id` bigint        primary key     comment '主键',
  `function_name`   varchar(50)   not null comment '功能名称',
  `function_code`   varchar(50)   not null comment '功能代码',
  `page_name`       varchar(50)            comment '页面名称',
  `category_id`     bigint        not null comment '功能类别ID',
  `created_at`      datetime default current_timestamp() not null comment '创建时间',
  `updated_at`      datetime default current_timestamp() not null on update current_timestamp() comment '修改时间'
) comment '功能表';


CREATE TABLE `t_hym_role`
(
  `hym_role_id`       bigint        primary key     comment '主键',
  `role_name`         varchar(50)   unique not null comment '角色名',
  `role_description`  varchar(200)                comment '角色描述',
  `created_at`        datetime default current_timestamp() not null comment '创建时间',
  `updated_at`        datetime default current_timestamp() not null on update current_timestamp() comment '修改时间'
) comment '角色表';

CREATE TABLE `t_hym_role_function`
(
  `role_id`     bigint not null comment '角色ID',
  `function_id` bigint not null comment '功能ID',
  `created_at`  datetime default current_timestamp() not null comment '创建时间',
  `updated_at`  datetime default current_timestamp() not null on update current_timestamp() comment '修改时间',
  primary key (`role_id`, `function_id`)
) comment '角色功能表';


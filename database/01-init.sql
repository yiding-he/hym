#
admin/admin123
insert into t_hym_user
set hym_user_id=1,
    user_name='admin',
    password='$2a$10$xUem34teQFEXtXG.TxMKsOno7hIpL1afCfyTqoM1l5R5VGRd9cgWG';

INSERT INTO `t_hym_function_cat`
  (`hym_function_cat_id`, `category_name`, `created_at`, `updated_at`)
VALUES (1, '系统管理', NOW(), NOW()),
       (2, '系统监控', NOW(), NOW());

INSERT INTO `t_hym_function`
(`hym_function_id`, `function_name`, `page_name`,
 `category_id`, `created_at`, `updated_at`)
VALUES (1, '用户管理', 'UserManagementPage', 1, NOW(), NOW()),
       (2, '角色管理', 'RoleManagementPage', 1, NOW(), NOW()),
       (3, '权限管理', 'PermissionManagementPage', 1, NOW(), NOW()),
       (4, '菜单管理', 'MenuManagementPage', 1, NOW(), NOW()),
       (5, '部门管理', 'DepartmentManagementPage', 1, NOW(), NOW()),
       (6, '在线用户', 'OnlineUserPage', 2, NOW(), NOW()),
       (7, '操作日志', 'OperationLogPage', 2, NOW(), NOW()),
       (8, '登录日志', 'LoginLogPage', 2, NOW(), NOW()),
       (9, '异常日志', 'ExceptionLogPage', 2, NOW(), NOW()),
       (10, '定时任务', 'ScheduledTaskPage', 2, NOW(), NOW());

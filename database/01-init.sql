#
# admin/admin123

insert into t_hym_user
set hym_user_id=1,
    user_name='admin',
    password='$2a$10$xUem34teQFEXtXG.TxMKsOno7hIpL1afCfyTqoM1l5R5VGRd9cgWG';

TRUNCATE TABLE `t_hym_function_cat`;
INSERT INTO `t_hym_function_cat`
  (`hym_function_cat_id`, `category_name`, `visible`, `created_at`, `updated_at`)
VALUES (0, '后台功能', 0, NOW(), NOW()),
       (1, '系统管理', 1, NOW(), NOW()),
       (2, '系统监控', 1, NOW(), NOW());

TRUNCATE TABLE `t_hym_function`;
INSERT INTO `t_hym_function`
(`hym_function_id`, `function_name`, `function_code`, `page_name`,
 `category_id`, `created_at`, `updated_at`)
VALUES (1,  '用户管理', 'user_management',       'UserManagementPage', 1, NOW(), NOW()),
       (2,  '角色管理', 'role_management',       'RoleManagementPage', 1, NOW(), NOW()),
       (3,  '权限管理', 'permission_management', 'PermissionManagementPage', 1, NOW(), NOW()),
       (4,  '菜单管理', 'menu_management',       'MenuManagementPage', 1, NOW(), NOW()),
       (5,  '部门管理', 'department_management', 'DepartmentManagementPage', 1, NOW(), NOW()),
       (6,  '在线用户', 'online_user',           'OnlineUserPage', 2, NOW(), NOW()),
       (7,  '操作日志', 'operation_log',         'OperationLogPage', 2, NOW(), NOW()),
       (8,  '登录日志', 'login_log',             'LoginLogPage', 2, NOW(), NOW()),
       (9,  '异常日志', 'exception_log',         'ExceptionLogPage', 2, NOW(), NOW()),
       (10, '定时任务', 'scheduled_task',        'ScheduledTaskPage', 2, NOW(), NOW());

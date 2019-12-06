INSERT INTO rz_sys_user (id, `createTime`, `updateTime`, `createBy`, `updateBy`, name, password, salt, state, username) VALUES('7888579381403590656', '2019-11-29 10:21:40.516', '2019-11-29 10:21:40.516', 'jjh', 'jjh', 'admin', '04ee31ab2352b1fb4548b2c41cd7da36695002c2b7f2eb917a69f1bba4616366', '1574994100513', 0, 'admin');
INSERT INTO rz_sys_role (id, `createTime`, `updateTime`, `createBy`, `updateBy`, code, name, status) VALUES('1', NULL, NULL, NULL, NULL, 'admin', '管理员', 0);
INSERT INTO rz_sys_role (id, `createTime`, `updateTime`, `createBy`, `updateBy`, code, name, status) VALUES('2', NULL, NULL, NULL, NULL, 'vip', 'VIP会员', 0);
INSERT INTO rz_sys_role (id, `createTime`, `updateTime`, `createBy`, `updateBy`, code, name, status) VALUES('3', NULL, NULL, NULL, NULL, 'test', 'test', 1);
INSERT INTO rz_sys_permission (id, `createTime`, `updateTime`, `createBy`, `updateBy`, code, name, `parentId`, `parentIds`, `resourceType`, status, url) VALUES('1', NULL, NULL, NULL, NULL, 'system:sysUser:view', '用户管理', 0, '0/', 'menu', 0, 'sysUser/userList');
INSERT INTO rz_sys_permission (id, `createTime`, `updateTime`, `createBy`, `updateBy`, code, name, `parentId`, `parentIds`, `resourceType`, status, url) VALUES('2', NULL, NULL, NULL, NULL, 'system:sysUser:add', '用户添加', 1, '0/1', 'button', 0, 'sysUser/userAdd');
INSERT INTO rz_sys_permission (id, `createTime`, `updateTime`, `createBy`, `updateBy`, code, name, `parentId`, `parentIds`, `resourceType`, status, url) VALUES('3', NULL, NULL, NULL, NULL, 'system:sysUser:del', '用户删除', 1, '0/1', 'button', 0, 'sysUser/userDel');
INSERT INTO rz_sys_permission (id, `createTime`, `updateTime`, `createBy`, `updateBy`, code, name, `parentId`, `parentIds`, `resourceType`, status, url) VALUES('4', NULL, NULL, NULL, NULL, 'system:sysUser:list', '用户列表', 1, '0/1', 'button', 0, 'sysUser/userDel');
INSERT INTO rz_sys_user_role_mapping (id, `createTime`, `updateTime`, `createBy`, `updateBy`, `roleId`, `userId`) VALUES('1', NULL, NULL, NULL, NULL, '1', '7888579381403590656');
INSERT INTO rz_sys_role_permission_mapping (id, `createTime`, `updateTime`, `createBy`, `updateBy`, `permissionId`, `roleId`) VALUES('1', NULL, NULL, NULL, NULL, '1', '1');
INSERT INTO rz_sys_role_permission_mapping (id, `createTime`, `updateTime`, `createBy`, `updateBy`, `permissionId`, `roleId`) VALUES('2', NULL, NULL, NULL, NULL, '2', '1');
INSERT INTO rz_sys_role_permission_mapping (id, `createTime`, `updateTime`, `createBy`, `updateBy`, `permissionId`, `roleId`) VALUES('3', NULL, NULL, NULL, NULL, '3', '2');
INSERT INTO rz_sys_role_permission_mapping (id, `createTime`, `updateTime`, `createBy`, `updateBy`, `permissionId`, `roleId`) VALUES('4', NULL, NULL, NULL, NULL, '4', '1');


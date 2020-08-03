<?php

use think\facade\Route;

Route::post(':version/admin/register',':version.Admin/adminRegister');
Route::get(':version/test', ':version.Admin/test');

//用户相关路由
Route::get(':version/user', ':version.User/index');
Route::get(':version/user/:id', ':version.User/read');
Route::post(':version/user', ':version.User/save');
Route::put(':version/user/:id', ':version.User/update');
Route::delete(':version/user/:id', ':version.User/delete');
Route::put(':version/auditUser/[:id]', ':version.User/changeAuditStatus');

//培养联系人及入党推荐人
Route::get(':version/contacts', ':version.Contacts/index');
Route::post(':version/assignContacts', ':version.Contacts/assignContacts');
Route::post(':version/assignRecommands', ':version.Contacts/assignRecommands');

//阶段相关路由
Route::get(':version/stage', ':version.Stage/index');
Route::get(':version/stage/:id', ':version.Stage/read');
Route::post(':version/stage', ':version.Stage/save');
Route::put(':version/stage/:id', ':version.Stage/update');
Route::delete(':version/stage/:id', ':version.Stage/delete');

//用户当前状态
Route::put(':version/state',':version.UserState/update');
Route::put(':version/state/next_mission',':version.UserState/next_mission');
Route::put(':version/state/resetState',':version.UserState/resetState');
Route::post(':version/state/audit',':version.UserState/audit');

//任务相关路由
Route::get(':version/task', ':version.Task/index');
Route::get(':version/task/:id', ':version.Task/read');
Route::post(':version/task', ':version.Task/save');
Route::put(':version/task/:id', ':version.Task/update');
Route::delete(':version/task/:id', ':version.Task/delete');

//材料审核
Route::get(':version/taskDetail', ':version.Detail/taskDetail');
Route::post(':version/taskAudit', ':version.Detail/audit');
Route::post(':version/uploadDetailDoc', ':version.Detail/uploadDetailDoc');

//成绩相关
Route::post(':version/score/record', ':version.Score/exam_score');

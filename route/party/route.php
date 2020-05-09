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

//阶段相关路由
Route::get(':version/stage', ':version.Stage/index');
Route::get(':version/stage/:id', ':version.Stage/read');
Route::post(':version/stage', ':version.Stage/save');
Route::put(':version/stage/:id', ':version.Stage/update');
Route::delete(':version/stage/:id', ':version.Stage/delete');

//用户当前状态
Route::put(':version/state',':version.UserState/update');

//任务相关路由
Route::get(':version/task', ':version.Task/index');
Route::get(':version/task/:id', ':version.Task/read');
Route::post(':version/task', ':version.Task/save');
Route::put(':version/task/:id', ':version.Task/update');
Route::delete(':version/task/:id', ':version.Task/delete');

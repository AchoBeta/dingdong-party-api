<?php

use think\facade\Route;

Route::post(':version/admin/register',':version.Admin/adminRegister');
Route::get(':version/test', ':version.Admin/test');
Route::get(':version/user', ':version.User/index');
Route::get(':version/user/:id', ':version.User/read');
Route::post(':version/user', ':version.User/save');
Route::put(':version/user/:id', ':version.User/update');
Route::delete(':version/user/:id', ':version.User/delete');
<?php

use think\facade\Route;

Route::post(':version/admin/register',':version.Admin/adminRegister');
Route::get(':version/test', ':version.Admin/test');
Route::get(':version/user', ':version.User/index');
Route::get(':version/user/:id', ':version.User/read');
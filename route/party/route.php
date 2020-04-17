<?php

use think\facade\Route;

Route::post(':version/admin/register',':version.Admin/adminRegister');
Route::get(':version/test', ':version.Admin/test');
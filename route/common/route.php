<?php
use think\facade\Route;
Route::pattern([
    'name' => '\w+',
    'id'   => '\-?\d+',
]);
Route::get("hello/[:name]", 'v1.Banner/hello');

Route::post(':version/token/user',':version.Token/getToken');
Route::post(':version/token/admin',':version.Token/adminToken');
Route::post(':version/token/logout',':version.Token/logout');







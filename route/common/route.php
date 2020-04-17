<?php
use think\facade\Route;
Route::pattern([
    'name' => '\w+',
    'id'   => '\-?\d+',
]);
Route::get("hello/[:name]", 'v1.Banner/hello');

Route::post('token/user','Token/getToken');
Route::post('token/admin','Token/adminToken');
Route::post('token/logout','Token/logout');







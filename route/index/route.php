<?php

use think\facade\Route;

Route::get("hello/[:name]",'index/hello');
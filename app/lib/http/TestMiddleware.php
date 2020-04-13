<?php


namespace app\lib\http;


class TestMiddleware
{
    public function handle($request,\Closure $next)
    {
        echo 123;
        return $next($request);
    }
}
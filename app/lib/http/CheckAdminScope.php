<?php


namespace app\lib\http;


use app\api\service\Token as TokenService;
use app\lib\enum\ScopeEnum;
use app\lib\exception\ForbiddenException;
use app\lib\exception\TokenException;

class CheckAdminScope
{
    public function handle($request,\Closure $next)
    {
        $scope = TokenService::getCurrentTokenVar('scope');
        if($scope)
        {
            if($scope>=ScopeEnum::Admin)
            {
                return $next($request);
            }else{
                throw new ForbiddenException();
            }
        }else{
            throw new TokenException();
        }
    }
}
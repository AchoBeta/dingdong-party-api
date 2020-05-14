<?php


namespace app\lib\enum;


class MaterialStatusEnum
{
    const PASS_BY_ADMIN = 2;
    const PASS_BY_AI = 1;
    const NO_PASS_BY_AI = -1;
    const NO_PASS_BY_ADMIN = -2;
    const NO_CHECKED = 0;
}
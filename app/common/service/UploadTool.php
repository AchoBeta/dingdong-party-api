<?php


namespace app\common\service;


use app\common\model\Image;
use app\common\model\Upload;
use app\lib\exception\UploadException;
use think\exception\ValidateException;

class UploadTool
{
    public function image_upload($files)
    {
        try {
//            $validate = new FileValidate();
//            $result = $validate->scene('image')->check($files);
//            $result = $validate->goCheck('image');
//            if (!$result)
//            {
//                throw new UploadException(['msg'=>$validate->getError(),'errorCode'=>70000]);
//            }
            $savename = [];
            foreach ($files['image'] as $k => $file) {
                $absolute = \think\facade\Filesystem::disk('image')->putFile( '', $file);
                $savename[$k] = [
                    'savepath' => str_replace('\\','/',$absolute),
                    'url' => str_replace('\\','/',$absolute),
                    'file_mime' => $file->getOriginalMime(),
                    'file_name' => $file->getOriginalName(),
                    'ext' => $file->getOriginalExtension(),
                    'size' => round($file->getSize()/1024/1024,2),
                    'ip'=>request()->ip()
                ];
            }
            $upload = new Image();
            $res = $upload->saveAll($savename);
            return $res->toArray();
        } catch (ValidateException $e) {
            throw new UploadException(['msg'=>$e->getMessage()]);
        }
    }
    public function file_upload($files)
    {
        try {
//            $validate = new FileValidate();
//            $result = $validate->scene('common')->check($files);
//            if (!$result)
//            {
//                throw new UploadException(['msg'=>$validate->getError(),'errorCode'=>70000]);
//            }
            $savename = [];
            foreach ($files['file'] as $k => $file) {
                $absolute = \think\facade\Filesystem::disk('public')->putFile( '', $file);
                $savename[$k] = [
                    'savepath' => str_replace('\\','/',$absolute),
                    'url' => str_replace('\\','/',$absolute),
                    'file_mime' => $file->getOriginalMime(),
                    'file_name' => $file->getOriginalName(),
                    'ext' => $file->getOriginalExtension(),
                    'size' => round($file->getSize()/1024/1024,2),
                    'ip'=>request()->ip()
                ];
            }
            $upload = new Upload();
            $res = $upload->saveAll($savename);
            return $res->toArray();
        } catch (ValidateException $e) {
            throw new UploadException(['msg'=>$e->getMessage()]);
        }
    }
}
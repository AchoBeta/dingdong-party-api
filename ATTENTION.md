# 开发注意事项
## 1. 环境要求
+ mysql 5.5+
+ jdk 1.8+
+ maven 3.3+
+ redis 3.2+

## 2. 提交代码
+ 远端仓库有两个分支
    + master：线上环境，稳定版本
    + dev：开发环境：新提交的代码都 pull request 到此分支

## 3. 开发配置
每个模块都需要两套环境
+ 线上环境 - `application-pro.yml`
+ 生产环境 - `application-dev.yml`
+ 总环境 - `application.yml`

总环境用于切换，生产环境带有 swagger，日志打印，线上环境只打印错误日志

## 4. 接口设计
接口完全按照 restful 规范

### 协议
HTTP/HTTPS 1.1

### 端点
基本要求
> 以 /api/<project>/<version> 开头，例如 /api/log/v1

> URL 路径不能使用大写，单词如果需要分隔，统一使用短横线

> 路径禁止携带表示请求内容类型的后缀，应放到请求 Header 中

端点形式：
```
# 基于资源，其中 <resources>、<subresources> 均使用复数，<resource_id> 是Path Parameter：
/api/<resources>
/api/<resources>/<resource_id>
/api/<resources>/<resource_id>/<subresources>
/api/<resources>/<resource_id>/<subresources>/<subresource_id>

# 基于资源的表述，其中 <representation> 为名词，例如 info、status、progress、summary、result 等，仅用于 GET：
/api/<representation>
/api/<resources>/<representation>
/api/<resources>/<resource_id>/<representation>
/api/<resources>/<resource_id>/<subresources>/<representation>
/api/<resources>/<resource_id>/<subresources>/<subresource_id>/<representation>

# 基于资源的操作，其中 <action> 为动词，例如 search、upload、start、stop、trigger、analyze、flush 等，仅用于 POST：
/api/<action>
/api/<resources>/<action>
/api/<resources>/<resource_id>/<action>
/api/<resources>/<resource_id>/<subresources>/<action>
/api/<resources>/<resource_id>/<subresources>/<subresource_id>/<action>
```

### 请求方法
+ GET
```
/api/<resources>：获取资源列表
/api/<resources>/<resource_id>：获取单个资源
/api/<resources>/<resource_id>/<subresources>：获取子资源列表
/api/<resources>/<resource_id>/<subresources>/<subresource_id>：获取单个子资源
```

+ POST
```
/api/<resources>：创建资源（返回<resource_id>）
/api/<resources>/<resource_id>/<subresources>：创建子资源（返回<subresource_id>）
```

+ PUT
```
/api/<resources>：更新多个资源
/api/<resources>/<resource_id>：更新单个资源
/api/<resources>/<resource_id>/<subresources>：更新多个子资源
/api/<resources>/<resource_id>/<subresources>/<subresource_id>：更新单个子资源
```

+ DELETE
```
/api/<resources>：删除多个资源
/api/<resources>/<resource_id>：删除单个资源
/api/<resources>/<resource_id>/<subresources>：删除多个子资源
/api/<resources>/<resource_id>/<subresources>/<subresource_id>：删除单个子资源
```



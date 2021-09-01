## 叮咚党建（分布式架构）
### 结构解释
+ common：公共包
    + common-utils：公共工具包
        + 接口返回封装
        + 异常输出
        + http 请求（请求微信接口）
        + md5 加密（密码加密保存数据库）
        + 获取随机数
    + service-base：基础配置
        + mybatis-plus 分页配置
        + redis 配置
        + swagger 配置
        + 自定义异常处理
        + 全局异常处理封装
        + mybatis-plus 自动注入
        + 格式化字符串
<br>
+ infrastructure：基础服务
    + apigateway：api 网关
        + 拦截没 token 请求
        + 开放 login 请求
<br>
+ service：业务包
    + service-eureka：注册中心集群（两个，主从结构）
        + service-eureka-host：主机
        + service-eureka-slave：从机，当主机宕机时顶替
    + service-user：用户业务
    + service-admin：
        + 管理员业务
        + security 身份认证，权限拦截，同时只能登录一次

---

## 运行流程：
1. 启动 redis
2. 启动注册中心
3. 启动相关包
4. 启动网关

---

## 访问地址
### 网关地址
localhost:81/api/dingdong-party/v1

网关 swagger: localhost:81/api/dingdong-party/v1/doc.html

### 注册中心
localhost:18001/eureka 或 localhost:18002/eureka

访问中心地址：localhost:18001/

### 活动管理
`localhost:8001/`

### 用户管理
`localhost:8002/`

### 后台管理
`localhost:8003/`


### swagger 地址
[http://localhost:port/api/dingdong-party/v1/doc.html]()



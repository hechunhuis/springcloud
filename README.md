# 微服务练习项目

## 运行环境
Java：8及以上 <br/>
Maven：3.6.1及以上 <br/>
MySQL：5.6及以上
## 技术版本选型
SpringCloud：Hoxton.SR1 <br/>
SpringBoot：2.2.2.RELEASE <br/>

## 编译运行
```shell
cd ./springcloud
mvn install 
mvn idea:idea
```

## 修改配置文件
1. 修改【cloud-provider-payment-8001】工程下application.yml中数据库连接配置
2. sql信息后续根据实体类自动创建(待完善)

## 模块介绍
### 公共模块【cloud-api-commons】
### Eureka注册中心【cloud-eureka-server-7001】
### 消费者-订单模块【cloud-consumer-oder-80】
### 生产者-支付模块【cloud-provider-payment-8001】

## APIPost接口文档
https://console-docs.apipost.cn/preview/5c9e12fd11eb6342/bb1f08f7f0c93060

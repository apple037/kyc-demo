# 交易所架構說明

## 模塊說明
1. cloud
* 提供SpringCloud微服務注冊中心功能，為基礎模塊，必須部署
* 依賴服務：無
2. ucenter-api
* 提供用戶相關的接口（如登錄、注冊、資產列表）,該模塊為基礎為基礎模塊，必須部署
* 依賴服務：mysql,kafka,redis,mongodb,短信接口，郵箱賬號
3. otc-api
* 提供場外交易功能接口，沒有場外交易的可以不部署
* 依賴服務：mysql,redis,mongodb,短信接口
4. exchange-api
* 提供幣幣交易接口，沒有幣幣交易的項目可以不部署
* 依賴服務：mysql,redis,mongodb,kafka
5. chat
* 提供實時通訊接口，基礎模塊，需要部署
* 依賴服務：mysql,redis,mongodb
6. admin
* 提供管理後台的所有服務接口，必須部署
* 依賴服務：mysql,redis,mongodb
7. wallet
* 提供充幣、提幣、獲取地址等錢包服務，為基礎模塊，必須部署
* 依賴服務：mysql,mongodb,kafka,cloud
8. market
* 提供幣種價格、k線、實時成交等接口服務，場外交易不需要部署
* 依賴服務：mysql,redis,mongodb,kafka,cloud
9. exchange
* 提供撮合交易服務，場外交易不需要部署
* 依賴服務：mysql,mongodb,kafka

## 佈署

1. 推薦配置 由it新增 請依照實際情況調整
  * GKE 一台 (framework/nginx/redis...) 工程師透過jenkins佈署
  * GCE 一台 (kafka) it安裝  版本 2.2.0
  * GCE 一台 (mongo) it安裝  版本 3.6.3
  * GCE 一台 (rpc) 工程師提供jar包給it啟動

2. 匯入prod_exchange.sql

3. coinpay-framework 新增環境配置文件

  * 各個service新增Dokerfile-{env} (deployment.yaml, service.yaml 沿用)
  * 各個service新增 {env}/application.properties (連接db, kafka...url修改)
  * Jenkins 新增分頁 複製舊專案job 並修改相關配置

4. rpc-wallet/erc-token 新增環境配置文件
  * 新增usdt-{env}/application.properties, usdt-{env}/logback-spring.xml
    (連接db, kafka...url修改 / eureka.instance.instance-id修改 / eureka.instance.ip-address修改)
  * 本地打包後請it上傳到rpc主機

5. 創建提幣錢包地址
  * 啟動後打 /address/withdraw 取得提幣地址json檔案名
  * 充值少量eth到提幣地址作為燃料費 (0.5eth)
  * 修改erc-token/application.properties 提幣地址檔案名 (coin.withdraw-wallet)
  * 重新打包後請it上傳到rpc主機

6. 前台創建帳號並測試相關功能
  * 正確產生會員錢包
  * 外部錢包轉入 (鏈上交易)
  * 交易所錢包轉出 (鏈上交易, 正確結果為從提幣地址轉出到目標地址)


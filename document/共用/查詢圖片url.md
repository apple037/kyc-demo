# 查詢圖片url

### Request
- Method **GET**
- URL: ```/app/find/file```
```
fileIds:1,2
```

#### 請求參數說明
|參數名稱(英文)|參數名稱(中文)|參數型態|參數說明|是否必填|
|:--|:--|:--|:--|:--|
|fileIds|檔案id|String||是|



### Success Response
```json
{
  "code": "G_0000",
  "message": "SUCCESS",
  "data": [
    {
      "id": 1,
      "url": "https://kyc-backend.snailloop.com/bucket/userdata/img/clock3.jpg"
    },
    {
      "id": 2,
      "url": "https://kyc-backend.snailloop.com/bucket/userdata/img/explode.jpg"
    }
  ]
}
```
### 參數說明
|參數名稱(英文)|參數名稱(中文)|參數型態|參數說明|
|:--|:--|:--|:--|
|id|圖片id|String||
|url|圖片url|String||

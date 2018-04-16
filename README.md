# [unofficial] qcloud-api
生成腾讯云 API 请求参数的辅助类

### 使用方法
- 初始化
```java
RequestBuilder requestBuilder = new RequestBuilder("AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA",
    "Gu5t9xGARNpq86cd98joQYCN3Cozk1qA",
    "gz",
    "TextTranslate",
    new tmt());
```

- 添加模块参数
```java
requestBuilder.GET()
    .addModuleParameter(tmt.Parameter.PROJECT_ID, "0")
    .addModuleParameter(tmt.Parameter.SOURCE, "zh")
    .addModuleParameter(tmt.Parameter.SOURCE_TEXT, "你好")
    .addModuleParameter(tmt.Parameter.TARGET, "en");

//可直接覆盖
requestBuilder.addModuleParameter(tmt.Parameter.SOURCE, "en")
    .addModuleParameter(tmt.Parameter.SOURCE_TEXT, "hello")
    .addModuleParameter(tmt.Parameter.TARGET, "zh");
```

- 生成请求参数
```java
requestBuilder.getRequestParameter();
// 或者只生成签名串
requestBuilder.getSignature();
```
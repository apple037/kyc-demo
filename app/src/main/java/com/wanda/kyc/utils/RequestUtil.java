package com.wanda.kyc.utils;


import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RequestUtil {
    public static final MediaType JSON_HEADER = MediaType.parse("application/json; charset=utf-8");
    // 請求超時設定
    private static final int CONN_TIMEOUT = 30;

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";


    /**
     * 發送Get請求
     * @param url
     * @return
     * @throws IOException
     */
    public static String doGet(String url) throws IOException {
        return doGet(url, null);
    }

    /**
     * 發送帶Header的Get請求
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> headers) throws IOException {
        try {
            Request request;
            if (headers == null || headers.isEmpty()) {
                request = new Request.Builder()
                        .url(url)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .headers(Headers.of(headers))
                        .build();
            }
            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS).
                    readTimeout(CONN_TIMEOUT, TimeUnit.SECONDS).build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.trace("doGet-response.body:[{}]", responseBody);
            return responseBody;
        } catch (Exception e) {
            log.warn("doGet-err:[{}]", e.getMessage());
            throw e;
        }
    }

    /**
     * 發送post請求 (指定回傳資料格式為json、編碼為utf-8)
     * @param url
     * @param paras
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String paras) throws IOException {
        return doPost(url, paras, JSON_HEADER);
    }

    /**
     * 發送post請求 (指定回傳資料格式為json、編碼為utf-8, 並可設置timeOut)
     * @param url
     * @param paras
     * @param connectTimeOut 連接時間限制(單位:秒)
     * @param readTimeOut    收到回傳訊息時間限制(單位:秒)
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String paras, int connectTimeOut, int readTimeOut) throws IOException {
        return doPost(url, paras, null, JSON_HEADER, connectTimeOut, readTimeOut);
    }

    /**
     * 發送post請求 (帶Header,指定回傳資料格式為json、編碼為utf-8)
     * @param url
     * @param paras
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String paras, Map<String, String> headers) throws IOException {
        return doPost(url, paras, headers, JSON_HEADER, CONN_TIMEOUT, CONN_TIMEOUT);
    }

    /**
     * 發送post請求(預設timeOut)
     * @param url
     * @param paras
     * @param contentType
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String paras, MediaType contentType) throws IOException {
        return doPost(url, paras, null, contentType, CONN_TIMEOUT, CONN_TIMEOUT);
    }

    /**
     * 發送Post請求(可設置timeOut)
     * @param url
     * @param paras
     * @param headers
     * @param contentType
     * @param connectTimeOut 連接時間限制(單位:秒)
     * @param readTimeOut    收到回傳訊息時間限制(單位:秒)
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String paras, Map<String, String> headers, MediaType contentType, int connectTimeOut, int readTimeOut) throws IOException {
        try {
            log.trace("url:[{}],paras:[{}]", url, paras);
            RequestBody requestBody = RequestBody.create(contentType, paras);
            log.trace("doPost-paras:[{}]", paras);
            Request request;
            if (headers == null || headers.isEmpty()) {
                request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .headers(Headers.of(headers))
                        .post(requestBody)
                        .build();
            }
            log.trace("doPost-request:[{}]", request);
            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(connectTimeOut, TimeUnit.SECONDS).
                    readTimeout(readTimeOut, TimeUnit.SECONDS).build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.trace("doPost-response.body:[{}]", responseBody);
            return responseBody;
        } catch (Exception e) {
            log.error("doPost-err:[{}]", e.getMessage());
            throw e;
        }
    }

}

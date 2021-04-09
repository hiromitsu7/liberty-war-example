package com.example.servlet;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleServletIT {

    private static Logger logger = LoggerFactory.getLogger(ExampleServletIT.class);

    @Test
    public void testDoGet() {
        String hostname = System.getProperty("liberty.hostname", "localhost");
        String port = System.getProperty("liberty.http.port", "9080");
        String url = "http://" + hostname + ":" + port + "/liberty-war-example/";

        // デフォルト設定でHttpClientインスタンスを作成
        HttpClient client = HttpClient.newHttpClient();

        // ビルダーを使用してHttpRequestインスタンスを作成
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + "ExampleServlet")).build();

        try {
            // リクエストを送信
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // レスポンスボディを出力
            logger.info(response.body());

            assertNotNull(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

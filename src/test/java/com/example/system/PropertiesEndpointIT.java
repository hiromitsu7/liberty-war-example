package com.example.system;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PropertiesEndpointIT {

    private SSLContext sslContext;
    private X509TrustManager trustManager;

    @Test
    public void HTTPで通信できること() throws IOException {
        String url = "http://myhost.example.com:9080/liberty-war-example/system/properties";
        getHttp(url);
    }

    @Test
    public void HTTPSで通信できること() throws GeneralSecurityException, IOException {
        String keystorePath = "target/liberty/wlp/usr/servers/defaultServer/resources/security/key.p12";
        String keystorePassword = "yourPassword";
        String url = "https://myhost.example.com:9443/liberty-war-example/system/properties";
        getHttps(keystorePath, keystorePassword, url);
    }

    @Test(expected = SSLPeerUnverifiedException.class)
    public void ホスト名が異なる場合にHTTPSで通信できないこと() throws GeneralSecurityException, IOException {
        String keystorePath = "target/liberty/wlp/usr/servers/defaultServer/resources/security/key.p12";
        String keystorePassword = "yourPassword";
        String url = "https://localhost:9443/liberty-war-example/system/properties";
        getHttps(keystorePath, keystorePassword, url);
        fail("error");
    }

    private void getHttp(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            String actual = response.body().string();
            System.out.println(actual);
            assertTrue(actual.length() > 0);
        }
    }

    private void getHttps(String keystorePath, String keystorePassword, String url)
            throws GeneralSecurityException, IOException {
        createSSLContextAndTrustManager(keystorePath, keystorePassword);

        OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                .build();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            String actual = response.body().string();
            System.out.println(actual);
            assertTrue(actual.length() > 0);
        }

    }

    private void createSSLContextAndTrustManager(String keystoreFile, String password)
            throws GeneralSecurityException, IOException {
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        try (InputStream in = new FileInputStream(keystoreFile)) {
            keystore.load(in, password.toCharArray());
        }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, password.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        trustManager = (X509TrustManager) trustManagerFactory.getTrustManagers()[0];

        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
    }
}
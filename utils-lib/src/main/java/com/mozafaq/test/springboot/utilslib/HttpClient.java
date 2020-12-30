package com.mozafaq.test.springboot.utilslib;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


public class HttpClient
{
    private static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET = "GET";
    private String url = "";

    public HttpClient(String url) {
        this.url = url;
    }

    public static void main(String[] args) throws Exception {


        HttpClient httpClient = new HttpClient("https://www.google.com");
        System.out.println(httpClient.performGet(Collections.emptyMap()));


    }

    public String performGet(final Map<String, String> parameters) {
        try {
            return performGet(this.url, parameters);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public String performGet(final String url, final Map<String, String> parameters) throws Exception {

        String params = parameters.entrySet()
                .stream()
                .map((e) -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        String finalUrl = url.concat("?").concat(params);
        if (!url.startsWith("http")) {
            finalUrl = "https://".concat(finalUrl);
        }


        LOG.info("Final URL: " + finalUrl);
        URL obj = new URL(finalUrl);
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();

        try {
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(GET);


            con.setRequestProperty("User-Agent", USER_AGENT);
            con.connect();

            InputStream is = null;
            if (con.getResponseCode() / 200 == 1) {
                is = con.getInputStream();
            } else {
                is = con.getErrorStream();
            }

            in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }

        LOG.info("Response: " + sb);
        return sb.toString();
    }
}

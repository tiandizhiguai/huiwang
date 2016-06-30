package com.huiwang.service.biz.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.huiwang.param.ArticleParam;
import com.huiwang.service.biz.JokeBizService;

@Service
public class JokeBizServiceImpl implements JokeBizService {

    private static final Logger logger           = LogManager.getLogger(JokeBizServiceImpl.class);
    private static final String DEF_CHATSET      = "UTF-8";
    private static final int    DEF_CONN_TIMEOUT = 2000;
    private static final int    DEF_READ_TIMEOUT = 2000;
    private static final String APPKEY           = "5716ada7e8f29eac235869d9176ccc88";
    private static final String URL              = "http://japi.juhe.cn/joke/content/list.from";

    @Override
    public List<String> getList(ArticleParam bizParam) {
        Map<String, Object> params = new HashMap<>();
        long currentTime = Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10));
        params.put("time", currentTime);
        params.put("key", APPKEY);
        params.put("sort", "desc");
        params.put("page", bizParam.getPageNo());
        params.put("pagesize", bizParam.getPageSize());

        String content = getContent(URL, params, "GET");
        JSONObject json = JSONObject.parseObject(content);
        JSONArray datas = (JSONArray) ((JSONObject) json.get("result")).get("data");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < datas.size(); i++) {
            JSONObject data = (JSONObject) datas.get(i);
            list.add(String.valueOf(data.get("content")));
        }
        return list;
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public String getContent(String strUrl, Map<String, Object> params, String method) {
        if (method == null || method.equals("GET")) {
            strUrl = strUrl + "?" + urlencode(params);
        }

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();

            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                content.append(strRead);
            }
        } catch (IOException e) {
            logger.error("failed to get joke.", e);
        } finally {
            IOUtils.close(reader);
            if (conn != null) {
                conn.disconnect();
            }
        }
        return content.toString();
    }

    // 将map型转为请求参数型
    public String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", DEF_CHATSET)).append("&");
            } catch (UnsupportedEncodingException e) {
                logger.error("failed to url encode.", e);
            }
        }
        return sb.toString();
    }
}

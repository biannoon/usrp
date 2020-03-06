package com.scrcu.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Map;

/**
 * 描述： JSON工具类
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/19 15:29
 */
public class JsonUtils {

    /**
     * 描述： 将map转化为JSONObject
     * @param map
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 15:29
     */
    public static JSONObject createJSONObjectByMap(Map<String, Object> map) {
        JSONObject obj = new JSONObject();
        for(Map.Entry<String,Object> entry : map.entrySet()) {
            obj.put(entry.getKey(), entry.getValue());
        }
        return obj;
    }

    public static JSONObject createJSONObjectByMap2(Map<String, String> map) {
        JSONObject obj = new JSONObject();
        for(Map.Entry<String,String> entry : map.entrySet()) {
            obj.put(entry.getKey(), entry.getValue());
        }
        return obj;
    }

    public static Map<String,Object> createMapByJSONObject(JSONObject jsonObject) {
        Map<String, Object> map = new LinkedCaseInsensitiveMap<>();
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public static JSONObject getSuccJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("rc", 1);
        return obj;
    }

    public static JSONObject getErrJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("rc", 0);
        return obj;
    }
}

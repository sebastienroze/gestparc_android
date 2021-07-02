package ifa.devlog.gestparc.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {
    public static String getJsonString(JSONObject json, String value) throws JSONException {
        if (json.isNull(value)) return null;
        return json.getString(value);
    }
}

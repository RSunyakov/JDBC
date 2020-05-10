package ru.kpfu.itis.service.vkapi;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JsonParserVkWallImpl implements JsonParserVkWall{
    @Override
    public List<String> getWallsList(String json) {
        List<String> list = new ArrayList<>();
        JSONObject object = new JSONObject(json);
        JSONObject responseObject = object.getJSONObject("response");
        JSONArray array = responseObject.getJSONArray("items");
        for (int i = 0; i < array.length(); i++) {
            JSONObject item = array.getJSONObject(i);
            String text = item.getString("text");
            list.add(text);
        }
        return list;
    }
}

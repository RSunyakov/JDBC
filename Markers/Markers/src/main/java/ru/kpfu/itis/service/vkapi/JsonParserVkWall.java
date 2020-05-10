package ru.kpfu.itis.service.vkapi;

import java.util.List;

public interface JsonParserVkWall {
    List<String> getWallsList(String json);
}

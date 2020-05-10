package ru.kpfu.itis.service.vkapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class VkApiServiceImpl implements VkApiService{
    @Autowired
    private JsonParserVkWall jsonParserVkWall;

    @Override
    public List<String> getPosts() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.vk.com/method/wall.get?domain=covid2019_official&count=10&filter=all&v=5.103&access_token=227c94de978bfbf297493fb81eb75f3dd19345399ea0c29d41ee56a4fda5d1ee5748b9a76af65591172d3";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return jsonParserVkWall.getWallsList(response.getBody());
    }
}

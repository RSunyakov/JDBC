package ru.kpfu.itis.service.covid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.dto.CovidInfo;

import java.util.List;
@Service
public class CovidInfoServiceImpl implements CovidInfoService{
    @Autowired
    private JsonParserCovidInfo jsonParserCovidInfo;

    @Override
    public List<CovidInfo> covidInfoList() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.covid19api.com/country/russia";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return jsonParserCovidInfo.getCovidInfoFromJson(response.getBody());
    }
}

package ru.kpfu.itis.service.covid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.CovidInfo;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonParserCovidInfoImpl implements JsonParserCovidInfo {
    @Override
    public List<CovidInfo> getCovidInfoFromJson(String json) {
        List<CovidInfo> list = new ArrayList<>();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject item = array.getJSONObject(i);
            Long confirmed = item.getLong("Confirmed");
            Long deaths = item.getLong("Deaths");
            Long recovered = item.getLong("Recovered");
            String date = item.getString("Date");
            list.add(CovidInfo.builder()
                    .date(date)
                    .confirmed(confirmed)
                    .deaths(deaths)
                    .recovered(recovered).build());
        }
        return list;
    }
}


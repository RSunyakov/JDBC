package ru.kpfu.itis.service.covid;

import ru.kpfu.itis.dto.CovidInfo;

import java.util.List;

public interface CovidInfoService {
    List<CovidInfo> covidInfoList();
}

package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kpfu.itis.service.covid.CovidInfoService;
import ru.kpfu.itis.service.vkapi.VkApiService;

@Controller
public class CovidController {
    @Autowired
    private VkApiService vkApiService;

    @Autowired
    private CovidInfoService covidInfoService;

    @RequestMapping(value = "/covid_vk", method = RequestMethod.GET)
    public String getCovidVkPage(Model model) {
        model.addAttribute("posts", vkApiService.getPosts());
        return "covid_vk";
    }

    @RequestMapping(value = "/covid", method = RequestMethod.GET)
    public String getCovidInfo(Model model) {
        model.addAttribute("infos", covidInfoService.covidInfoList());
        return "covid";
    }
}

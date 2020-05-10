package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.models.Marker;
import ru.kpfu.itis.service.auditorium.AuditoriumService;
import ru.kpfu.itis.service.marker.EditMarkerService;

import java.util.Arrays;

@Controller
public class AuditoriumController {
    private static String[] colors = {"Красный", "Оранжевый", "Жёлтый", "Зелёный", "Голубой", "Синий", "Фиолетовый"};

    @Autowired
    AuditoriumService auditoriumService;

    @Autowired
    EditMarkerService editMarkerService;

    @RequestMapping(value = "/auditorium/{name}", method = RequestMethod.GET)
    public String getAuditorium(@PathVariable("name") String name, Model model) {
        Auditorium auditorium = auditoriumService.getAuditorium(name);
        model.addAttribute("auditorium", auditorium);
        model.addAttribute("markers", auditorium.getMarkerList());
        return "auditorium";
    }

    @RequestMapping(value = "/auditorium/{name}/addMarker", method = RequestMethod.GET)
    public String getAddMarker(@ModelAttribute("auditorium") Auditorium auditorium, @ModelAttribute("marker") Marker marker, Model model){
        model.addAttribute("colors", Arrays.asList(colors));
        return "add_marker";
    }

    @RequestMapping(value = "/auditorium/{name}/addMarker", method = RequestMethod.POST)
    public String addMarker(@PathVariable("name") String name, @ModelAttribute("marker") Marker marker, RedirectAttributes redirectAttributes, Model model) {
        editMarkerService.addMarkerToAuditorium(marker, auditoriumService.getAuditorium(name));
        redirectAttributes.addFlashAttribute("message", "Маркер \"" + marker.getColor() + "\"  добавлен в аудиторию");
        return "redirect:" + "/auditorium/" + name;
    }

    @RequestMapping(value = "/auditorium/{name}", method = RequestMethod.POST)
    public String setMarkerCondition(@PathVariable("name") String name, @RequestParam("markerId") Long markerId, Model model){
        editMarkerService.editMarkerCondition(editMarkerService.getMarker(markerId));
        Auditorium auditorium = auditoriumService.getAuditorium(name);
        model.addAttribute("auditorium", auditorium);
        model.addAttribute("markers", auditorium.getMarkerList());
        return "auditorium";
    }
}

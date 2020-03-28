package ru.springuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.springuser.exception.NotFoundException;
import ru.springuser.service.ListUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/list")
public class ListController {

    @Autowired
    ListUserService service;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUserList(ModelMap map) {
        map.put("userList", service.getAllUsers());
        return "user_list";
    }

    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public String time(HttpServletResponse response, java.io.Writer writer) throws IOException {
        String headerValue = CacheControl.maxAge(30, TimeUnit.SECONDS).getHeaderValue();
        System.out.println(headerValue);
        response.addHeader("Cache-Control", headerValue);
        return "time";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String test() {
        return "error:404";
    }

    @RequestMapping(value = "/ex", method = RequestMethod.GET)
    public String ex() {
        throw new NotFoundException();
    }
}

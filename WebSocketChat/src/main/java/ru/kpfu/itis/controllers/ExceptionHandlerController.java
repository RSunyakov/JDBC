package ru.kpfu.itis.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.exception.ForbiddenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Bad cookie")
    @ExceptionHandler(ForbiddenException.class)
    public ModelAndView forbiddenView(HttpServletRequest httpServletRequest, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex.getMessage());
        mav.addObject("url", httpServletRequest.getRequestURL());
        mav.setViewName("forbidden_exception");
        return mav;
    }
}

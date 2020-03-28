package ru.springuser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.springuser.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Text")
    public void standard() {}

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView informedView(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("exceptionWithInfo");
        return mav;
    }
}

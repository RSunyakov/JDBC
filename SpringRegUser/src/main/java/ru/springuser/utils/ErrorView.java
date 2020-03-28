package ru.springuser.utils;

import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ErrorView extends RedirectView {
    private static int STATUS_CODE;

    public static int getStatusCode() {
        return STATUS_CODE;
    }

    public static void setStatusCode(int statusCode) {
        STATUS_CODE = statusCode;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(STATUS_CODE);
        super.render(model, request, response);
    }
}

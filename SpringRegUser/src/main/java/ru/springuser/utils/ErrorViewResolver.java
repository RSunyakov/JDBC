package ru.springuser.utils;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;

public class ErrorViewResolver extends InternalResourceViewResolver {

    @Override
    protected View createView(String viewName, Locale locale) throws Exception {
        if(viewName.startsWith("error:")) {
               String forwardUrl = viewName.substring("error:".length());
                AbstractUrlBasedView view = new ErrorView();
                return this.applyLifecycleMethods("error:", view);
            } else return super.createView(viewName, locale);
        }
    }

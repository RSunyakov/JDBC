package ru.springuser.utils;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

public class MyViewResolver extends InternalResourceViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (viewName.startsWith("error")) {
            try {
                return this.resolveViewName(viewName.substring(viewName.indexOf(":") + 1), locale);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Not found view for this status code");
            }
        } else return super.resolveViewName(viewName, locale);
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        ErrorView view = (ErrorView) this.buildView(viewName);
        View result = this.applyLifecycleMethods(viewName, view);
        return view.checkResource(locale) ? result : null;
    }
}

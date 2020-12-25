package ru.kpfu.itis.annotations.configuration;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfig {

    public static Configuration getConfiguration() {
       Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        return configuration;
    }
}

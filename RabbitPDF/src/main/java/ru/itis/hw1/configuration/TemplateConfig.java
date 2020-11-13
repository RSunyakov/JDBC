package ru.itis.hw1.configuration;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import ru.itis.hw1.models.User;
import ru.itis.hw1.utils.UserFromConsole;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TemplateConfig {

    public static Configuration getConfiguration() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        try {
            cfg.setTemplateLoader(new FileTemplateLoader(new File("templates")));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't find template path", e);
        }
        return cfg;
    }
}

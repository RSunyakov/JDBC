package ru.kpfu.itis.annotations.html;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.kpfu.itis.annotations.configuration.FreemarkerConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class HtmlFormBuilder {

    public void buildHtmlForm(String filePath, Form form) {
        try {
            Configuration configuration = FreemarkerConfig.getConfiguration();
            configuration.setClassForTemplateLoading(this.getClass(), "/");
            Template template = configuration.getTemplate("User.ftl");
            FileWriter fileWriter = new FileWriter(filePath);
            HashMap<String, Object> rootMap = new HashMap<>();
            rootMap.put("form", form);
            template.process(rootMap, fileWriter);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}

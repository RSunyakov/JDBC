package ru.kpfu.itis.service.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageContentServiceImpl implements MessageContentService {
    @Autowired
    private Configuration configuration;

    @Override
    public String getFileUrl(String url, MimeMessage message) {
        try {
            Map<String, Object> model = new HashMap();
            model.put("url", url);
            Template template = configuration.getTemplate("url.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

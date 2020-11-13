package ru.itis.hw1.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.itis.hw1.configuration.TemplateConfig;
import ru.itis.hw1.models.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PdfGenerator {
    public static String generatePdf(User user, String templateName) {
        Map<String, User> root = new HashMap<>();
        root.put("user", user);
        try {
            Configuration cfg = TemplateConfig.getConfiguration();
            Template template = cfg.getTemplate(templateName + ".ftl");
            FileWriter writer = new FileWriter(new File("templates/html/"+templateName + ".html"));
            try {
                template.process(root, writer);
            } catch (TemplateException e) {
                throw new IllegalArgumentException("Template error", e);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Wrong templateName", e);
        }
        String pdfPath = "pdf/" + UUID.randomUUID().toString() + ".pdf";
        File file = new File(pdfPath);
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            try {
                XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream("templates/html/" + templateName + ".html"));
                document.close();
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to write to PDF");
            }
        } catch (DocumentException | FileNotFoundException e) {
            throw new IllegalArgumentException("Failed to create pdf");
        }
        return pdfPath;
    }
}

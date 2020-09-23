package ru.itis.hw1.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.rabbitmq.client.Delivery;
import ru.itis.hw1.models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class PdfGenerator {
    public static void generatePdf(User user, String title) {
        File file = new File("pdf/" + UUID.randomUUID().toString() + ".pdf");
        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            pdf.getCatalog().setLang(new PdfString("ru-RU"));
            document.add(new Paragraph(title));
            List list = new List()
                    .setSymbolIndent(12)
                    .setListSymbol("*");
            list.add(new ListItem("Name: " + user.getName()));
            list.add(new ListItem("Surname: " + user.getSurname()));
            list.add(new ListItem("Age: " + user.getAge()));
            list.add(new ListItem("Passport number: " + user.getPassportNumber()));
            list.add(new ListItem("Date of issue: " + user.getDateOfIssue()));
            document.add(list);
            document.close();
            pdf.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File didn't find");
        }
    }
}

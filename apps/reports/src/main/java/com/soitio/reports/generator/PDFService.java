package com.soitio.reports.generator;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class PDFService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyy'T'HH:mm:ss");

    public String generatePdf(String name, String rendered) throws Exception {
        String filename = "%s_%s.pdf".formatted(name, getCurrentTime());

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(htmlToXhtml(rendered), "/");

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            builder.toStream(fos);
            builder.run();
        } catch (Exception e) {
            throw e;
        }
        return filename;
    }

    private String htmlToXhtml(String html) {
        // Convert HTML to XHTML
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

}

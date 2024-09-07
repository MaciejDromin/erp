package com.soitio.reports.generator;

import jakarta.enterprise.context.ApplicationScoped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;

@ApplicationScoped
public class PDFService {

    public String generatePdf(String name, String rendered) {
        String filename = name + ".pdf";
        // Create an ITextRenderer instance
        ITextRenderer renderer = new ITextRenderer();

        // Convert HTML to XHTML
        String htmlToXhtml = htmlToXhtml(rendered);
        renderer.setDocumentFromString(htmlToXhtml);

        // Render the document to PDF
        renderer.layout();

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            renderer.createPDF(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    private String htmlToXhtml(String html) {
        // Convert HTML to XHTML
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

}

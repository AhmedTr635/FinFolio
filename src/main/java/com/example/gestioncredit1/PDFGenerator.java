package com.example.gestioncredit1;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import javafx.scene.control.Label;

import java.io.FileNotFoundException;
import java.util.List;

public class PDFGenerator {
    // Method to generate PDF from credit details
    public static void generatePDF(String fileName, List<Label> labels, String text) throws FileNotFoundException {
        PdfWriter pdfWriter = new PdfWriter(fileName);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        // Set default page size
        pdfDocument.setDefaultPageSize(PageSize.A4);

        // Add title
        Paragraph title = new Paragraph("Credit Details")
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold();
        document.add(title);

        // Add empty line
        document.add(new Paragraph());

        // Create table
        float[] columnWidths = {100, 100}; // Set column widths
        Table table = new Table(UnitValue.createPercentArray(columnWidths));

        // Add table headers
        table.addCell(createCell("Montant"));
        table.addCell(createCell("Intérêt max et min"));
        table.addCell(createCell("Date début"));
        table.addCell(createCell("Date fin"));

        // Add credit details to table
        for (Label label : labels) {
            String labelText = label.getText();
            String[] parts = labelText.split(": ");
            if (parts.length == 2) {
                table.addCell(createCell(parts[1])); // Add the value part
            }
        }

        // Add table to document
        document.add(table);

        // Close document
        document.close();
    }


    // Method to create cell with specific styling
    private static Paragraph createCell(String content) {
        return new Paragraph(content)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(1)); // Set border without specifying color
    }
}


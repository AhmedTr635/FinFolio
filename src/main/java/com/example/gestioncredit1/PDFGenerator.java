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

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


    // Method to generate PDF from credit details
    public class PDFGenerator {
        // Method to generate PDF from credit details
        public static void generatePDF(String fileName, List<String> labels, String userName) throws FileNotFoundException {
            PdfWriter pdfWriter = new PdfWriter(fileName);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            // Set default page size
            pdfDocument.setDefaultPageSize(PageSize.A4);

            // Add user name
            Paragraph name = new Paragraph(" " + userName)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(name);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTimeString = now.format(formatter);
            Paragraph dateTime = new Paragraph("Date and Time: " + dateTimeString)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(dateTime);


            // Add title
            Paragraph title = new Paragraph("Finfolio")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold();
            document.add(title);

            // Add empty line
            document.add(new Paragraph());

            // Create table
            float[] columnWidths = {3, 4}; // Set column widths
            Table table = new Table(UnitValue.createPercentArray(columnWidths))
                    .useAllAvailableWidth();

            // Add table headers
            table.addCell(createHeaderCell("Credit Details"));
            table.addCell(createHeaderCell("Value"));

            // Add credit details to table
            for (String label : labels) {
                String[] parts = label.split(": ");
                if (parts.length == 2) {
                    if (parts[0].equalsIgnoreCase("Montant")) {
                        parts[1] = "$" + parts[1]; // Add "$" symbol for Montant
                    }
                    table.addCell(createCell(parts[0])); // Add the parameter
                    table.addCell(createCell(parts[1])); // Add the value
                }
            }

            // Add table to document
            document.add(table);

            // Close document
            document.close();
        }

        // Method to create header cell with specific styling
        private static Paragraph createHeaderCell(String content) {
            return new Paragraph(content)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setBorder(new SolidBorder(1)); // Set border
        }

        // Method to create cell with specific styling
        private static Paragraph createCell(String content) {
            return new Paragraph(content)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(new SolidBorder(1)); // Set border
        }
    }

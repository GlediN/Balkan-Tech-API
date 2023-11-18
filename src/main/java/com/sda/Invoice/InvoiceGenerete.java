package com.sda.Invoice;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;

public class InvoiceGenerete {


    public  void generateInvoice() {
        try {
            Document document = new Document();

            String filePath = "src/main/java/com/sda/generatedPdf/invoice.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();

            addContent(document);

            document.close();

            System.out.println("Invoice generated successfully at: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addContent(Document document) throws DocumentException {

        document.add(new Paragraph("Invoice"));

        addEmptyLine(document, 1);
        document.add(new Paragraph("\nInvoice Details:"));
        document.add(new Paragraph("Invoice Number: 001"));
        document.add(new Paragraph("Date: 2023-11-17"));



        addEmptyLine(document, 1);
        document.add(new Paragraph("\nClient Details:"));
        document.add(new Paragraph("Customer Name: John Doe"));
        document.add(new Paragraph("Address: 123 Main Street, City"));
        document.add(new Paragraph("Phone Number:123456789"));


        addEmptyLine(document, 1);
        document.add(new Paragraph("\nProduct Details:"));
        document.add(new Paragraph("Product 1: Item description - $50"));
        document.add(new Paragraph("Product 2: Another item description - $30"));
        addEmptyLine(document, 1);
        document.add(new Paragraph("\nTotal Amount: $80"));

        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(BaseColor.BLACK);
        document.add(new Chunk(lineSeparator));

        // Add more space after the line
        addEmptyLine(document, 2); // Add 2 empty lines
    }

    private static void addEmptyLine(Document document, int number) throws DocumentException {
        for (int i = 0; i < number; i++) {
            document.add(Chunk.NEWLINE);
        }
    }
}




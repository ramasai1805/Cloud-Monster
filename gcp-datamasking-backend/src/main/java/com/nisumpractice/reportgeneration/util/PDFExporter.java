package com.nisumpractice.reportgeneration.util;

import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nisumpractice.reportgeneration.model.User;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFExporter {
    private TableResult result;

    public PDFExporter(TableResult result) {
        this.result = result;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Card Holder Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Card Number", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Card Type", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Expiry Date", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("CVV", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email Id", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Phone number", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {

        for (FieldValueList row : result.iterateAll()) {
            table.addCell(row.get("cardholder_name").getStringValue());
            String card_number = "XXXX XXXX XXXX "+row.get("card_number").getStringValue().substring(9,13);
            table.addCell(card_number);
            table.addCell(row.get("card_type").getStringValue());
            table.addCell(row.get("expiry_date").getStringValue());
            table.addCell(row.get("cvv").getStringValue());
            table.addCell(row.get("email").getStringValue());
            table.addCell(row.get("phone").getStringValue());

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont((FontFactory.HELVETICA_BOLD));
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("List Of Users", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
//        table.setWidths(new float[]{4f, 4f, 3.0f, 3.0f, 1.5f,1.5f,4f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}

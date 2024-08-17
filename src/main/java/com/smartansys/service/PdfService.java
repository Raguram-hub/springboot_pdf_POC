package com.smartansys.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.smartansys.model.Invoice;
import com.smartansys.model.InvoiceItem;

@Service
public class PdfService {
	
	//4th set of code
	 public ByteArrayInputStream generateInvoicePdf(Invoice invoice) {
	        Document document = new Document(PageSize.A4);
	        ByteArrayOutputStream out = new ByteArrayOutputStream();

	        try {
	            PdfWriter writer = PdfWriter.getInstance(document, out);
	            writer.setPageEvent(new HeaderFooterPageEvent());

	            document.open();

	            // Add Logo
	            URL imageUrl = getClass().getResource("/static/images/smartansys-logo.png");
	            if (imageUrl != null) {
	                Image img = Image.getInstance(imageUrl);
	                img.scaleToFit(100, 50);
	                img.setAlignment(Element.ALIGN_LEFT);	 
	                
	                document.add(img);
	            } else {
	                System.err.println("Image not found at path: /static/images/smartansys-logo.png");
	            }
	            
	           

	            // Company Information
	            addCompanyInfo(document);

	            // Invoice Information
	            addInvoiceInfo(document, invoice);

	            // Add Table
	            addInvoiceTable(document, invoice);

	            // Add Total Row
	            addTotalRow(document, invoice);

	            // Payment Options
	            addPaymentOptions(document);
	            
//	            Rectangle rect= new Rectangle(577,825,18,15); // you can resize rectangle 
//	            rect.enableBorderSide(1);
//	            rect.enableBorderSide(2);
//	            rect.enableBorderSide(4);
//	            rect.enableBorderSide(8);
//	            //rect.setBorderColor(BaseColor.BLACK);
//	            rect.setBorderWidth(1);
//	            document.add(rect);
	            
	           

	            document.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return new ByteArrayInputStream(out.toByteArray());
	    }

	 
	 private void addCompanyInfo(Document document) throws DocumentException {
	        PdfPTable table = new PdfPTable(1);
	        table.setWidthPercentage(100);

	        PdfPCell cell = new PdfPCell();
	        cell.setBorder(Rectangle.NO_BORDER);
	        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

	        Paragraph companyInfo = new Paragraph();
	        companyInfo.add(new Chunk("ADDRESS:\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        companyInfo.add(new Chunk("SmartanSys Technologies Private Ltd,\n"));
	        companyInfo.add(new Chunk("6/96, Weavers Colony,\n"));
	        companyInfo.add(new Chunk("Mallasamudram, Namakkal,\n"));
	        companyInfo.add(new Chunk("Tamil Nadu, India, 637503\n"));
	        companyInfo.add(new Chunk("Phone: +91 84385 09909\n"));
	        companyInfo.add(new Chunk("Website: https://www.smartansys.com\n"));
	        companyInfo.add(new Chunk("GSTIN: 33ABHCS3959D1ZA\n"));
	        cell.addElement(companyInfo);
	        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        table.addCell(cell);
	        document.add(table);
	    }

	    private void addInvoiceInfo(Document document, Invoice invoice) throws DocumentException {
          
	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("BILL TO:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        document.add(new Paragraph("Company Name: " + invoice.getCustomerName()));
	        document.add(new Paragraph("Address: " + invoice.getCustomerAddress()));
	        document.add(new Paragraph("Phone Number: " + invoice.getCustomerPhone()));
	        document.add(new Paragraph("Fed ID: " + invoice.getCustomerFedId()));
	        document.add(new Paragraph("Email ID: " + invoice.getCustomerEmail()));
	        document.add(new Paragraph("DATE: " + invoice.getDate()));
	        document.add(new Paragraph("Invoice Date: " + invoice.getInvoiceDate()));
	    }

	    private void addInvoiceTable(Document document, Invoice invoice) throws DocumentException {
	    	document.add(Chunk.NEWLINE);
	        PdfPTable table = new PdfPTable(5);
	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{1, 3, 2, 2, 2});

	        addTableHeader(table);
	        addRows(table, invoice);

	        document.add(table);
	    }

	    private void addTableHeader(PdfPTable table) {
	        Stream.of("ID", "RESOURCES", "Number of resources", "Number of hours", "AMOUNT (April 2024) 1 hour = $")
	                .forEach(columnTitle -> {
	                    PdfPCell header = new PdfPCell();
	                    //header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	                    header.setBorderWidth(1);
	                    header.setPhrase(new Phrase(columnTitle));
	                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    table.addCell(header);
	                });
	    }

	    private void addRows(PdfPTable table, Invoice invoice) {
	        for (InvoiceItem item : invoice.getItems()) {
	            table.addCell(String.valueOf(item.getId()));
	            table.addCell(item.getDescription());
	            table.addCell(String.valueOf(item.getQuantity()));
	            table.addCell(String.valueOf(item.getHours()));
	            table.addCell(String.valueOf(item.getPrice()));
	        }
	    }

	    private void addTotalRow(Document document, Invoice invoice) throws DocumentException {
	        PdfPTable totalTable = new PdfPTable(5);
	        totalTable.setWidthPercentage(100);
	        totalTable.setWidths(new int[]{1, 3, 2, 2, 2});
	        
	        PdfPCell totalCell = new PdfPCell(new Phrase("Total "));
	        totalCell.setColspan(4);
	        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        totalCell.setBorderWidth(1);
	        totalTable.addCell(totalCell);

	        PdfPCell totalValueCell = new PdfPCell(new Phrase(String.valueOf(invoice.getTotal())));
	        totalValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        totalValueCell.setBorderWidth(1);
	        totalTable.addCell(totalValueCell);

	        document.add(totalTable);
	    }

	    private void addPaymentOptions(Document document) throws DocumentException {
	        document.add(Chunk.NEWLINE);
	        Paragraph paymentOptions = new Paragraph();
	        paymentOptions.add(new Chunk("PAYMENT OPTIONS:\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        paymentOptions.add(new Chunk("Bank Account Name: SMARTANSYS TECHNOLOGIES PRIVATE LIMITED\n"));
	        paymentOptions.add(new Chunk("Account number: 7453721743\n"));
	        paymentOptions.add(new Chunk("Bank Name: INDIAN BANK, KALIPATTI, NAMAKKAL, TAMILNADU, INDIA-637501\n"));
	        paymentOptions.add(new Chunk("IFSC Code: IDIB000K279\n"));
	        document.add(paymentOptions);
	    }

	    class HeaderFooterPageEvent extends PdfPageEventHelper {
	        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC);

	        @Override
	        public void onEndPage(PdfWriter writer, Document document) {
	            PdfPTable footer = new PdfPTable(1);
	            footer.setTotalWidth(523);
	            footer.setWidthPercentage(100);

	            PdfPCell cell = new PdfPCell(new Phrase("Generated by My Company", footerFont));
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setBorder(Rectangle.NO_BORDER);
	            footer.addCell(cell);

	            footer.writeSelectedRows(0, -1, 36, 30, writer.getDirectContent());
	        }
	    }

//    public ByteArrayInputStream generateInvoicePdf(Invoice invoice) {
//        Document document = new Document(PageSize.A4);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        try {
//            PdfWriter.getInstance(document, out);
//            document.open();
//
//            document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
//            document.add(new Paragraph("Customer Name: " + invoice.getCustomerName()));
//            document.add(new Paragraph("Customer Address: " + invoice.getCustomerAddress()));
//
//            document.add(new Paragraph("\nItems:"));
//                      
//            for (InvoiceItem item : invoice.getItems()) {
//                document.add(new Paragraph(item.getDescription() + " - " + item.getQuantity() + " x " + item.getPrice()));
//            }
//
//            document.add(new Paragraph("\nTotal: " + invoice.getTotal()));
//
//            document.close();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//        return new ByteArrayInputStream(out.toByteArray());
//    }
	
//2nd set of code
	
//	public ByteArrayInputStream generateInvoicePdf(Invoice invoice) {
//        Document document = new Document(PageSize.A4);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        try {
//            PdfWriter writer = PdfWriter.getInstance(document, out);
//            writer.setPageEvent(new HeaderFooterPageEvent());
//
//            document.open();
//
//            // Add Invoice Details
//            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
//            Paragraph title = new Paragraph("Invoice", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
//            document.add(new Paragraph("Customer Name: " + invoice.getCustomerName()));
//            document.add(new Paragraph("Customer Address: " + invoice.getCustomerAddress()));
//            document.add(Chunk.NEWLINE);
//
//            // Add Table
//            PdfPTable table = new PdfPTable(4);
//            table.setWidthPercentage(100);
//            table.setWidths(new int[]{3, 1, 2, 2});
//
//            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//            PdfPCell hcell;
//            hcell = new PdfPCell(new Phrase("Description", headFont));
//            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(hcell);
//
//            hcell = new PdfPCell(new Phrase("Quantity", headFont));
//            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(hcell);
//
//            hcell = new PdfPCell(new Phrase("Unit Price", headFont));
//            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(hcell);
//
//            hcell = new PdfPCell(new Phrase("Total Price", headFont));
//            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(hcell);
//
//            for (InvoiceItem item : invoice.getItems()) {
//                PdfPCell cell;
//
//                cell = new PdfPCell(new Phrase(item.getDescription()));
//                cell.setPaddingLeft(5);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase(String.valueOf(item.getQuantity())));
//                cell.setPaddingLeft(5);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase(String.valueOf(item.getPrice())));
//                cell.setPaddingLeft(5);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase(String.valueOf(item.getQuantity() * item.getPrice())));
//                cell.setPaddingLeft(5);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//            }
//
//            document.add(table);
//            document.add(Chunk.NEWLINE);
//
//            // Add Total
//            Paragraph total = new Paragraph("Total: " + invoice.getTotal(), titleFont);
//            total.setAlignment(Element.ALIGN_RIGHT);
//            document.add(total);
//
//            document.close();
//        } catch (DocumentException ex) {
//            ex.printStackTrace();
//        }
//
//        return new ByteArrayInputStream(out.toByteArray());
//    }
//
//    class HeaderFooterPageEvent extends PdfPageEventHelper {
//        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC);
//
//        @Override
//        public void onEndPage(PdfWriter writer, Document document) {
//            PdfPTable footer = new PdfPTable(1);
//            footer.setTotalWidth(523);
//            footer.setWidthPercentage(100);
//
//            PdfPCell cell = new PdfPCell(new Phrase("Generated by My Company", footerFont));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(Rectangle.NO_BORDER);
//            footer.addCell(cell);
//
//            footer.writeSelectedRows(0, -1, 36, 30, writer.getDirectContent());
//        }
//    }
	
//3rd set of code
//	 public ByteArrayInputStream generateInvoicePdf(Invoice invoice) {
//	        Document document = new Document(PageSize.A4);
//	        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//	        try {
//	            PdfWriter writer = PdfWriter.getInstance(document, out);
//	            writer.setPageEvent(new HeaderFooterPageEvent());
//
//	            document.open();
//
//	            // Add Logo
//	            URL imageUrl = getClass().getResource("/static/images/smartansys-logo.png");
//	            if (imageUrl != null) {
//	                Image img = Image.getInstance(imageUrl);
//	                img.scaleToFit(100, 50);
//	                img.setAlignment(Element.ALIGN_LEFT);
//	                document.add(img);
//	            }
//	            
//	            
//	            Font underlinedFont = new Font(Font.HELVETICA, 12, Font.UNDERLINE);
//	            Chunk underlinedText = new Chunk("Invoice Number: " + invoice.getInvoiceNumber(), underlinedFont);
//	            Paragraph invoiceNumberParagraph = new Paragraph();
//	            invoiceNumberParagraph.add(underlinedText);
//	            document.add(invoiceNumberParagraph);
//
//	            // Add Invoice Details
//	            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
//	            Paragraph title = new Paragraph("Invoice", titleFont);
//	            title.setAlignment(Element.ALIGN_CENTER);
//	            document.add(title);
//
//	            document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
//	            document.add(new Paragraph("Customer Name: " + invoice.getCustomerName()));
//	            document.add(new Paragraph("Customer Address: " + invoice.getCustomerAddress()));
//	            document.add(Chunk.NEWLINE);
//
//	            // Add Table
//	            PdfPTable table = new PdfPTable(4);
//	            table.setWidthPercentage(100);
//	            table.setWidths(new int[]{4, 1, 2, 2});
//
//	            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//	            PdfPCell hcell;
//	            hcell = new PdfPCell(new Phrase("Description", headFont));
//	            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            table.addCell(hcell);
//
//	            hcell = new PdfPCell(new Phrase("Quantity", headFont));
//	            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            table.addCell(hcell);
//
//	            hcell = new PdfPCell(new Phrase("Unit Price", headFont));
//	            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            table.addCell(hcell);
//
//	            hcell = new PdfPCell(new Phrase("Total Price", headFont));
//	            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            table.addCell(hcell);
//
//	            for (InvoiceItem item : invoice.getItems()) {
//	                PdfPCell cell;
//
//	                cell = new PdfPCell(new Phrase(item.getDescription()));
//	                cell.setPaddingLeft(5);
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//	                table.addCell(cell);
//
//	                cell = new PdfPCell(new Phrase(String.valueOf(item.getQuantity())));
//	                cell.setPaddingLeft(5);
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//
//	                cell = new PdfPCell(new Phrase(String.valueOf(item.getPrice())));
//	                cell.setPaddingLeft(5);
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//
//	                cell = new PdfPCell(new Phrase(String.valueOf(item.getQuantity() * item.getPrice())));
//	                cell.setPaddingLeft(5);
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	                table.addCell(cell);
//	            }
//
//	            document.add(table);
//	            document.add(Chunk.NEWLINE);
//
//	            // Add Total
//	            Paragraph total = new Paragraph("Total: " + invoice.getTotal(), titleFont);
//	            total.setAlignment(Element.ALIGN_RIGHT);
//	            document.add(total);
//
//	            document.close();
//	        } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
//
//	        return new ByteArrayInputStream(out.toByteArray());
//	    }
//
//	    class HeaderFooterPageEvent extends PdfPageEventHelper {
//	        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC);
//
//	        @Override
//	        public void onEndPage(PdfWriter writer, Document document) {
//	            PdfPTable footer = new PdfPTable(1);
//	            footer.setTotalWidth(523);
//	            footer.setWidthPercentage(100);
//
//	            PdfPCell cell = new PdfPCell(new Phrase("Generated by My Company", footerFont));
//	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            cell.setBorder(Rectangle.NO_BORDER);
//	            footer.addCell(cell);
//
//	            footer.writeSelectedRows(0, -1, 36, 30, writer.getDirectContent());
//	        }
//	    }
}

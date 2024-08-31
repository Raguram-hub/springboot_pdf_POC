package com.smartansys.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;

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
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;



@Service
public class MatPdfService {
	
	 public ByteArrayInputStream generateInvoicePdf() {
	        Document document = new Document(PageSize.A4);
	        ByteArrayOutputStream out = new ByteArrayOutputStream();

	        try {
	            PdfWriter writer = PdfWriter.getInstance(document, out);
	            writer.setPageEvent(new HeaderFooterPageEvent());	         

	            document.open();	
	            
	            //addMatBasicInvoiceInfo(document);
	            addMatBasicInvoiceInfo(document);
	            
	            //addMatBasicInvoiceInfo1(document, writer);
	            
	            document.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return new ByteArrayInputStream(out.toByteArray());
	    }
	 
	 private void addUnderlineToAllPdf(Document document) {
		 PdfPTable table = new PdfPTable(1);
        PdfPCell underlineCell = new PdfPCell();
		    underlineCell.setBorder(Rectangle.NO_BORDER);		    
		    
		    Paragraph underline = new Paragraph(new Chunk(new LineSeparator()));
		    underlineCell.addElement(underline);
		    table.addCell(underlineCell);
		    document.add(table);
	 }
	
	 

	 
	 private void addMatBasicInvoiceInfo(Document document) throws DocumentException {
	        // Create a table with 2 columns for alternating left and right sections
	        PdfPTable mainTable = new PdfPTable(2);
	        mainTable.setWidthPercentage(100);
	        mainTable.setWidths(new int[]{1, 1}); // Equal width for both columns

	        // Add Personal Details to the left column
	        PdfPCell leftCell = new PdfPCell();
	        leftCell.setBorder(Rectangle.NO_BORDER);
	        leftCell.addElement(new Paragraph("Personal Details:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        leftCell.addElement(new Paragraph("ID: MATR123456"));
	        leftCell.addElement(new Paragraph("Name: Arun Kumar"));
	        leftCell.addElement(new Paragraph("Mobile Number: 9876543210"));
	        leftCell.addElement(new Paragraph("Email Address: arun.kumar@example.com"));
	        leftCell.addElement(new Paragraph("Partner Preference: Looking for a well-educated partner with similar values."));
	        leftCell.addElement(new Paragraph("About This Profile: This profile is created by the user's parents."));
	        leftCell.addElement(new Paragraph("Profile Created For: Son"));
	        leftCell.addElement(new Paragraph("Expectation Details: Looking for a well-educated partner."));
	        leftCell.addElement(new Paragraph("District Preference: Chennai"));
	        mainTable.addCell(leftCell);
	        
	        PdfPCell rightCell = new PdfPCell();
	        rightCell.setBorder(Rectangle.NO_BORDER);
	        rightCell.addElement(new Paragraph("Photo"));
	        mainTable.addCell(rightCell);

	        // Add Basic Details to the right column
	        leftCell = new PdfPCell();
	        leftCell.setBorder(Rectangle.NO_BORDER);
	        leftCell.addElement(new Paragraph("Basic Details:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        leftCell.addElement(new Paragraph("Date of birth: 1990-01-01"));
	        leftCell.addElement(new Paragraph("Age: 34"));
	        rightCell.addElement(new Paragraph("Gender: Male"));
	        leftCell.addElement(new Paragraph("Height: 6 ft"));
	        leftCell.addElement(new Paragraph("Weight: 75 kg"));
	        leftCell.addElement(new Paragraph("Mother Tongue: Tamil"));
	        leftCell.addElement(new Paragraph("Marital Status: Single"));
	        leftCell.addElement(new Paragraph("Body Type: Athletic"));
	        leftCell.addElement(new Paragraph("Complexion: Fair"));
	        leftCell.addElement(new Paragraph("Languages Known: English, Tamil"));
	        leftCell.addElement(new Paragraph("Blood Group: O+"));
	        leftCell.addElement(new Paragraph("Birth Time: 10:00 AM"));
	        leftCell.addElement(new Paragraph("BirthPlace: Chennai"));
	        mainTable.addCell(leftCell);
	        
	        
	     // Add Family Details Information to the left column
	        leftCell = new PdfPCell();	        
	        leftCell.setBorder(Rectangle.NO_BORDER);
	        leftCell.addElement(new Paragraph("Family Details Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        leftCell.addElement(new Paragraph("Father's Name: Raman Iyer"));
	        leftCell.addElement(new Paragraph("Mother's Name: Lakshmi Iyer"));
	        leftCell.addElement(new Paragraph("Family Value: Traditional"));
	        leftCell.addElement(new Paragraph("Family Status: Middle Class"));
	        leftCell.addElement(new Paragraph("Family Type: Nuclear"));
	        leftCell.addElement(new Paragraph("Father's Occupation: Retired"));
	        leftCell.addElement(new Paragraph("Mother's Occupation: Homemaker"));
	        leftCell.addElement(new Paragraph("Parent's Contact Number: 9876543210"));
	        leftCell.addElement(new Paragraph("Number of Brothers: 1"));
	        leftCell.addElement(new Paragraph("Number of Sisters: 2"));
	        leftCell.addElement(new Paragraph("Married Sisters: 1"));
	        leftCell.addElement(new Paragraph("Married Brothers: 0"));
	        leftCell.addElement(new Paragraph("Asset Details: House in Chennai"));
	        leftCell.addElement(new Paragraph("Asset Worth: 50 Lakhs"));
	        leftCell.addElement(new Paragraph("About My Family: We are a close-knit family with traditional values."));
	        mainTable.addCell(leftCell);

	        // Add Religion Information to the right column
	        rightCell = new PdfPCell();
	        rightCell.setBorder(Rectangle.NO_BORDER);
	        rightCell.addElement(new Paragraph("Religion Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        rightCell.addElement(new Paragraph("Religion: Hindu"));
	        rightCell.addElement(new Paragraph("Caste: Brahmin"));
	        rightCell.addElement(new Paragraph("Sub Caste: Iyer"));
	        rightCell.addElement(new Paragraph("Rasi: Mesha"));
	        rightCell.addElement(new Paragraph("Star: Ashwini"));
	        rightCell.addElement(new Paragraph("Dosham: No"));
	        rightCell.addElement(new Paragraph("Dosham Details: N/A"));
	        rightCell.addElement(new Paragraph("Kuladeivam: Perumal"));
	        mainTable.addCell(rightCell);

	        
	        // Add Contact Information to the left column
	        leftCell = new PdfPCell();
	        leftCell.setBorder(Rectangle.NO_BORDER);
	        leftCell.addElement(new Paragraph("Contact Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        leftCell.addElement(new Paragraph("State: Tamil Nadu"));
	        leftCell.addElement(new Paragraph("District: Chennai"));
	        leftCell.addElement(new Paragraph("City: Chennai"));
	        leftCell.addElement(new Paragraph("Taluk: Mylapore"));
	        leftCell.addElement(new Paragraph("Pin Code: 600004"));
	        mainTable.addCell(leftCell);

	        // Add Professional Information to the right column
	        rightCell = new PdfPCell();
	        rightCell.setBorder(Rectangle.NO_BORDER);
	        rightCell.addElement(new Paragraph("Professional Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        rightCell.addElement(new Paragraph("Education: B.Tech in Computer Science"));
	        rightCell.addElement(new Paragraph("College Details: Anna University"));
	        rightCell.addElement(new Paragraph("Occupation Details: Software Engineer at ABC Corp"));
	        rightCell.addElement(new Paragraph("Job Type: Full-time"));
	        rightCell.addElement(new Paragraph("Yearly Income: 10 Lakhs"));
	        mainTable.addCell(rightCell);

	        // Add LifeStyle Information to the left column
	        leftCell = new PdfPCell();
	        leftCell.setBorder(Rectangle.NO_BORDER);
	        leftCell.addElement(new Paragraph("LifeStyle Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	        leftCell.addElement(new Paragraph("Hobbies and Interests: Reading, Traveling"));
	        leftCell.addElement(new Paragraph("Sports: Cricket"));
	        leftCell.addElement(new Paragraph("Music: Classical"));
	        leftCell.addElement(new Paragraph("Food: Vegetarian"));
	        mainTable.addCell(leftCell);

	        // Add an empty cell for the right column to maintain layout consistency
	        rightCell = new PdfPCell();
	        rightCell.setBorder(Rectangle.NO_BORDER);
	        mainTable.addCell(rightCell);

	        // Add the table to the document
	        document.add(mainTable);
	    }

	    private void addInvoiceInfo(Document document) throws DocumentException {
	    	
	    	document.add(Chunk.NEWLINE);
	    	document.add(new Paragraph("Personal Details:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	    	document.add(new Paragraph("ID: MATR123456"));
	    	document.add(new Paragraph("Name: Arun Kumar"));
	    	document.add(new Paragraph("Mobile Number: 9876543210"));
	    	document.add(new Paragraph("Email Address: arun.kumar@example.com"));
	    	document.add(new Paragraph("Partner Preference: Looking for a well-educated partner with similar values."));
	    	document.add(new Paragraph("About This Profile: This profile is created by the user's parents."));
	    	document.add(new Paragraph("Profile Created For: Son"));
	    	document.add(new Paragraph("Expectation Details: Looking for a well-educated partner."));
	    	document.add(new Paragraph("District Preference: Chennai"));

	    	document.add(Chunk.NEWLINE);
	    	document.add(new Paragraph("Basic Details:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	    	document.add(new Paragraph("Date of birth: 1990-01-01"));
	    	document.add(new Paragraph("Age: 34"));
	    	document.add(new Paragraph("Gender: Male"));
	    	document.add(new Paragraph("Height: 6 ft"));
	    	document.add(new Paragraph("Weight: 75 kg"));
	    	document.add(new Paragraph("Mother Tongue: Tamil"));
	    	document.add(new Paragraph("Marital Status: Single"));
	    	document.add(new Paragraph("Body Type: Athletic"));
	    	document.add(new Paragraph("Complexion: Fair"));
	    	document.add(new Paragraph("Languages Known: English, Tamil"));
	    	document.add(new Paragraph("Blood Group: O+"));
	    	document.add(new Paragraph("Birth Time: 10:00 AM"));
	    	document.add(new Paragraph("BirthPlace: Chennai"));

	    	document.add(Chunk.NEWLINE);
	    	document.add(new Paragraph("Religion Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	    	document.add(new Paragraph("Religion: Hindu"));
	    	document.add(new Paragraph("Caste: Brahmin"));
	    	document.add(new Paragraph("Sub Caste: Iyer"));
	    	document.add(new Paragraph("Rasi: Mesha"));
	    	document.add(new Paragraph("Star: Ashwini"));
	    	document.add(new Paragraph("Dosham: No"));
	    	document.add(new Paragraph("Dosham Details: N/A"));
	    	document.add(new Paragraph("Kuladeivam: Perumal"));

	    	document.add(Chunk.NEWLINE);
	    	document.add(new Paragraph("Family Details Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	    	document.add(new Paragraph("Father's Name: Raman Iyer"));
	    	document.add(new Paragraph("Mother's Name: Lakshmi Iyer"));
	    	document.add(new Paragraph("Family Value: Traditional"));
	    	document.add(new Paragraph("Family Status: Middle Class"));
	    	document.add(new Paragraph("Family Type: Nuclear"));
	    	document.add(new Paragraph("Father's Occupation: Retired"));
	    	document.add(new Paragraph("Mother's Occupation: Homemaker"));
	    	document.add(new Paragraph("Parent's Contact Number: 9876543210"));
	    	document.add(new Paragraph("Number of Brothers: 1"));
	    	document.add(new Paragraph("Number of Sisters: 2"));
	    	document.add(new Paragraph("Married Sisters: 1"));
	    	document.add(new Paragraph("Married Brothers: 0"));
	    	document.add(new Paragraph("Asset Details: House in Chennai"));
	    	document.add(new Paragraph("Asset Worth: 50 Lakhs"));
	    	document.add(new Paragraph("About My Family: We are a close-knit family with traditional values."));

	    	document.add(Chunk.NEWLINE);
	    	document.add(new Paragraph("Contact Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	    	document.add(new Paragraph("State: Tamil Nadu"));
	    	document.add(new Paragraph("District: Chennai"));
	    	document.add(new Paragraph("City: Chennai"));
	    	document.add(new Paragraph("Taluk: Mylapore"));
	    	document.add(new Paragraph("Pin Code: 600004"));

	    	document.add(Chunk.NEWLINE);
	    	document.add(new Paragraph("Professional Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	    	document.add(new Paragraph("Education: B.Tech in Computer Science"));
	    	document.add(new Paragraph("College Details: Anna University"));
	    	document.add(new Paragraph("Occupation Details: Software Engineer at ABC Corp"));
	    	document.add(new Paragraph("Job Type: Full-time"));
	    	document.add(new Paragraph("Yearly Income: 10 Lakhs"));

	    	document.add(Chunk.NEWLINE);
	    	document.add(new Paragraph("LifeStyle Information:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
	    	document.add(new Paragraph("Hobbies and Interests: Reading, Traveling"));
	    	document.add(new Paragraph("Sports: Cricket"));
	    	document.add(new Paragraph("Music: Classical"));
	    	document.add(new Paragraph("Food: Vegetarian"));

       
//	        document.add(Chunk.NEWLINE);
//	        document.add(new Paragraph("BILL TO:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
//	        document.add(new Paragraph("Company Name: " + invoice.getCustomerName()));
//	        document.add(new Paragraph("Address: " + invoice.getCustomerAddress()));
//	        document.add(new Paragraph("Phone Number: " + invoice.getCustomerPhone()));
//	        document.add(new Paragraph("Fed ID: " + invoice.getCustomerFedId()));
//	        document.add(new Paragraph("Email ID: " + invoice.getCustomerEmail()));
//	        document.add(new Paragraph("DATE: " + invoice.getDate()));
//	        document.add(new Paragraph("Invoice Date: " + invoice.getInvoiceDate()));
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

}

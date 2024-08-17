package com.smartansys.controller;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartansys.model.Invoice;
import com.smartansys.model.InvoiceItem;
import com.smartansys.service.PdfService;

@RestController
public class InvoiceController {
	//4th set of code
	 @Autowired
	    private PdfService pdfService;

	    @GetMapping("/invoice/pdf")
	    public ResponseEntity<InputStreamResource> generateInvoice() {
	        Invoice invoice = new Invoice(
	                "INV-12345",
	                "MS Info tech LLC",
	                "723 south interstate I-35 East, Ste#215, Denton-76205.",
	                "515-758-0450",
	                "87-2646898",
	                "accounts@msitus.com",
	                "02.07.2024",
	                "Month of April 2024",
	                Arrays.asList(
	                        new InvoiceItem(1, "Full Stack Developer", 5, 100, 500.0)
	                ),
	                500.0
	        );

	        ByteArrayInputStream bis = pdfService.generateInvoicePdf(invoice);

	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=invoice.pdf");

	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
	    }

//    @Autowired
//    private PdfService pdfService;
//
//    @GetMapping("/invoice")
//    public ResponseEntity<InputStreamResource> getInvoice() {
//        Invoice invoice = new Invoice(
//                "INV-12345",
//                "John Doe",
//                "1234 Elm Street, Springfield, IL",
//                Arrays.asList(
//                        new InvoiceItem("Item 1", 2, 50.0),
//                        new InvoiceItem("Item 2", 1, 150.0)
//                ),
//                250.0
//        );
//
//        ByteArrayInputStream bis = pdfService.generateInvoicePdf(invoice);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=invoice.pdf");
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(bis));
//    }
}

package com.smartansys.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
//    private String invoiceNumber;
//    private String customerName;
//    private String customerAddress;
//    private List<InvoiceItem> items;
//    private double total;
	
	 private String invoiceNumber;
	    private String customerName;
	    private String customerAddress;
	    private String customerPhone;
	    private String customerFedId;
	    private String customerEmail;
	    private String date;
	    private String invoiceDate;
	    private List<InvoiceItem> items;
	    private double total;
}


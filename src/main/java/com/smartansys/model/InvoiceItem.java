package com.smartansys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {
//    private String description;
//    private int quantity;
//    private double price;
	
	private int id;
    private String description;
    private int quantity;
    private int hours;
    private double price;

}

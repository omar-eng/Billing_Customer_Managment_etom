package model;

import java.util.Date;
import java.util.List;

public class Bill {
    private int id;
    private int customerId;
    private Date billingDate;
    private double totalAmount;
    private String invoiceDate;
    private int invoiceId;
    private String invoiceNumber;
    private List<BillItem> items;

    // Constructors
    public Bill() {}

    // Getters and Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public int getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
    }

    public Date getBillingDate() { 
        return billingDate; 
    }
    
    public void setBillingDate(Date billingDate) { 
        this.billingDate = billingDate; 
    }

    public double getTotalAmount() { 
        return totalAmount; 
    }
    
    public void setTotalAmount(double totalAmount) { 
        this.totalAmount = totalAmount; 
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    // Deprecated methods for backward compatibility
    @Deprecated
    public String getAmount() {
        return String.valueOf(totalAmount);
    }

    @Deprecated
    public String getBillDate() {
        return invoiceDate;
    }
}
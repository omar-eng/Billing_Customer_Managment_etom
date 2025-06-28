package model;

public class BillItem {
    private int id;
    private int billId;
    private int invoiceId;
    private String description;
    private double amount;

    // Constructors
    public BillItem() {}

    public BillItem(int id, int billId, int invoiceId, String description, double amount) {
        this.id = id;
        this.billId = billId;
        this.invoiceId = invoiceId;
        this.description = description;
        this.amount = amount;
    }

    // Getters and Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public int getBillId() { 
        return billId; 
    }
    
    public void setBillId(int billId) { 
        this.billId = billId; 
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }

    public double getAmount() { 
        return amount; 
    }
    
    public void setAmount(double amount) { 
        this.amount = amount; 
    }
}
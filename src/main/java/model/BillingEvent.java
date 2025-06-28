package model;

public class BillingEvent {
    private int id;
    private int customerId;
    private String eventType;
    private double amount;
    private String eventDate;

    public BillingEvent() {}

    public BillingEvent(int id, int customerId, String eventType, double amount, String eventDate) {
        this.id = id;
        this.customerId = customerId;
        this.eventType = eventType;
        this.amount = amount;
        this.eventDate = eventDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
}

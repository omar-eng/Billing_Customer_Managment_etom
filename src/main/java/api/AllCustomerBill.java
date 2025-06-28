package api;

import db.DBConnection;
import model.Bill;
import model.BillItem;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/billing")
public class  AllCustomerBill{

    @GET
    @Path("/bills")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingEvents() {
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String billQuery = "SELECT * FROM bills";
            PreparedStatement billStmt = conn.prepareStatement(billQuery);
            ResultSet billRs = billStmt.executeQuery();

            while (billRs.next()) {
                Bill bill = new Bill();
                int billId = billRs.getInt("id");

                bill.setId(billId);
                bill.setCustomerId(billRs.getInt("customer_id"));
                bill.setInvoiceDate(billRs.getString("invoice_date"));
                bill.setTotalAmount(billRs.getDouble("total_amount"));
                // Change here: use invoice_number instead of invoice_id
                bill.setInvoiceNumber(billRs.getString("invoice_number"));

                // جلب عناصر الفاتورة المرتبطة
                String itemQuery = "SELECT * FROM bill_items WHERE bill_id = ?";
                PreparedStatement itemStmt = conn.prepareStatement(itemQuery);
                itemStmt.setInt(1, billId);
                ResultSet itemRs = itemStmt.executeQuery();

                List<BillItem> items = new ArrayList<>();
                while (itemRs.next()) {
                    BillItem item = new BillItem();
                    item.setId(itemRs.getInt("id"));
                    item.setInvoiceId(itemRs.getInt("invoice_id"));
                    item.setBillId(itemRs.getInt("bill_id"));
                    item.setDescription(itemRs.getString("description"));
                    item.setAmount(itemRs.getDouble("amount"));
                    items.add(item);
                }

                bill.setItems(items);
                bills.add(bill);
            }

            return Response.ok(bills).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }
}

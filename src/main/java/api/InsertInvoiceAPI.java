package api;

import db.DBConnection;
import model.Bill;
import model.BillItem;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;

@Path("/billing")
public class InsertInvoiceAPI {

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertInvoice(Bill bill) {
        try (Connection conn = DBConnection.getConnection()) {

            // Insert invoice first
            String billSql = "INSERT INTO bills (customer_id, invoice_date, total_amount, invoice_number) VALUES (?, ?, ?, ?)";
            PreparedStatement billStmt = conn.prepareStatement(billSql, Statement.RETURN_GENERATED_KEYS);
            billStmt.setInt(1, bill.getCustomerId());
            billStmt.setString(2, bill.getInvoiceDate());
            billStmt.setDouble(3, bill.getTotalAmount());
            billStmt.setString(4, bill.getInvoiceNumber());
            billStmt.executeUpdate();

            ResultSet billKeys = billStmt.getGeneratedKeys();
            int billId = 0;
            if (billKeys.next()) {
                billId = billKeys.getInt(1);
            }

            // Insert related invoice items
            String itemSql = "INSERT INTO bill_items (invoice_id, bill_id, description, amount) VALUES (?, ?, ?, ?)";
            PreparedStatement itemStmt = conn.prepareStatement(itemSql);

            List<BillItem> items = bill.getItems();
            for (BillItem item : items) {
                itemStmt.setInt(1, bill.getInvoiceId());
                itemStmt.setInt(2, billId);
                itemStmt.setString(3, item.getDescription());
                itemStmt.setDouble(4, item.getAmount());
                itemStmt.addBatch();
            }

            itemStmt.executeBatch();

            return Response.ok(bill).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }
}

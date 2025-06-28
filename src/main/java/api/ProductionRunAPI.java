package api;

import db.DBConnection;
import model.BillItem;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Path("/production")
public class ProductionRunAPI {

    public static class BillRequest {
        public int customerId;
        public int invoiceId;
        public List<BillItem> items;
    }

    @POST
    @Path("/createBill")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBill(BillRequest request) {
        double totalAmount = 0.0;
        for (BillItem item : request.items) {
            totalAmount += item.getAmount();
        }

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Insert invoice into bills table
            String billSql = "INSERT INTO bills (customer_id, invoice_date, total_amount, invoice_id) VALUES (?, ?, ?, ?)";
            PreparedStatement billStmt = conn.prepareStatement(billSql, Statement.RETURN_GENERATED_KEYS);
            billStmt.setInt(1, request.customerId);
            billStmt.setString(2, LocalDate.now().toString());
            billStmt.setDouble(3, totalAmount);
            billStmt.setInt(4, request.invoiceId);
            billStmt.executeUpdate();

            ResultSet generatedKeys = billStmt.getGeneratedKeys();
            int billId;
            if (generatedKeys.next()) {
                billId = generatedKeys.getInt(1);
            } else {
                conn.rollback();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Failed to retrieve bill ID").build();
            }

            // Insert invoice items
            String itemSql = "INSERT INTO bill_items (invoice_id, bill_id, description, amount) VALUES (?, ?, ?, ?)";
            PreparedStatement itemStmt = conn.prepareStatement(itemSql);
            for (BillItem item : request.items) {
                itemStmt.setInt(1, request.invoiceId);
                itemStmt.setInt(2, billId);
                itemStmt.setString(3, item.getDescription());
                itemStmt.setDouble(4, item.getAmount());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();

            conn.commit();
            return Response.ok("Bill and items created successfully").build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }
}

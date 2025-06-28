package api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.*;
import db.DBConnection;
@Path("/billing")
public class InvoiceArchiveAPI {

    @GET
    @Path("/archive")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getArchivedInvoices() {
        StringBuilder response = new StringBuilder();

        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bills");

            while (rs.next()) {
                response.append("Invoice: ").append(rs.getInt("invoice_id"))
                        .append(", Customer ID: ").append(rs.getInt("customer_id"))
                        .append(", Date: ").append(rs.getString("invoice_date"))
                        .append(", Total: ").append(rs.getDouble("total_amount"))
                        .append("\n");
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("DB Error: " + e.getMessage()).build();
        }

        return Response.ok(response.toString()).build();
    }
}

package api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import db.DBConnection;
@Path("/billing")
public class BillingEventsAPI {

    @GET
    @Path("/events/{billId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingEvents(@PathParam("billId") int billId) {
        List<Map<String, Object>> events = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM bill_items WHERE bill_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", rs.getInt("id"));
                item.put("invoice_id", rs.getInt("invoice_id"));
                item.put("description", rs.getString("description"));
                item.put("amount", rs.getDouble("amount"));
                events.add(item);
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Database error: " + e.getMessage()).build();
        }

        return Response.ok(events).build();
    }
}

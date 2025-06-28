package api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.FileOutputStream;
import java.sql.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import db.DBConnection;
@Path("/billing")
public class RenderInvoiceAPI {

    @GET
    @Path("/render/{invoiceId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response renderInvoiceToPDF(@PathParam("invoiceId") int invoiceId) {
        try (Connection conn = DBConnection.getConnection()) {
            // Query invoice data
            String billSql = "SELECT * FROM bills WHERE invoice_id = ?";
            PreparedStatement billStmt = conn.prepareStatement(billSql);
            billStmt.setInt(1, invoiceId);
            ResultSet billRs = billStmt.executeQuery();

            if (!billRs.next()) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Invoice not found.").build();
            }

            // Prepare PDF file
            Document document = new Document();
            String filePath = "invoice_" + invoiceId + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Write data to PDF file
            document.add(new Paragraph("Invoice ID: " + invoiceId));
            document.add(new Paragraph("Customer ID: " + billRs.getInt("customer_id")));
            document.add(new Paragraph("Invoice Date: " + billRs.getString("invoice_date")));
            document.add(new Paragraph("Total Amount: " + billRs.getDouble("total_amount")));
            document.add(new Paragraph(" ")); // Empty line

            // Get invoice items
            String itemSql = "SELECT * FROM bill_items WHERE invoice_id = ?";
            PreparedStatement itemStmt = conn.prepareStatement(itemSql);
            itemStmt.setInt(1, invoiceId);
            ResultSet itemRs = itemStmt.executeQuery();

            while (itemRs.next()) {
                document.add(new Paragraph("Item: " + itemRs.getString("description") +
                        " - Amount: " + itemRs.getDouble("amount")));
            }

            document.close();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error: " + e.getMessage()).build();
        }

        return Response.ok("Invoice rendered and saved as PDF.").build();
    }
}

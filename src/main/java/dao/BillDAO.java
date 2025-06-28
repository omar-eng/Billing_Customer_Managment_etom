package dao;

import db.DBConnection;
import model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillDAO {

    public void insert(Bill bill) throws SQLException {
        String sql = "INSERT INTO bills (customer_id, billing_date, total_amount) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bill.getCustomerId());
            stmt.setDate(2, new java.sql.Date(bill.getBillingDate().getTime()));
            stmt.setDouble(3, bill.getTotalAmount());
            stmt.executeUpdate();
        }
    }

    // You can add update, delete, and get later

    public Bill getBillById(int billId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Bill getById(int billId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

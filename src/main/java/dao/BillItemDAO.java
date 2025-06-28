package dao;

import db.DBConnection;
import model.BillItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillItemDAO {

    public void insert(BillItem billItem) {
        String sql = "INSERT INTO bill_items (bill_id, description, amount) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billItem.getBillId());
            stmt.setString(2, billItem.getDescription());
            stmt.setDouble(3, billItem.getAmount());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 
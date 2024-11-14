
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sendi
 */
public class DatabaseHelper {
   // Metode untuk koneksi ke database SQLite
    public static Connection koneksidb() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\USER\\contacts.db"); // Perbaiki URL koneksi
            //JOptionPane.showMessageDialog(null, "Terhubung");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    // Menambahkan kontak baru ke database
    public static void addContact(String name, String phone, String category) throws SQLException {
        String sql = "INSERT INTO contacts(name, phone, category) VALUES(?, ?, ?)";
        try (Connection conn = koneksidb(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, category);
            pstmt.executeUpdate();
        }
    }

    // Mendapatkan semua kontak dari database
    public static List<Contact> getContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";
        try (Connection conn = koneksidb(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Contact contact = new Contact(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("category")
                );
                contacts.add(contact);
            }
        }
        return contacts;
    }

    // Mengupdate data kontak
    public static void updateContact(int id, String name, String phone, String category) throws SQLException {
        String sql = "UPDATE contacts SET name = ?, phone = ?, category = ? WHERE id = ?";
        try (Connection conn = koneksidb(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, category);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
    }

    // Menghapus kontak dari database
    public static void deleteContact(int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (Connection conn = koneksidb(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Mencari kontak berdasarkan nama atau nomor telepon
    public static List<Contact> searchContact(String keyword) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts WHERE name LIKE ? OR phone LIKE ?";
        try (Connection conn = koneksidb(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("category")
                );
                contacts.add(contact);
            }
        }
        return contacts;
    }
}

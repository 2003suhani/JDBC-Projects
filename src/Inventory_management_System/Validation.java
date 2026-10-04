package Inventory_management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Validation {

	    // =========================================================
	    // BASIC VALIDATION
	    // =========================================================

	    // Checks whether a value is not empty
	    public static boolean validateNotEmpty(String value) {

	        return value != null && !value.trim().isEmpty();
	    }

	    // Checks whether name contains alphabets and spaces
	    public static boolean validateName(String name) {

	        return name != null &&
	                name.matches("[a-zA-Z ]+");
	    }

	    // Checks whether email format is valid
	    public static boolean validateEmail(String email) {

	        return email != null &&
	                email.matches(
	                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
	                );
	    }

	    // Checks whether phone contains exactly 10 digits
	    public static boolean validatePhone(String phone) {

	        return phone != null &&
	                phone.matches("[0-9]{10}");
	    }

	    // Checks whether price is valid
	    public static boolean validatePrice(double price) {

	        return price >= 0;
	    }

	    // Checks whether stock is valid
	    public static boolean validateStock(int stock) {

	        return stock >= 0;
	    }

	    // =========================================================
	    // SUPPLIER ID VALIDATION
	    // =========================================================

	    // Checks whether supplier ID exists
	    public static boolean validateSupplierId(int supplierId) {

	        String sql =
	                "SELECT supplier_id FROM suppliers " +
	                "WHERE supplier_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps =
	                     con.prepareStatement(sql)) {

	            ps.setInt(1, supplierId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error validating supplier ID: "
	                    + e.getMessage()
	            );

	            return false;
	        }
	    }

	    // =========================================================
	    // PRODUCT ID VALIDATION
	    // =========================================================

	    // Checks whether product ID exists
	    public static boolean validateProductId(int productId) {

	        String sql =
	                "SELECT product_id FROM products " +
	                "WHERE product_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps =
	                     con.prepareStatement(sql)) {

	            ps.setInt(1, productId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error validating product ID: "
	                    + e.getMessage()
	            );

	            return false;
	        }
	    }

	    // =========================================================
	    // QUANTITY VALIDATION
	    // =========================================================

	    // Checks whether quantity is positive
	    public static boolean validateQuantity(int quantity) {

	        return quantity > 0;
	    }
	

}

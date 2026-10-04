package Library_management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Validation {
	

	    // =========================================================
	    // BASIC FIELD VALIDATION
	    // =========================================================

	    // Checks whether a field is not empty
	    public static boolean validateNotEmpty(String value) {
	        return value != null && !value.trim().isEmpty();
	    }

	    // Checks whether name contains only alphabets and spaces
	    public static boolean validateName(String name) {
	        return name != null && name.matches("[a-zA-Z ]+");
	    }

	    // Checks whether email format is valid
	    public static boolean validateEmail(String email) {
	        return email != null &&
	               email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	    }

	    // Checks whether phone contains exactly 10 digits
	    public static boolean validatePhone(String phone) {
	        return phone != null && phone.matches("[0-9]{10}");
	    }

	    // Checks whether quantity is valid
	    public static boolean validateQuantity(int quantity) {
	        return quantity >= 0;
	    }

	    // =========================================================
	    // BOOK VALIDATION
	    // =========================================================

	    // Checks whether book title is valid
	    public static boolean validateBookTitle(String title) {
	        return validateNotEmpty(title);
	    }

	    // Checks whether author name is valid
	    public static boolean validateAuthor(String author) {
	        return validateName(author);
	    }

	    // Checks whether category is valid
	    public static boolean validateCategory(String category) {
	        return validateNotEmpty(category);
	    }

	    // =========================================================
	    // MEMBER ID VALIDATION
	    // =========================================================

	    // Checks whether member ID exists
	    public static boolean validateMemberId(int memberId) {

	        String sql = "SELECT member_id FROM members WHERE member_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, memberId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println("Error validating member ID: "
	                    + e.getMessage());

	            return false;
	        }
	    }

	    // =========================================================
	    // BOOK ID VALIDATION
	    // =========================================================

	    // Checks whether book ID exists
	    public static boolean validateBookId(int bookId) {

	        String sql = "SELECT book_id FROM books WHERE book_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, bookId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println("Error validating book ID: "
	                    + e.getMessage());

	            return false;
	        }
	    }

	    // =========================================================
	    // ISSUE ID VALIDATION
	    // =========================================================

	    // Checks whether issue ID exists
	    public static boolean validateIssueId(int issueId) {

	        String sql = "SELECT issue_id FROM book_issues WHERE issue_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, issueId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println("Error validating issue ID: "
	                    + e.getMessage());

	            return false;
	        }
	    }
	
}

package Hotel_management_System;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

	    // =========================================================
	    // ROOM VALIDATION
	    // =========================================================

	    // Checks whether room type is valid
	    public static boolean validateRoomType(String roomType) {

	        return roomType != null &&
	                (roomType.equalsIgnoreCase("Single")
	                || roomType.equalsIgnoreCase("Double")
	                || roomType.equalsIgnoreCase("Deluxe"));
	    }

	    // Checks the standard price for each room type
	    public static boolean validateRoomPrice(
	            String roomType, double price) {

	        if (roomType.equalsIgnoreCase("Single")) {
	            return price == 1500;
	        }

	        if (roomType.equalsIgnoreCase("Double")) {
	            return price == 2500;
	        }

	        if (roomType.equalsIgnoreCase("Deluxe")) {
	            return price == 3500;
	        }

	        return false;
	    }

	    // =========================================================
	    // CUSTOMER ID VALIDATION
	    // =========================================================

	    // Checks whether customer ID exists
	    public static boolean validateCustomerId(int customerId) {

	        String sql =
	                "SELECT customer_id FROM customers " +
	                "WHERE customer_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps =
	                     con.prepareStatement(sql)) {

	            ps.setInt(1, customerId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error validating customer ID: "
	                    + e.getMessage()
	            );

	            return false;
	        }
	    }

	    // =========================================================
	    // ROOM ID VALIDATION
	    // =========================================================

	    // Checks whether room ID exists
	    public static boolean validateRoomId(int roomId) {

	        String sql =
	                "SELECT room_id FROM rooms WHERE room_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps =
	                     con.prepareStatement(sql)) {

	            ps.setInt(1, roomId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error validating room ID: "
	                    + e.getMessage()
	            );

	            return false;
	        }
	    }

	    // =========================================================
	    // BOOKING ID VALIDATION
	    // =========================================================

	    // Checks whether booking ID exists
	    public static boolean validateBookingId(int bookingId) {

	        String sql =
	                "SELECT booking_id FROM bookings " +
	                "WHERE booking_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps =
	                     con.prepareStatement(sql)) {

	            ps.setInt(1, bookingId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error validating booking ID: "
	                    + e.getMessage()
	            );

	            return false;
	        }
	    }

	    // =========================================================
	    // DATE VALIDATION
	    // =========================================================

	    // Checks whether check-out is after check-in
	    public static boolean validateBookingDates(
	            LocalDate checkIn,
	            LocalDate checkOut) {

	        return checkIn != null &&
	                checkOut != null &&
	                checkOut.isAfter(checkIn);
	    }
	

}

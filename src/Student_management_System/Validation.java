package Student_management_System;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validation {
	


	    // =========================================================
	    // BASIC FIELD VALIDATION
	    // =========================================================

	    // Checks whether a field is empty
	    public static boolean validateNotEmpty(String value) {

	        return value != null && !value.trim().isEmpty();
	    }

	    // Checks whether the name contains only alphabets and spaces
	    public static boolean validateName(String name) {

	        return name != null && name.matches("[a-zA-Z ]+");
	    }

	    // Checks whether the email format is valid
	    public static boolean validateEmail(String email) {

	        return email != null &&
	               email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	    }

	    // Checks whether the phone contains exactly 10 digits
	    public static boolean validatePhone(String phone) {

	        return phone != null && phone.matches("[0-9]{10}");
	    }

	    // Checks whether age is valid
	    public static boolean validateAge(int age) {

	        return age >= 15;
	    }

	    // Checks whether marks are between 0 and 100
	    public static boolean validateMarks(double marks) {

	        return marks >= 0 && marks <= 100;
	    }

	    // Checks whether course duration is positive
	    public static boolean validateDuration(int duration) {

	        return duration > 0;
	    }

	    // Checks whether course fee is valid
	    public static boolean validateFee(double fee) {

	        return fee >= 0;
	    }

	    // =========================================================
	    // STUDENT ID VALIDATION
	    // =========================================================

	    // Checks whether the student ID exists in the database
	    public static boolean validateStudentId(int studentId) {

	        String sql = "SELECT student_id FROM students WHERE student_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, studentId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println("Error validating student ID: "
	                    + e.getMessage());

	            return false;
	        }
	    }

	    // =========================================================
	    // COURSE ID VALIDATION
	    // =========================================================

	    // Checks whether the course ID exists in the database
	    public static boolean validateCourseId(int courseId) {

	        String sql = "SELECT course_id FROM courses WHERE course_id=?";

	        try (Connection con = tablesCreation.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, courseId);

	            ResultSet rs = ps.executeQuery();

	            return rs.next();

	        } catch (SQLException e) {

	            System.out.println("Error validating course ID: "
	                    + e.getMessage());

	            return false;
	        }
	    }

	    // =========================================================
	    // ATTENDANCE VALIDATION
	    // =========================================================

	    // Checks whether attendance status is valid
	    public static boolean validateAttendanceStatus(String status) {

	        return status != null &&
	               (status.equalsIgnoreCase("Present")
	               || status.equalsIgnoreCase("Absent"));
	    }

	    // =========================================================
	    // GENDER VALIDATION
	    // =========================================================

	    // Checks whether gender value is valid
	    public static boolean validateGender(String gender) {

	        return gender != null &&
	               (gender.equalsIgnoreCase("Male")
	               || gender.equalsIgnoreCase("Female")
	               || gender.equalsIgnoreCase("Other"));
	    }
	

}

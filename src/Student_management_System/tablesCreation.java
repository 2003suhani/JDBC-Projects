package Student_management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class tablesCreation {

	    // Database connection details
	    static final String URL = "jdbc:mysql://localhost:3306/jdbc_practice";
	    static final String USER = "root";
	    static final String PASSWORD = "Suhani@2003";

	    // =========================================================
	    // DATABASE CONNECTION
	    // =========================================================

	    // Creates and returns a connection with MySQL
	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }

	    // =========================================================
	    // TABLE CREATION
	    // =========================================================

	    // Creates the students table
	    public static void createStudentsTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS students (" +
	                "student_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "student_name VARCHAR(100) NOT NULL, " +
	                "email VARCHAR(100) UNIQUE NOT NULL, " +
	                "phone VARCHAR(15) UNIQUE NOT NULL, " +
	                "age INT CHECK(age >= 15), " +
	                "gender VARCHAR(10), " +
	                "course VARCHAR(100))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Students table created successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error creating students table: "
	                    + e.getMessage());
	        }
	    }

	    // Creates the courses table
	    public static void createCoursesTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS courses (" +
	                "course_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "course_name VARCHAR(100) NOT NULL, " +
	                "duration INT CHECK(duration > 0), " +
	                "fee DECIMAL(10,2) CHECK(fee >= 0))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Courses table created successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error creating courses table: "
	                    + e.getMessage());
	        }
	    }

	    // Creates the enrollments table
	    public static void createEnrollmentsTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS enrollments (" +
	                "enrollment_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "student_id INT NOT NULL, " +
	                "course_id INT NOT NULL, " +
	                "enrollment_date DATE NOT NULL, " +
	                "FOREIGN KEY(student_id) REFERENCES students(student_id), " +
	                "FOREIGN KEY(course_id) REFERENCES courses(course_id))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Enrollments table created successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error creating enrollments table: "
	                    + e.getMessage());
	        }
	    }

	    // Creates the attendance table
	    public static void createAttendanceTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS attendance (" +
	                "attendance_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "student_id INT NOT NULL, " +
	                "attendance_date DATE NOT NULL, " +
	                "status VARCHAR(10) CHECK(status IN ('Present','Absent')), " +
	                "FOREIGN KEY(student_id) REFERENCES students(student_id))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Attendance table created successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error creating attendance table: "
	                    + e.getMessage());
	        }
	    }

	    // Creates the marks table
	    public static void createMarksTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS marks (" +
	                "mark_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "student_id INT NOT NULL, " +
	                "subject VARCHAR(100) NOT NULL, " +
	                "marks DECIMAL(5,2) CHECK(marks BETWEEN 0 AND 100), " +
	                "FOREIGN KEY(student_id) REFERENCES students(student_id))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Marks table created successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error creating marks table: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // STUDENT OPERATIONS
	    // =========================================================

	    // Adds a new student
	    public static void addStudent(Scanner sc) {

	        System.out.print("Enter student name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter phone: ");
	        String phone = sc.nextLine();

	        System.out.print("Enter age: ");
	        int age = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter gender: ");
	        String gender = sc.nextLine();

	        System.out.print("Enter course: ");
	        String course = sc.nextLine();

	        String sql = "INSERT INTO students " +
	                "(student_name, email, phone, age, gender, course) " +
	                "VALUES (?, ?, ?, ?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);
	            ps.setInt(4, age);
	            ps.setString(5, gender);
	            ps.setString(6, course);

	            ps.executeUpdate();

	            System.out.println("Student added successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error adding student: "
	                    + e.getMessage());
	        }
	    }

	    // Displays all students
	    public static void fetchStudents() {

	        String sql = "SELECT * FROM students";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println("\n========== STUDENT DETAILS ==========");

	            while (rs.next()) {

	                System.out.println(
	                        "ID: " + rs.getInt("student_id")
	                        + " | Name: " + rs.getString("student_name")
	                        + " | Email: " + rs.getString("email")
	                        + " | Phone: " + rs.getString("phone")
	                        + " | Age: " + rs.getInt("age")
	                        + " | Gender: " + rs.getString("gender")
	                        + " | Course: " + rs.getString("course")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error fetching students: "
	                    + e.getMessage());
	        }
	    }

	    // Updates an existing student
	    public static void updateStudent(Scanner sc) {

	        System.out.print("Enter student ID: ");
	        int id = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter new student name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter new email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter new phone: ");
	        String phone = sc.nextLine();

	        System.out.print("Enter new age: ");
	        int age = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter new gender: ");
	        String gender = sc.nextLine();

	        System.out.print("Enter new course: ");
	        String course = sc.nextLine();

	        String sql = "UPDATE students SET " +
	                "student_name=?, email=?, phone=?, age=?, " +
	                "gender=?, course=? WHERE student_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);
	            ps.setInt(4, age);
	            ps.setString(5, gender);
	            ps.setString(6, course);
	            ps.setInt(7, id);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {
	                System.out.println("Student updated successfully.");
	            } else {
	                System.out.println("Student ID not found.");
	            }

	        } catch (SQLException e) {
	            System.out.println("Error updating student: "
	                    + e.getMessage());
	        }
	    }

	    // Deletes a student
	    public static void deleteStudent(Scanner sc) {

	        System.out.print("Enter student ID: ");
	        int id = sc.nextInt();
	        sc.nextLine();

	        String sql = "DELETE FROM students WHERE student_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, id);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {
	                System.out.println("Student deleted successfully.");
	            } else {
	                System.out.println("Student ID not found.");
	            }

	        } catch (SQLException e) {
	            System.out.println("Error deleting student: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // COURSE OPERATIONS
	    // =========================================================

	    // Adds a new course
	    public static void addCourse(Scanner sc) {

	        System.out.print("Enter course name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter duration in months: ");
	        int duration = sc.nextInt();

	        System.out.print("Enter course fee: ");
	        double fee = sc.nextDouble();
	        sc.nextLine();

	        String sql = "INSERT INTO courses(course_name, duration, fee) "
	                + "VALUES (?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setInt(2, duration);
	            ps.setDouble(3, fee);

	            ps.executeUpdate();

	            System.out.println("Course added successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error adding course: "
	                    + e.getMessage());
	        }
	    }

	    // Displays all courses
	    public static void fetchCourses() {

	        String sql = "SELECT * FROM courses";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println("\n========== COURSE DETAILS ==========");

	            while (rs.next()) {

	                System.out.println(
	                        "ID: " + rs.getInt("course_id")
	                        + " | Course: " + rs.getString("course_name")
	                        + " | Duration: " + rs.getInt("duration")
	                        + " months | Fee: " + rs.getDouble("fee")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error fetching courses: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // ENROLLMENT OPERATIONS
	    // =========================================================

	    // Enrolls a student into a course
	    public static void enrollStudent(Scanner sc) {

	        System.out.print("Enter student ID: ");
	        int studentId = sc.nextInt();

	        System.out.print("Enter course ID: ");
	        int courseId = sc.nextInt();
	        sc.nextLine();

	        String sql = "INSERT INTO enrollments " +
	                "(student_id, course_id, enrollment_date) " +
	                "VALUES (?, ?, CURDATE())";

	        Connection con = null;

	        try {

	            con = getConnection();

	            // Start transaction
	            con.setAutoCommit(false);

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setInt(1, studentId);
	            ps.setInt(2, courseId);

	            ps.executeUpdate();

	            // Save the transaction permanently
	            con.commit();

	            System.out.println("Student enrolled successfully.");

	        } catch (SQLException e) {

	            // Undo transaction if an error occurs
	            try {
	                if (con != null) {
	                    con.rollback();
	                }
	            } catch (SQLException ex) {
	                System.out.println("Rollback failed.");
	            }

	            System.out.println("Enrollment failed: "
	                    + e.getMessage());

	        } finally {

	            // Close the connection
	            try {
	                if (con != null) {
	                    con.close();
	                }
	            } catch (SQLException e) {
	                System.out.println("Connection closing error.");
	            }
	        }
	    }

	    // =========================================================
	    // ATTENDANCE OPERATIONS
	    // =========================================================

	    // Adds attendance for a student
	    public static void addAttendance(Scanner sc) {

	        System.out.print("Enter student ID: ");
	        int studentId = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter attendance date (YYYY-MM-DD): ");
	        String date = sc.nextLine();

	        System.out.print("Enter status (Present/Absent): ");
	        String status = sc.nextLine();

	        String sql = "INSERT INTO attendance " +
	                "(student_id, attendance_date, status) " +
	                "VALUES (?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, studentId);
	            ps.setString(2, date);
	            ps.setString(3, status);

	            ps.executeUpdate();

	            System.out.println("Attendance added successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error adding attendance: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // MARKS OPERATIONS
	    // =========================================================

	    // Adds marks for a student
	    public static void addMarks(Scanner sc) {

	        System.out.print("Enter student ID: ");
	        int studentId = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter subject: ");
	        String subject = sc.nextLine();

	        System.out.print("Enter marks: ");
	        double marks = sc.nextDouble();
	        sc.nextLine();

	        String sql = "INSERT INTO marks(student_id, subject, marks) "
	                + "VALUES (?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, studentId);
	            ps.setString(2, subject);
	            ps.setDouble(3, marks);

	            ps.executeUpdate();

	            System.out.println("Marks added successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error adding marks: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // JOIN QUERY
	    // =========================================================

	    // Displays student and course details using JOIN
	    public static void fetchStudentCourses() {

	        String sql = "SELECT s.student_id, s.student_name, "
	                + "c.course_name, e.enrollment_date "
	                + "FROM students s "
	                + "JOIN enrollments e ON s.student_id = e.student_id "
	                + "JOIN courses c ON e.course_id = c.course_id";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println(
	                    "\n========== STUDENT COURSE DETAILS =========="
	            );

	            while (rs.next()) {

	                System.out.println(
	                        "Student ID: " + rs.getInt("student_id")
	                        + " | Name: " + rs.getString("student_name")
	                        + " | Course: " + rs.getString("course_name")
	                        + " | Enrollment Date: "
	                        + rs.getDate("enrollment_date")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error fetching student courses: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // RESULT GENERATION
	    // =========================================================

	    // Generates result using average marks
	    public static void generateResult(Scanner sc) {

	        System.out.print("Enter student ID: ");
	        int studentId = sc.nextInt();
	        sc.nextLine();

	        String sql = "SELECT AVG(marks) AS average_marks "
	                + "FROM marks WHERE student_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, studentId);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {

	                double average = rs.getDouble("average_marks");

	                System.out.println("Average Marks: " + average);

	                if (average >= 40) {
	                    System.out.println("Result: PASS");
	                } else {
	                    System.out.println("Result: FAIL");
	                }
	            }

	        } catch (SQLException e) {
	            System.out.println("Error generating result: "
	                    + e.getMessage());
	        }
	    }
	
}

package Library_management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
public class tablesCreation {


	    // Database connection details
	    static final String URL = "jdbc:mysql://localhost:3306/jdbc_practice";
	    static final String USER = "root";
	    static final String PASSWORD = "Suhani@2003";

	    // =========================================================
	    // DATABASE CONNECTION
	    // =========================================================

	    // Creates and returns a MySQL connection
	    public static Connection getConnection() throws SQLException {
	        return java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
	    }

	    // =========================================================
	    // TABLE CREATION
	    // =========================================================

	    // Creates the books table
	    public static void createBooksTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS books (" +
	                "book_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "title VARCHAR(150) NOT NULL, " +
	                "author VARCHAR(100) NOT NULL, " +
	                "category VARCHAR(100), " +
	                "quantity INT CHECK(quantity >= 0))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Books table created successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error creating books table: "
	                    + e.getMessage());
	        }
	    }

	    // Creates the members table
	    public static void createMembersTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS members (" +
	                "member_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "member_name VARCHAR(100) NOT NULL, " +
	                "email VARCHAR(100) UNIQUE NOT NULL, " +
	                "phone VARCHAR(15) UNIQUE NOT NULL)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Members table created successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error creating members table: "
	                    + e.getMessage());
	        }
	    }

	    // Creates the book issues table
	    public static void createBookIssuesTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS book_issues (" +
	                "issue_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "book_id INT NOT NULL, " +
	                "member_id INT NOT NULL, " +
	                "issue_date DATE NOT NULL, " +
	                "return_date DATE, " +
	                "fine DECIMAL(10,2) DEFAULT 0, " +
	                "FOREIGN KEY(book_id) REFERENCES books(book_id), " +
	                "FOREIGN KEY(member_id) REFERENCES members(member_id))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Book issues table created successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error creating book issues table: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // BOOK OPERATIONS
	    // =========================================================

	    // Adds a new book
	    public static void addBook(Scanner sc) {

	        System.out.print("Enter book title: ");
	        String title = sc.nextLine();

	        System.out.print("Enter author name: ");
	        String author = sc.nextLine();

	        System.out.print("Enter category: ");
	        String category = sc.nextLine();

	        System.out.print("Enter quantity: ");
	        int quantity = sc.nextInt();
	        sc.nextLine();

	        String sql = "INSERT INTO books(title, author, category, quantity) "
	                + "VALUES (?, ?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, title);
	            ps.setString(2, author);
	            ps.setString(3, category);
	            ps.setInt(4, quantity);

	            ps.executeUpdate();

	            System.out.println("Book added successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error adding book: "
	                    + e.getMessage());
	        }
	    }

	    // Displays all books
	    public static void fetchBooks() {

	        String sql = "SELECT * FROM books";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println("\n========== BOOK DETAILS ==========");

	            while (rs.next()) {

	                System.out.println(
	                        "ID: " + rs.getInt("book_id")
	                        + " | Title: " + rs.getString("title")
	                        + " | Author: " + rs.getString("author")
	                        + " | Category: " + rs.getString("category")
	                        + " | Quantity: " + rs.getInt("quantity")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error fetching books: "
	                    + e.getMessage());
	        }
	    }

	    // Searches books by title
	    public static void searchBook(Scanner sc) {

	        System.out.print("Enter book title to search: ");
	        String title = sc.nextLine();

	        String sql = "SELECT * FROM books WHERE title LIKE ?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, "%" + title + "%");

	            ResultSet rs = ps.executeQuery();

	            System.out.println("\n========== SEARCH RESULT ==========");

	            while (rs.next()) {

	                System.out.println(
	                        "ID: " + rs.getInt("book_id")
	                        + " | Title: " + rs.getString("title")
	                        + " | Author: " + rs.getString("author")
	                        + " | Category: " + rs.getString("category")
	                        + " | Quantity: " + rs.getInt("quantity")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error searching book: "
	                    + e.getMessage());
	        }
	    }

	    // Updates book details
	    public static void updateBook(Scanner sc) {

	        System.out.print("Enter book ID: ");
	        int id = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter new title: ");
	        String title = sc.nextLine();

	        System.out.print("Enter new author: ");
	        String author = sc.nextLine();

	        System.out.print("Enter new category: ");
	        String category = sc.nextLine();

	        System.out.print("Enter new quantity: ");
	        int quantity = sc.nextInt();
	        sc.nextLine();

	        String sql = "UPDATE books SET title=?, author=?, "
	                + "category=?, quantity=? WHERE book_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, title);
	            ps.setString(2, author);
	            ps.setString(3, category);
	            ps.setInt(4, quantity);
	            ps.setInt(5, id);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {
	                System.out.println("Book updated successfully.");
	            } else {
	                System.out.println("Book ID not found.");
	            }

	        } catch (SQLException e) {
	            System.out.println("Error updating book: "
	                    + e.getMessage());
	        }
	    }

	    // Deletes a book
	    public static void deleteBook(Scanner sc) {

	        System.out.print("Enter book ID: ");
	        int id = sc.nextInt();
	        sc.nextLine();

	        String sql = "DELETE FROM books WHERE book_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, id);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {
	                System.out.println("Book deleted successfully.");
	            } else {
	                System.out.println("Book ID not found.");
	            }

	        } catch (SQLException e) {
	            System.out.println("Error deleting book: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // MEMBER OPERATIONS
	    // =========================================================

	    // Adds a new library member
	    public static void addMember(Scanner sc) {

	        System.out.print("Enter member name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter phone: ");
	        String phone = sc.nextLine();

	        String sql = "INSERT INTO members(member_name, email, phone) "
	                + "VALUES (?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);

	            ps.executeUpdate();

	            System.out.println("Member added successfully.");

	        } catch (SQLException e) {
	            System.out.println("Error adding member: "
	                    + e.getMessage());
	        }
	    }

	    // Displays all library members
	    public static void fetchMembers() {

	        String sql = "SELECT * FROM members";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println("\n========== MEMBER DETAILS ==========");

	            while (rs.next()) {

	                System.out.println(
	                        "ID: " + rs.getInt("member_id")
	                        + " | Name: " + rs.getString("member_name")
	                        + " | Email: " + rs.getString("email")
	                        + " | Phone: " + rs.getString("phone")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error fetching members: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // ISSUE BOOK
	    // =========================================================

	    // Issues a book to a member using transaction
	    public static void issueBook(Scanner sc) {

	        System.out.print("Enter book ID: ");
	        int bookId = sc.nextInt();

	        System.out.print("Enter member ID: ");
	        int memberId = sc.nextInt();
	        sc.nextLine();

	        Connection con = null;

	        try {

	            con = getConnection();

	            // Start transaction
	            con.setAutoCommit(false);

	            // Check book availability
	            String checkSql =
	                    "SELECT quantity FROM books WHERE book_id=?";

	            PreparedStatement checkPs =
	                    con.prepareStatement(checkSql);

	            checkPs.setInt(1, bookId);

	            ResultSet rs = checkPs.executeQuery();

	            if (!rs.next()) {

	                System.out.println("Book ID not found.");
	                con.rollback();
	                return;
	            }

	            int quantity = rs.getInt("quantity");

	            if (quantity <= 0) {

	                System.out.println("Book is not available.");
	                con.rollback();
	                return;
	            }

	            // Insert issue record
	            String issueSql =
	                    "INSERT INTO book_issues " +
	                    "(book_id, member_id, issue_date) " +
	                    "VALUES (?, ?, CURDATE())";

	            PreparedStatement issuePs =
	                    con.prepareStatement(issueSql);

	            issuePs.setInt(1, bookId);
	            issuePs.setInt(2, memberId);

	            issuePs.executeUpdate();

	            // Reduce book quantity
	            String updateSql =
	                    "UPDATE books SET quantity=quantity-1 " +
	                    "WHERE book_id=?";

	            PreparedStatement updatePs =
	                    con.prepareStatement(updateSql);

	            updatePs.setInt(1, bookId);

	            updatePs.executeUpdate();

	            // Save transaction
	            con.commit();

	            System.out.println("Book issued successfully.");

	        } catch (SQLException e) {

	            try {

	                if (con != null) {
	                    con.rollback();
	                }

	            } catch (SQLException ex) {

	                System.out.println("Rollback failed.");
	            }

	            System.out.println("Book issue failed: "
	                    + e.getMessage());

	        } finally {

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
	    // RETURN BOOK
	    // =========================================================

	    // Returns a book and increases its quantity
	    public static void returnBook(Scanner sc) {

	        System.out.print("Enter issue ID: ");
	        int issueId = sc.nextInt();
	        sc.nextLine();

	        Connection con = null;

	        try {

	            con = getConnection();

	            // Start transaction
	            con.setAutoCommit(false);

	            // Find the issued book
	            String findSql =
	                    "SELECT book_id, issue_date, return_date " +
	                    "FROM book_issues WHERE issue_id=?";

	            PreparedStatement findPs =
	                    con.prepareStatement(findSql);

	            findPs.setInt(1, issueId);

	            ResultSet rs = findPs.executeQuery();

	            if (!rs.next()) {

	                System.out.println("Issue ID not found.");
	                con.rollback();
	                return;
	            }

	            int bookId = rs.getInt("book_id");

	            if (rs.getDate("return_date") != null) {

	                System.out.println("Book has already been returned.");
	                con.rollback();
	                return;
	            }

	            // Update return date
	            String returnSql =
	                    "UPDATE book_issues SET return_date=CURDATE() "
	                    + "WHERE issue_id=?";

	            PreparedStatement returnPs =
	                    con.prepareStatement(returnSql);

	            returnPs.setInt(1, issueId);

	            returnPs.executeUpdate();

	            // Increase book quantity
	            String updateSql =
	                    "UPDATE books SET quantity=quantity+1 "
	                    + "WHERE book_id=?";

	            PreparedStatement updatePs =
	                    con.prepareStatement(updateSql);

	            updatePs.setInt(1, bookId);

	            updatePs.executeUpdate();

	            // Save transaction
	            con.commit();

	            System.out.println("Book returned successfully.");

	        } catch (SQLException e) {

	            try {

	                if (con != null) {
	                    con.rollback();
	                }

	            } catch (SQLException ex) {

	                System.out.println("Rollback failed.");
	            }

	            System.out.println("Book return failed: "
	                    + e.getMessage());

	        } finally {

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
	    // ISSUE RECORDS
	    // =========================================================

	    // Displays book issue records using JOIN
	    public static void fetchIssues() {

	        String sql =
	                "SELECT bi.issue_id, b.title, m.member_name, "
	                + "bi.issue_date, bi.return_date, bi.fine "
	                + "FROM book_issues bi "
	                + "JOIN books b ON bi.book_id=b.book_id "
	                + "JOIN members m ON bi.member_id=m.member_id";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println(
	                    "\n========== BOOK ISSUE DETAILS =========="
	            );

	            while (rs.next()) {

	                System.out.println(
	                        "Issue ID: " + rs.getInt("issue_id")
	                        + " | Book: " + rs.getString("title")
	                        + " | Member: "
	                        + rs.getString("member_name")
	                        + " | Issue Date: "
	                        + rs.getDate("issue_date")
	                        + " | Return Date: "
	                        + rs.getDate("return_date")
	                        + " | Fine: "
	                        + rs.getDouble("fine")
	                );
	            }

	        } catch (SQLException e) {
	            System.out.println("Error fetching issue records: "
	                    + e.getMessage());
	        }
	    }

	    // =========================================================
	    // FINE CALCULATION
	    // =========================================================

	    // Calculates fine based on number of late days
	    public static void calculateFine(Scanner sc) {

	        System.out.print("Enter issue ID: ");
	        int issueId = sc.nextInt();
	        sc.nextLine();

	        String sql =
	                "SELECT issue_date, return_date "
	                + "FROM book_issues WHERE issue_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, issueId);

	            ResultSet rs = ps.executeQuery();

	            if (!rs.next()) {

	                System.out.println("Issue ID not found.");
	                return;
	            }

	            LocalDate issueDate =
	                    rs.getDate("issue_date").toLocalDate();

	            LocalDate returnDate;

	            if (rs.getDate("return_date") != null) {
	                returnDate =
	                        rs.getDate("return_date").toLocalDate();
	            } else {
	                returnDate = LocalDate.now();
	            }

	            long totalDays =
	                    ChronoUnit.DAYS.between(issueDate, returnDate);

	            // Library allows 14 days without fine
	            long lateDays = totalDays - 14;

	            if (lateDays > 0) {

	                double fine = lateDays * 5;

	                System.out.println("Late Days: " + lateDays);
	                System.out.println("Fine Amount: ₹" + fine);

	                // Store calculated fine
	                String updateSql =
	                        "UPDATE book_issues SET fine=? "
	                        + "WHERE issue_id=?";

	                try (PreparedStatement updatePs =
	                             con.prepareStatement(updateSql)) {

	                    updatePs.setDouble(1, fine);
	                    updatePs.setInt(2, issueId);

	                    updatePs.executeUpdate();
	                }

	            } else {

	                System.out.println("No fine applicable.");

	                String updateSql =
	                        "UPDATE book_issues SET fine=0 "
	                        + "WHERE issue_id=?";

	                try (PreparedStatement updatePs =
	                             con.prepareStatement(updateSql)) {

	                    updatePs.setDouble(1, 0);
	                    updatePs.setInt(2, issueId);

	                    updatePs.executeUpdate();
	                }
	            }

	        } catch (SQLException e) {
	            System.out.println("Error calculating fine: "
	                    + e.getMessage());
	        }
	    }
	
}

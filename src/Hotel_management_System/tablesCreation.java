package Hotel_management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
public class tablesCreation {

	    // =========================================================
	    // DATABASE CONNECTION
	    // =========================================================

	    static final String URL = "jdbc:mysql://localhost:3306/jdbc_practice";
	    static final String USER = "root";
	    static final String PASSWORD = "Suhani@2003";

	    // Creates and returns a MySQL connection
	    public static Connection getConnection() throws SQLException {

	        return DriverManager.getConnection(
	                URL, USER, PASSWORD
	        );
	    }

	    // =========================================================
	    // TABLE CREATION
	    // =========================================================

	    // Creates rooms table
	    public static void createRoomsTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS rooms (" +
	                "room_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "room_type VARCHAR(20) NOT NULL, " +
	                "price_per_day DECIMAL(10,2) NOT NULL, " +
	                "status VARCHAR(20) DEFAULT 'Available', " +
	                "CHECK(room_type IN ('Single','Double','Deluxe')), " +
	                "CHECK(status IN ('Available','Booked','Occupied')))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Rooms table created successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error creating rooms table: " + e.getMessage()
	            );
	        }
	    }

	    // Creates customers table
	    public static void createCustomersTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS customers (" +
	                "customer_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "customer_name VARCHAR(100) NOT NULL, " +
	                "email VARCHAR(100) UNIQUE NOT NULL, " +
	                "phone VARCHAR(15) UNIQUE NOT NULL)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Customers table created successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error creating customers table: " + e.getMessage()
	            );
	        }
	    }

	    // Creates bookings table
	    public static void createBookingsTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS bookings (" +
	                "booking_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "customer_id INT NOT NULL, " +
	                "room_id INT NOT NULL, " +
	                "check_in_date DATE NOT NULL, " +
	                "check_out_date DATE NOT NULL, " +
	                "bill_amount DECIMAL(10,2) NOT NULL, " +
	                "booking_status VARCHAR(20) DEFAULT 'Booked', " +
	                "actual_check_out_date DATE, " +
	                "FOREIGN KEY(customer_id) REFERENCES customers(customer_id), " +
	                "FOREIGN KEY(room_id) REFERENCES rooms(room_id), " +
	                "CHECK(check_out_date > check_in_date), " +
	                "CHECK(booking_status IN ('Booked','Checked-In','Checked-Out')))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Bookings table created successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error creating bookings table: " + e.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // ROOM OPERATIONS
	    // =========================================================

	    // Adds a new room
	    public static void addRoom(Scanner sc) {

	        System.out.print("Enter room type (Single/Double/Deluxe): ");
	        String roomType = sc.nextLine();

	        System.out.print("Enter price per day: ");
	        double price = sc.nextDouble();
	        sc.nextLine();

	        if (!Validation.validateRoomType(roomType)) {

	            System.out.println("Invalid room type.");
	            return;
	        }

	        if (!Validation.validateRoomPrice(roomType, price)) {

	            System.out.println(
	                    "Invalid price for the selected room type."
	            );
	            return;
	        }

	        String sql = "INSERT INTO rooms(room_type, price_per_day) " +
	                "VALUES (?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, roomType);
	            ps.setDouble(2, price);

	            ps.executeUpdate();

	            System.out.println("Room added successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error adding room: " + e.getMessage()
	            );
	        }
	    }

	    // Displays all rooms
	    public static void fetchRooms() {

	        String sql = "SELECT * FROM rooms";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println("\n========== ROOM DETAILS ==========");

	            while (rs.next()) {

	                System.out.println(
	                        "Room ID: " + rs.getInt("room_id")
	                        + " | Type: " + rs.getString("room_type")
	                        + " | Price: ₹"
	                        + rs.getDouble("price_per_day")
	                        + " | Status: "
	                        + rs.getString("status")
	                );
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error fetching rooms: " + e.getMessage()
	            );
	        }
	    }

	    // Updates room details
	    public static void updateRoom(Scanner sc) {

	        System.out.print("Enter room ID: ");
	        int roomId = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter new room type: ");
	        String roomType = sc.nextLine();

	        System.out.print("Enter new price per day: ");
	        double price = sc.nextDouble();
	        sc.nextLine();

	        if (!Validation.validateRoomType(roomType)) {

	            System.out.println("Invalid room type.");
	            return;
	        }

	        if (!Validation.validateRoomPrice(roomType, price)) {

	            System.out.println(
	                    "Invalid price for the selected room type."
	            );
	            return;
	        }

	        String sql = "UPDATE rooms SET room_type=?, " +
	                "price_per_day=? WHERE room_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, roomType);
	            ps.setDouble(2, price);
	            ps.setInt(3, roomId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {

	                System.out.println("Room updated successfully.");

	            } else {

	                System.out.println("Room ID not found.");
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error updating room: " + e.getMessage()
	            );
	        }
	    }

	    // Deletes a room
	    public static void deleteRoom(Scanner sc) {

	        System.out.print("Enter room ID: ");
	        int roomId = sc.nextInt();
	        sc.nextLine();

	        String sql = "DELETE FROM rooms WHERE room_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, roomId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {

	                System.out.println("Room deleted successfully.");

	            } else {

	                System.out.println("Room ID not found.");
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error deleting room: " + e.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // CUSTOMER OPERATIONS
	    // =========================================================

	    // Adds a new customer
	    public static void addCustomer(Scanner sc) {

	        System.out.print("Enter customer name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter phone: ");
	        String phone = sc.nextLine();

	        if (!Validation.validateName(name)) {

	            System.out.println("Invalid customer name.");
	            return;
	        }

	        if (!Validation.validateEmail(email)) {

	            System.out.println("Invalid email.");
	            return;
	        }

	        if (!Validation.validatePhone(phone)) {

	            System.out.println("Phone must contain 10 digits.");
	            return;
	        }

	        String sql = "INSERT INTO customers" +
	                "(customer_name, email, phone) VALUES (?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);

	            ps.executeUpdate();

	            System.out.println("Customer added successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error adding customer: " + e.getMessage()
	            );
	        }
	    }

	    // Displays all customers
	    public static void fetchCustomers() {

	        String sql = "SELECT * FROM customers";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println("\n========== CUSTOMER DETAILS ==========");

	            while (rs.next()) {

	                System.out.println(
	                        "ID: " + rs.getInt("customer_id")
	                        + " | Name: "
	                        + rs.getString("customer_name")
	                        + " | Email: "
	                        + rs.getString("email")
	                        + " | Phone: "
	                        + rs.getString("phone")
	                );
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error fetching customers: " + e.getMessage()
	            );
	        }
	    }

	    // Updates customer details
	    public static void updateCustomer(Scanner sc) {

	        System.out.print("Enter customer ID: ");
	        int customerId = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter new customer name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter new email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter new phone: ");
	        String phone = sc.nextLine();

	        if (!Validation.validateName(name)) {

	            System.out.println("Invalid customer name.");
	            return;
	        }

	        if (!Validation.validateEmail(email)) {

	            System.out.println("Invalid email.");
	            return;
	        }

	        if (!Validation.validatePhone(phone)) {

	            System.out.println("Phone must contain 10 digits.");
	            return;
	        }

	        String sql = "UPDATE customers SET customer_name=?, " +
	                "email=?, phone=? WHERE customer_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);
	            ps.setInt(4, customerId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {

	                System.out.println("Customer updated successfully.");

	            } else {

	                System.out.println("Customer ID not found.");
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error updating customer: " + e.getMessage()
	            );
	        }
	    }

	    // Deletes a customer
	    public static void deleteCustomer(Scanner sc) {

	        System.out.print("Enter customer ID: ");
	        int customerId = sc.nextInt();
	        sc.nextLine();

	        String sql = "DELETE FROM customers WHERE customer_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, customerId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {

	                System.out.println("Customer deleted successfully.");

	            } else {

	                System.out.println("Customer ID not found.");
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error deleting customer: " + e.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // BOOKING OPERATIONS
	    // =========================================================

	    // Creates a new room booking using a transaction
	    public static void addBooking(Scanner sc) {

	        System.out.print("Enter customer ID: ");
	        int customerId = sc.nextInt();

	        System.out.print("Enter room ID: ");
	        int roomId = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter check-in date (YYYY-MM-DD): ");
	        String checkInText = sc.nextLine();

	        System.out.print("Enter check-out date (YYYY-MM-DD): ");
	        String checkOutText = sc.nextLine();

	        if (!Validation.validateCustomerId(customerId)) {

	            System.out.println("Customer ID not found.");
	            return;
	        }

	        if (!Validation.validateRoomId(roomId)) {

	            System.out.println("Room ID not found.");
	            return;
	        }

	        LocalDate checkIn;
	        LocalDate checkOut;

	        try {

	            checkIn = LocalDate.parse(checkInText);
	            checkOut = LocalDate.parse(checkOutText);

	        } catch (Exception e) {

	            System.out.println(
	                    "Invalid date. Use YYYY-MM-DD format."
	            );
	            return;
	        }

	        if (!Validation.validateBookingDates(checkIn, checkOut)) {

	            System.out.println(
	                    "Check-out date must be after check-in date."
	            );
	            return;
	        }

	        Connection con = null;

	        try {

	            con = getConnection();

	            // Start transaction
	            con.setAutoCommit(false);

	            // Check room availability
	            String roomSql =
	                    "SELECT price_per_day, status FROM rooms " +
	                    "WHERE room_id=? FOR UPDATE";

	            PreparedStatement roomPs =
	                    con.prepareStatement(roomSql);

	            roomPs.setInt(1, roomId);

	            ResultSet rs = roomPs.executeQuery();

	            if (!rs.next()) {

	                System.out.println("Room not found.");
	                con.rollback();
	                return;
	            }

	            String status = rs.getString("status");

	            if (!status.equalsIgnoreCase("Available")) {

	                System.out.println("Room is not available.");
	                con.rollback();
	                return;
	            }

	            double pricePerDay =
	                    rs.getDouble("price_per_day");

	            long days =
	                    ChronoUnit.DAYS.between(checkIn, checkOut);

	            double billAmount = days * pricePerDay;

	            // Insert booking
	            String bookingSql =
	                    "INSERT INTO bookings " +
	                    "(customer_id, room_id, check_in_date, " +
	                    "check_out_date, bill_amount, booking_status) " +
	                    "VALUES (?, ?, ?, ?, ?, 'Booked')";

	            PreparedStatement bookingPs =
	                    con.prepareStatement(bookingSql);

	            bookingPs.setInt(1, customerId);
	            bookingPs.setInt(2, roomId);
	            bookingPs.setDate(
	                    3,
	                    java.sql.Date.valueOf(checkIn)
	            );
	            bookingPs.setDate(
	                    4,
	                    java.sql.Date.valueOf(checkOut)
	            );
	            bookingPs.setDouble(5, billAmount);

	            bookingPs.executeUpdate();

	            // Change room status
	            String updateRoomSql =
	                    "UPDATE rooms SET status='Booked' " +
	                    "WHERE room_id=?";

	            PreparedStatement updateRoomPs =
	                    con.prepareStatement(updateRoomSql);

	            updateRoomPs.setInt(1, roomId);

	            updateRoomPs.executeUpdate();

	            // Save transaction
	            con.commit();

	            System.out.println(
	                    "Booking created successfully."
	            );

	            System.out.println(
	                    "Number of days: " + days
	            );

	            System.out.println(
	                    "Total bill: ₹" + billAmount
	            );

	        } catch (SQLException e) {

	            try {

	                if (con != null) {
	                    con.rollback();
	                }

	            } catch (SQLException ex) {

	                System.out.println("Rollback failed.");
	            }

	            System.out.println(
	                    "Booking failed: " + e.getMessage()
	            );

	        } finally {

	            try {

	                if (con != null) {
	                    con.close();
	                }

	            } catch (SQLException e) {

	                System.out.println(
	                        "Connection closing error."
	                );
	            }
	        }
	    }

	    // Displays booking details
	    public static void fetchBookings() {

	        String sql =
	                "SELECT b.booking_id, c.customer_name, " +
	                "r.room_id, r.room_type, " +
	                "b.check_in_date, b.check_out_date, " +
	                "b.bill_amount, b.booking_status, " +
	                "b.actual_check_out_date " +
	                "FROM bookings b " +
	                "JOIN customers c " +
	                "ON b.customer_id=c.customer_id " +
	                "JOIN rooms r ON b.room_id=r.room_id";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println(
	                    "\n========== BOOKING DETAILS =========="
	            );

	            while (rs.next()) {

	                System.out.println(
	                        "Booking ID: "
	                        + rs.getInt("booking_id")
	                        + " | Customer: "
	                        + rs.getString("customer_name")
	                        + " | Room: "
	                        + rs.getInt("room_id")
	                        + " | Type: "
	                        + rs.getString("room_type")
	                        + " | Check-In: "
	                        + rs.getDate("check_in_date")
	                        + " | Check-Out: "
	                        + rs.getDate("check_out_date")
	                        + " | Bill: ₹"
	                        + rs.getDouble("bill_amount")
	                        + " | Status: "
	                        + rs.getString("booking_status")
	                        + " | Actual Check-Out: "
	                        + rs.getDate("actual_check_out_date")
	                );
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error fetching bookings: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // CHECK-IN
	    // =========================================================

	    // Changes booking and room status to Checked-In / Occupied
	    public static void checkIn(Scanner sc) {

	        System.out.print("Enter booking ID: ");
	        int bookingId = sc.nextInt();
	        sc.nextLine();

	        Connection con = null;

	        try {

	            con = getConnection();

	            String findSql =
	                    "SELECT room_id, booking_status " +
	                    "FROM bookings WHERE booking_id=?";

	            PreparedStatement findPs =
	                    con.prepareStatement(findSql);

	            findPs.setInt(1, bookingId);

	            ResultSet rs = findPs.executeQuery();

	            if (!rs.next()) {

	                System.out.println("Booking ID not found.");
	                return;
	            }

	            int roomId = rs.getInt("room_id");
	            String status = rs.getString("booking_status");

	            if (!status.equalsIgnoreCase("Booked")) {

	                System.out.println(
	                        "Only a booked reservation can be checked in."
	                );
	                return;
	            }

	            con.setAutoCommit(false);

	            // Update booking status
	            String bookingSql =
	                    "UPDATE bookings SET booking_status='Checked-In' " +
	                    "WHERE booking_id=?";

	            PreparedStatement bookingPs =
	                    con.prepareStatement(bookingSql);

	            bookingPs.setInt(1, bookingId);

	            bookingPs.executeUpdate();

	            // Update room status
	            String roomSql =
	                    "UPDATE rooms SET status='Occupied' " +
	                    "WHERE room_id=?";

	            PreparedStatement roomPs =
	                    con.prepareStatement(roomSql);

	            roomPs.setInt(1, roomId);

	            roomPs.executeUpdate();

	            con.commit();

	            System.out.println("Customer checked in successfully.");

	        } catch (SQLException e) {

	            try {

	                if (con != null) {
	                    con.rollback();
	                }

	            } catch (SQLException ex) {

	                System.out.println("Rollback failed.");
	            }

	            System.out.println(
	                    "Check-in failed: " + e.getMessage()
	            );

	        } finally {

	            try {

	                if (con != null) {
	                    con.close();
	                }

	            } catch (SQLException e) {

	                System.out.println(
	                        "Connection closing error."
	                );
	            }
	        }
	    }

	    // =========================================================
	    // CHECK-OUT
	    // =========================================================

	    // Checks out customer and makes room available
	    public static void checkOut(Scanner sc) {

	        System.out.print("Enter booking ID: ");
	        int bookingId = sc.nextInt();
	        sc.nextLine();

	        Connection con = null;

	        try {

	            con = getConnection();

	            String findSql =
	                    "SELECT room_id, booking_status, bill_amount " +
	                    "FROM bookings WHERE booking_id=?";

	            PreparedStatement findPs =
	                    con.prepareStatement(findSql);

	            findPs.setInt(1, bookingId);

	            ResultSet rs = findPs.executeQuery();

	            if (!rs.next()) {

	                System.out.println("Booking ID not found.");
	                return;
	            }

	            int roomId = rs.getInt("room_id");
	            String status = rs.getString("booking_status");
	            double bill = rs.getDouble("bill_amount");

	            if (!status.equalsIgnoreCase("Checked-In")) {

	                System.out.println(
	                        "Customer must be checked in before check-out."
	                );
	                return;
	            }

	            con.setAutoCommit(false);

	            // Update booking status and actual check-out date
	            String bookingSql =
	                    "UPDATE bookings SET " +
	                    "booking_status='Checked-Out', " +
	                    "actual_check_out_date=CURDATE() " +
	                    "WHERE booking_id=?";

	            PreparedStatement bookingPs =
	                    con.prepareStatement(bookingSql);

	            bookingPs.setInt(1, bookingId);

	            bookingPs.executeUpdate();

	            // Make room available
	            String roomSql =
	                    "UPDATE rooms SET status='Available' " +
	                    "WHERE room_id=?";

	            PreparedStatement roomPs =
	                    con.prepareStatement(roomSql);

	            roomPs.setInt(1, roomId);

	            roomPs.executeUpdate();

	            con.commit();

	            System.out.println("Customer checked out successfully.");
	            System.out.println("Final Bill: ₹" + bill);

	        } catch (SQLException e) {

	            try {

	                if (con != null) {
	                    con.rollback();
	                }

	            } catch (SQLException ex) {

	                System.out.println("Rollback failed.");
	            }

	            System.out.println(
	                    "Check-out failed: " + e.getMessage()
	            );

	        } finally {

	            try {

	                if (con != null) {
	                    con.close();
	                }

	            } catch (SQLException e) {

	                System.out.println(
	                        "Connection closing error."
	                );
	            }
	        }
	    }
	

}

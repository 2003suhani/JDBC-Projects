package Hotel_management_System;

import java.util.Scanner;
public class Main {

	    public static void main(String[] args) {

	        Scanner sc = new Scanner(System.in);

	        // Create all required tables
	        tablesCreation.createRoomsTable();
	        tablesCreation.createCustomersTable();
	        tablesCreation.createBookingsTable();

	        int choice;

	        do {

	            System.out.println("\n==========================================");
	            System.out.println("       HOTEL ROOM BOOKING SYSTEM");
	            System.out.println("==========================================");

	            System.out.println("\n----- ROOM MANAGEMENT -----");
	            System.out.println("1. Add Room");
	            System.out.println("2. View Rooms");
	            System.out.println("3. Update Room");
	            System.out.println("4. Delete Room");

	            System.out.println("\n----- CUSTOMER MANAGEMENT -----");
	            System.out.println("5. Add Customer");
	            System.out.println("6. View Customers");
	            System.out.println("7. Update Customer");
	            System.out.println("8. Delete Customer");

	            System.out.println("\n----- BOOKING MANAGEMENT -----");
	            System.out.println("9. Book Room");
	            System.out.println("10. View Bookings");
	            System.out.println("11. Check-In");
	            System.out.println("12. Check-Out");

	            System.out.println("\n13. Exit");

	            System.out.print("\nEnter your choice: ");
	            choice = sc.nextInt();
	            sc.nextLine();

	            switch (choice) {

	                // Room Management
	                case 1:
	                    tablesCreation.addRoom(sc);
	                    break;

	                case 2:
	                    tablesCreation.fetchRooms();
	                    break;

	                case 3:
	                    tablesCreation.updateRoom(sc);
	                    break;

	                case 4:
	                    tablesCreation.deleteRoom(sc);
	                    break;

	                // Customer Management
	                case 5:
	                    tablesCreation.addCustomer(sc);
	                    break;

	                case 6:
	                    tablesCreation.fetchCustomers();
	                    break;

	                case 7:
	                    tablesCreation.updateCustomer(sc);
	                    break;

	                case 8:
	                    tablesCreation.deleteCustomer(sc);
	                    break;

	                // Booking Management
	                case 9:
	                    tablesCreation.addBooking(sc);
	                    break;

	                case 10:
	                    tablesCreation.fetchBookings();
	                    break;

	                case 11:
	                    tablesCreation.checkIn(sc);
	                    break;

	                case 12:
	                    tablesCreation.checkOut(sc);
	                    break;

	                // Exit
	                case 13:
	                    System.out.println(
	                            "Thank you for using Hotel Room Booking System."
	                    );
	                    break;

	                default:
	                    System.out.println(
	                            "Invalid choice. Please enter 1 to 13."
	                    );
	            }

	        } while (choice != 13);

	        sc.close();
	    }
	
}

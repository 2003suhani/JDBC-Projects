package Library_management_System;

import java.util.Scanner;
public class Main {

	    public static void main(String[] args) {

	        Scanner sc = new Scanner(System.in);

	        // Create all required tables
	        tablesCreation.createBooksTable();
	        tablesCreation.createMembersTable();
	        tablesCreation.createBookIssuesTable();

	        int choice;

	        do {

	            System.out.println("\n==========================================");
	            System.out.println("        LIBRARY MANAGEMENT SYSTEM");
	            System.out.println("==========================================");

	            System.out.println("\n----- BOOK MANAGEMENT -----");
	            System.out.println("1. Add Book");
	            System.out.println("2. View Books");
	            System.out.println("3. Search Book");
	            System.out.println("4. Update Book");
	            System.out.println("5. Delete Book");

	            System.out.println("\n----- MEMBER MANAGEMENT -----");
	            System.out.println("6. Add Member");
	            System.out.println("7. View Members");

	            System.out.println("\n----- ISSUE MANAGEMENT -----");
	            System.out.println("8. Issue Book");
	            System.out.println("9. Return Book");
	            System.out.println("10. View Issue Records");

	            System.out.println("\n----- FINE MANAGEMENT -----");
	            System.out.println("11. Calculate Fine");

	            System.out.println("\n12. Exit");

	            System.out.print("\nEnter your choice: ");
	            choice = sc.nextInt();
	            sc.nextLine();

	            switch (choice) {

	                // Book Management
	                case 1:
	                    tablesCreation.addBook(sc);
	                    break;

	                case 2:
	                    tablesCreation.fetchBooks();
	                    break;

	                case 3:
	                    tablesCreation.searchBook(sc);
	                    break;

	                case 4:
	                    tablesCreation.updateBook(sc);
	                    break;

	                case 5:
	                    tablesCreation.deleteBook(sc);
	                    break;

	                // Member Management
	                case 6:
	                    tablesCreation.addMember(sc);
	                    break;

	                case 7:
	                    tablesCreation.fetchMembers();
	                    break;

	                // Issue Management
	                case 8:
	                    tablesCreation.issueBook(sc);
	                    break;

	                case 9:
	                    tablesCreation.returnBook(sc);
	                    break;

	                case 10:
	                    tablesCreation.fetchIssues();
	                    break;

	                // Fine Management
	                case 11:
	                    tablesCreation.calculateFine(sc);
	                    break;

	                // Exit
	                case 12:
	                    System.out.println(
	                            "Thank you for using Library Management System."
	                    );
	                    break;

	                default:
	                    System.out.println(
	                            "Invalid choice. Please enter 1 to 12."
	                    );
	            }

	        } while (choice != 12);

	        sc.close();
	    }
	
}

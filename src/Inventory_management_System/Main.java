package Inventory_management_System;
import java.util.Scanner;

public class Main {

	    public static void main(String[] args) {

	        Scanner sc = new Scanner(System.in);

	        // Create all required tables
	        tablesCreation.createSuppliersTable();
	        tablesCreation.createProductsTable();
	        tablesCreation.createSalesTable();

	        int choice;

	        do {

	            System.out.println("\n==========================================");
	            System.out.println("       INVENTORY MANAGEMENT SYSTEM");
	            System.out.println("==========================================");

	            System.out.println("\n----- SUPPLIER MANAGEMENT -----");
	            System.out.println("1. Add Supplier");
	            System.out.println("2. View Suppliers");
	            System.out.println("3. Update Supplier");
	            System.out.println("4. Delete Supplier");

	            System.out.println("\n----- PRODUCT MANAGEMENT -----");
	            System.out.println("5. Add Product");
	            System.out.println("6. View Products");
	            System.out.println("7. Update Product");
	            System.out.println("8. Update Stock");
	            System.out.println("9. Delete Product");
	            System.out.println("10. Batch Add Products");

	            System.out.println("\n----- SALES MANAGEMENT -----");
	            System.out.println("11. Record Sale");

	            System.out.println("\n----- REPORTS -----");
	            System.out.println("12. Stock Report");
	            System.out.println("13. Sales Report");

	            System.out.println("\n14. Exit");

	            System.out.print("\nEnter your choice: ");
	            choice = sc.nextInt();
	            sc.nextLine();

	            switch (choice) {

	                // Supplier Management
	                case 1:
	                    tablesCreation.addSupplier(sc);
	                    break;

	                case 2:
	                    tablesCreation.fetchSuppliers();
	                    break;

	                case 3:
	                    tablesCreation.updateSupplier(sc);
	                    break;

	                case 4:
	                    tablesCreation.deleteSupplier(sc);
	                    break;

	                // Product Management
	                case 5:
	                    tablesCreation.addProduct(sc);
	                    break;

	                case 6:
	                    tablesCreation.fetchProducts();
	                    break;

	                case 7:
	                    tablesCreation.updateProduct(sc);
	                    break;

	                case 8:
	                    tablesCreation.updateStock(sc);
	                    break;

	                case 9:
	                    tablesCreation.deleteProduct(sc);
	                    break;

	                case 10:
	                    tablesCreation.batchAddProducts(sc);
	                    break;

	                // Sales Management
	                case 11:
	                    tablesCreation.recordSale(sc);
	                    break;

	                // Reports
	                case 12:
	                    tablesCreation.stockReport();
	                    break;

	                case 13:
	                    tablesCreation.salesReport();
	                    break;

	                // Exit
	                case 14:
	                    System.out.println(
	                            "Thank you for using Inventory Management System."
	                    );
	                    break;

	                default:
	                    System.out.println(
	                            "Invalid choice. Please enter 1 to 14."
	                    );
	            }

	        } while (choice != 14);

	        sc.close();
	    }
	
}

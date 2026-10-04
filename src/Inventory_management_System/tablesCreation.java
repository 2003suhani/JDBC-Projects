package Inventory_management_System;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class tablesCreation {
	
    // =========================================================
	    // DATABASE CONNECTION
	    // =========================================================

	    static final String URL = "jdbc:mysql://localhost:3306/jdbc_practice";
	    static final String USER = "root";
	    static final String PASSWORD = "Suhani@2003";

	    // Creates and returns MySQL connection
	    public static Connection getConnection() throws SQLException {

	        return DriverManager.getConnection(
	                URL, USER, PASSWORD
	        );
	    }

	    // =========================================================
	    // TABLE CREATION
	    // =========================================================

	    // Creates suppliers table
	    public static void createSuppliersTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS suppliers (" +
	                "supplier_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "supplier_name VARCHAR(100) NOT NULL, " +
	                "email VARCHAR(100) UNIQUE NOT NULL, " +
	                "phone VARCHAR(15) UNIQUE NOT NULL)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Suppliers table created successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error creating suppliers table: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Creates products table
	    public static void createProductsTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS products (" +
	                "product_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "product_name VARCHAR(100) NOT NULL, " +
	                "category VARCHAR(100), " +
	                "price DECIMAL(10,2) CHECK(price >= 0), " +
	                "stock INT CHECK(stock >= 0), " +
	                "supplier_id INT NOT NULL, " +
	                "FOREIGN KEY(supplier_id) " +
	                "REFERENCES suppliers(supplier_id))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Products table created successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error creating products table: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Creates sales table
	    public static void createSalesTable() {

	        String sql = "CREATE TABLE IF NOT EXISTS sales (" +
	                "sale_id INT PRIMARY KEY AUTO_INCREMENT, " +
	                "product_id INT NOT NULL, " +
	                "quantity INT CHECK(quantity > 0), " +
	                "sale_price DECIMAL(10,2) CHECK(sale_price >= 0), " +
	                "sale_date DATE NOT NULL, " +
	                "FOREIGN KEY(product_id) " +
	                "REFERENCES products(product_id))";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.executeUpdate();

	            System.out.println("Sales table created successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error creating sales table: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // SUPPLIER OPERATIONS
	    // =========================================================

	    // Adds a supplier
	    public static void addSupplier(Scanner sc) {

	        System.out.print("Enter supplier name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter phone: ");
	        String phone = sc.nextLine();

	        if (!Validation.validateName(name)) {

	            System.out.println("Invalid supplier name.");
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

	        String sql =
	                "INSERT INTO suppliers " +
	                "(supplier_name, email, phone) " +
	                "VALUES (?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);

	            ps.executeUpdate();

	            System.out.println("Supplier added successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error adding supplier: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Displays all suppliers
	    public static void fetchSuppliers() {

	        String sql = "SELECT * FROM suppliers";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println(
	                    "\n========== SUPPLIER DETAILS =========="
	            );

	            while (rs.next()) {

	                System.out.println(
	                        "ID: " + rs.getInt("supplier_id")
	                        + " | Name: "
	                        + rs.getString("supplier_name")
	                        + " | Email: "
	                        + rs.getString("email")
	                        + " | Phone: "
	                        + rs.getString("phone")
	                );
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error fetching suppliers: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Updates supplier details
	    public static void updateSupplier(Scanner sc) {

	        System.out.print("Enter supplier ID: ");
	        int supplierId = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter new supplier name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter new email: ");
	        String email = sc.nextLine();

	        System.out.print("Enter new phone: ");
	        String phone = sc.nextLine();

	        if (!Validation.validateName(name)) {

	            System.out.println("Invalid supplier name.");
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

	        String sql =
	                "UPDATE suppliers SET supplier_name=?, " +
	                "email=?, phone=? WHERE supplier_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);
	            ps.setInt(4, supplierId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {

	                System.out.println(
	                        "Supplier updated successfully."
	                );

	            } else {

	                System.out.println("Supplier ID not found.");
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error updating supplier: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Deletes supplier
	    public static void deleteSupplier(Scanner sc) {

	        System.out.print("Enter supplier ID: ");
	        int supplierId = sc.nextInt();
	        sc.nextLine();

	        String sql =
	                "DELETE FROM suppliers WHERE supplier_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, supplierId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {

	                System.out.println(
	                        "Supplier deleted successfully."
	                );

	            } else {

	                System.out.println("Supplier ID not found.");
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error deleting supplier: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // PRODUCT OPERATIONS
	    // =========================================================

	    // Adds a product
	    public static void addProduct(Scanner sc) {

	        System.out.print("Enter product name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter category: ");
	        String category = sc.nextLine();

	        System.out.print("Enter price: ");
	        double price = sc.nextDouble();

	        System.out.print("Enter stock: ");
	        int stock = sc.nextInt();

	        System.out.print("Enter supplier ID: ");
	        int supplierId = sc.nextInt();
	        sc.nextLine();

	        if (!Validation.validateNotEmpty(name)) {

	            System.out.println("Product name cannot be empty.");
	            return;
	        }

	        if (!Validation.validatePrice(price)) {

	            System.out.println("Invalid price.");
	            return;
	        }

	        if (!Validation.validateStock(stock)) {

	            System.out.println("Invalid stock.");
	            return;
	        }

	        if (!Validation.validateSupplierId(supplierId)) {

	            System.out.println("Supplier ID not found.");
	            return;
	        }

	        String sql =
	                "INSERT INTO products " +
	                "(product_name, category, price, stock, supplier_id) " +
	                "VALUES (?, ?, ?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setString(2, category);
	            ps.setDouble(3, price);
	            ps.setInt(4, stock);
	            ps.setInt(5, supplierId);

	            ps.executeUpdate();

	            System.out.println("Product added successfully.");

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error adding product: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Displays products
	    public static void fetchProducts() {

	        String sql =
	                "SELECT p.product_id, p.product_name, " +
	                "p.category, p.price, p.stock, " +
	                "s.supplier_name " +
	                "FROM products p " +
	                "JOIN suppliers s " +
	                "ON p.supplier_id=s.supplier_id";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println(
	                    "\n========== PRODUCT DETAILS =========="
	            );

	            while (rs.next()) {

	                System.out.println(
	                        "ID: " + rs.getInt("product_id")
	                        + " | Product: "
	                        + rs.getString("product_name")
	                        + " | Category: "
	                        + rs.getString("category")
	                        + " | Price: ₹"
	                        + rs.getDouble("price")
	                        + " | Stock: "
	                        + rs.getInt("stock")
	                        + " | Supplier: "
	                        + rs.getString("supplier_name")
	                );
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error fetching products: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Updates product details
	    public static void updateProduct(Scanner sc) {

	        System.out.print("Enter product ID: ");
	        int productId = sc.nextInt();
	        sc.nextLine();

	        System.out.print("Enter new product name: ");
	        String name = sc.nextLine();

	        System.out.print("Enter new category: ");
	        String category = sc.nextLine();

	        System.out.print("Enter new price: ");
	        double price = sc.nextDouble();

	        System.out.print("Enter new supplier ID: ");
	        int supplierId = sc.nextInt();
	        sc.nextLine();

	        if (!Validation.validateNotEmpty(name)) {

	            System.out.println("Product name cannot be empty.");
	            return;
	        }

	        if (!Validation.validatePrice(price)) {

	            System.out.println("Invalid price.");
	            return;
	        }

	        if (!Validation.validateSupplierId(supplierId)) {

	            System.out.println("Supplier ID not found.");
	            return;
	        }

	        String sql =
	                "UPDATE products SET product_name=?, " +
	                "category=?, price=?, supplier_id=? " +
	                "WHERE product_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, name);
	            ps.setString(2, category);
	            ps.setDouble(3, price);
	            ps.setInt(4, supplierId);
	            ps.setInt(5, productId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {

	                System.out.println(
	                        "Product updated successfully."
	                );

	            } else {

	                System.out.println("Product ID not found.");
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error updating product: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Updates only product stock
	    public static void updateStock(Scanner sc) {

	        System.out.print("Enter product ID: ");
	        int productId = sc.nextInt();

	        System.out.print("Enter new stock: ");
	        int stock = sc.nextInt();
	        sc.nextLine();

	        if (!Validation.validateStock(stock)) {

	            System.out.println("Stock cannot be negative.");
	            return;
	        }

	        String sql =
	                "UPDATE products SET stock=? WHERE product_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, stock);
	            ps.setInt(2, productId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {

	                System.out.println(
	                        "Stock updated successfully."
	                );

	            } else {

	                System.out.println("Product ID not found.");
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error updating stock: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Deletes product
	    public static void deleteProduct(Scanner sc) {

	        System.out.print("Enter product ID: ");
	        int productId = sc.nextInt();
	        sc.nextLine();

	        String sql =
	                "DELETE FROM products WHERE product_id=?";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, productId);

	            int rows = ps.executeUpdate();

	            if (rows > 0) {

	                System.out.println(
	                        "Product deleted successfully."
	                );

	            } else {

	                System.out.println("Product ID not found.");
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error deleting product: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // BATCH PROCESSING
	    // =========================================================

	    // Adds multiple products using batch processing
	    public static void batchAddProducts(Scanner sc) {

	        System.out.print("Enter number of products: ");
	        int count = sc.nextInt();
	        sc.nextLine();

	        String sql =
	                "INSERT INTO products " +
	                "(product_name, category, price, stock, supplier_id) " +
	                "VALUES (?, ?, ?, ?, ?)";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            for (int i = 1; i <= count; i++) {

	                System.out.println("\nProduct " + i);

	                System.out.print("Enter product name: ");
	                String name = sc.nextLine();

	                System.out.print("Enter category: ");
	                String category = sc.nextLine();

	                System.out.print("Enter price: ");
	                double price = sc.nextDouble();

	                System.out.print("Enter stock: ");
	                int stock = sc.nextInt();

	                System.out.print("Enter supplier ID: ");
	                int supplierId = sc.nextInt();
	                sc.nextLine();

	                if (!Validation.validateNotEmpty(name)
	                        || !Validation.validatePrice(price)
	                        || !Validation.validateStock(stock)
	                        || !Validation.validateSupplierId(supplierId)) {

	                    System.out.println(
	                            "Invalid product details."
	                    );
	                    return;
	                }

	                ps.setString(1, name);
	                ps.setString(2, category);
	                ps.setDouble(3, price);
	                ps.setInt(4, stock);
	                ps.setInt(5, supplierId);

	                ps.addBatch();
	            }

	            // Executes all INSERT operations together
	            ps.executeBatch();

	            System.out.println(
	                    "Products added successfully using batch processing."
	            );

	        } catch (SQLException e) {

	            System.out.println(
	                    "Batch insertion failed: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // SALES OPERATIONS
	    // =========================================================

	    // Records a sale using transaction
	    public static void recordSale(Scanner sc) {

	        System.out.print("Enter product ID: ");
	        int productId = sc.nextInt();

	        System.out.print("Enter quantity sold: ");
	        int quantity = sc.nextInt();
	        sc.nextLine();

	        if (quantity <= 0) {

	            System.out.println("Quantity must be greater than zero.");
	            return;
	        }

	        Connection con = null;

	        try {

	            con = getConnection();

	            // Start transaction
	            con.setAutoCommit(false);

	            // Check product stock and price
	            String productSql =
	                    "SELECT price, stock FROM products " +
	                    "WHERE product_id=? FOR UPDATE";

	            PreparedStatement productPs =
	                    con.prepareStatement(productSql);

	            productPs.setInt(1, productId);

	            ResultSet rs = productPs.executeQuery();

	            if (!rs.next()) {

	                System.out.println("Product ID not found.");
	                con.rollback();
	                return;
	            }

	            double price = rs.getDouble("price");
	            int stock = rs.getInt("stock");

	            if (stock < quantity) {

	                System.out.println("Insufficient stock.");
	                con.rollback();
	                return;
	            }

	            double totalAmount = price * quantity;

	            // Insert sale record
	            String saleSql =
	                    "INSERT INTO sales " +
	                    "(product_id, quantity, sale_price, sale_date) " +
	                    "VALUES (?, ?, ?, CURDATE())";

	            PreparedStatement salePs =
	                    con.prepareStatement(saleSql);

	            salePs.setInt(1, productId);
	            salePs.setInt(2, quantity);
	            salePs.setDouble(3, totalAmount);

	            salePs.executeUpdate();

	            // Reduce stock
	            String updateSql =
	                    "UPDATE products SET stock=stock-? " +
	                    "WHERE product_id=?";

	            PreparedStatement updatePs =
	                    con.prepareStatement(updateSql);

	            updatePs.setInt(1, quantity);
	            updatePs.setInt(2, productId);

	            updatePs.executeUpdate();

	            // Save transaction
	            con.commit();

	            System.out.println("Sale recorded successfully.");
	            System.out.println("Total Sale Amount: ₹" + totalAmount);

	        } catch (SQLException e) {

	            try {

	                if (con != null) {
	                    con.rollback();
	                }

	            } catch (SQLException ex) {

	                System.out.println("Rollback failed.");
	            }

	            System.out.println(
	                    "Sale failed: " + e.getMessage()
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
	    // REPORTS
	    // =========================================================

	    // Displays stock report using aggregate functions
	    public static void stockReport() {

	        String sql =
	                "SELECT COUNT(*) AS total_products, " +
	                "SUM(stock) AS total_stock, " +
	                "SUM(stock * price) AS stock_value " +
	                "FROM products";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {

	                System.out.println(
	                        "\n========== STOCK REPORT =========="
	                );

	                System.out.println(
	                        "Total Products: "
	                        + rs.getInt("total_products")
	                );

	                System.out.println(
	                        "Total Stock: "
	                        + rs.getInt("total_stock")
	                );

	                System.out.println(
	                        "Total Stock Value: ₹"
	                        + rs.getDouble("stock_value")
	                );
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error generating stock report: "
	                    + e.getMessage()
	            );
	        }
	    }

	    // Displays sales report using aggregate functions
	    public static void salesReport() {

	        String sql =
	                "SELECT COUNT(*) AS total_sales, " +
	                "SUM(quantity) AS total_quantity, " +
	                "SUM(sale_price) AS total_revenue " +
	                "FROM sales";

	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {

	                System.out.println(
	                        "\n========== SALES REPORT =========="
	                );

	                System.out.println(
	                        "Total Sales: "
	                        + rs.getInt("total_sales")
	                );

	                System.out.println(
	                        "Total Quantity Sold: "
	                        + rs.getInt("total_quantity")
	                );

	                System.out.println(
	                        "Total Revenue: ₹"
	                        + rs.getDouble("total_revenue")
	                );
	            }

	        } catch (SQLException e) {

	            System.out.println(
	                    "Error generating sales report: "
	                    + e.getMessage()
	            );
	        }
	    }
	

}

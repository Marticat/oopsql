import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0000";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("List of product:");
            displayProducts(connection);

            System.out.print("Enter ID of product, which ones you want to take: ");
            int productId = scanner.nextInt();

            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();

            updateProductQuantity(connection, productId, quantity);

            System.out.println("The table has been updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayProducts(Connection connection) throws SQLException {
        String sql = "SELECT product_id, product_name FROM products";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                System.out.println(productId + ". " + productName);
            }
        }
    }

    private static void updateProductQuantity(Connection connection, int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
        }
    }
}

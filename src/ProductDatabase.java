import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



class ProductDatabase {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/sdp";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "maimir2005";


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }






    public static void updateStock(Product product) {
        try (Connection connection = getConnection()) {
            String query = "UPDATE products SET stock = ? WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, product.getStock());
                preparedStatement.setString(2, product.getName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public static List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    productList.add(new Product(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("stock")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> searchProducts(String productName) {
        List<Product> searchResults = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM products WHERE LOWER(name) LIKE LOWER(?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + productName + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Product product = new Product(
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getDouble("price"),
                                resultSet.getInt("stock")
                        );
                        searchResults.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }



}



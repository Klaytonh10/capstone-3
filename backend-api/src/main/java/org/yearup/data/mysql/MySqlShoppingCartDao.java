package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart cart = new ShoppingCart();
        String sql = """
                select * from shopping_cart
                join products using (product_id)
                where user_id = ?;
                """;
        try (
                Connection connection = super.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(mapRow(resultSet));
                item.setQuantity(resultSet.getInt("quantity"));
                cart.add(item);
            }
            return cart;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ShoppingCart addShoppingCartItem(ShoppingCartItem item, int id) {
        ShoppingCart shoppingCart = getByUserId(id);
        String insertQuery = """
                insert into shopping_cart (user_id, product_id, quantity)
                values (?,?,?);
                """;
        String updateQuery = """
                update shopping_cart
                set quantity = quantity + 1
                where product_id = ? and user_id = ?;
                """;
        if(!shoppingCart.contains(item.getProductId())){
            try (
                    Connection connection = super.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            ) {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, item.getProductId());
                preparedStatement.setInt(3, item.getQuantity());
                int rowsAdded = preparedStatement.executeUpdate();
                if (rowsAdded > 0) {
                    return getByUserId(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (
                    Connection connection = super.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            ) {
                preparedStatement.setInt(1, item.getProductId());
                preparedStatement.setInt(2, id);
                int rowsAdded = preparedStatement.executeUpdate();
                if (rowsAdded > 0) {
                    return getByUserId(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ShoppingCart deleteShoppingCart(int userId) {
        String sql = """
                delete from shopping_cart
                where user_id = ?;
                """;
        try(
                Connection connection = super.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            return getByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //@Override
    //public ShoppingCart removeSpecificCartItem(ShoppingCartItem item, User user, ShoppingCart shoppingCart) {
    //    String sql = """
    //            delete
    //            """;
//
    //    return null;
    //}

    protected static Product mapRow(ResultSet row) throws SQLException {
        int productId = row.getInt("product_id");
        String name = row.getString("name");
        BigDecimal price = row.getBigDecimal("price");
        int categoryId = row.getInt("category_id");
        String description = row.getString("description");
        String subCategory = row.getString("subcategory");
        int stock = row.getInt("stock");
        boolean isFeatured = row.getBoolean("featured");
        String imageUrl = row.getString("image_url");

        return new Product(productId, name, price, categoryId, description, subCategory, stock, isFeatured, imageUrl);
    }
}

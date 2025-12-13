package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.yearup.data.CategoryDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.*;

public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    private ShoppingCart shoppingCart = null;

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
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
            while(resultSet.next()) {
                int id = resultSet.getInt("product_id");
                ShoppingCartItem item = new ShoppingCartItem();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

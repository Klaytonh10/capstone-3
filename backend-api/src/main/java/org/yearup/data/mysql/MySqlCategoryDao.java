package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {

    private static List<Category> categories;

    @Autowired
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        categories = new ArrayList<>();
        String sql = """
                select * from categories;
                """;
        try (
                Connection connection = super.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = mapRow(resultSet);
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category getById(int categoryId) {
        String sql = """
                select * from categories
                where category_id = ?;
                """;
        try (
                Connection connection = super.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return mapRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category create(Category category) {
        String sql = """
                insert into categories (name, description)
                values (?,?);
                """;
        try(
                Connection connection = super.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
            if(rowsInserted != 1) {
                System.err.println("Hey now...you added more than one thing just then");
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int categoryId = resultSet.getInt(1);
            resultSet.close();
            return getById(categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int categoryId, Category category) {
        String sql = """
                update categories
                set name = ?, description = ?
                where category_id = ?;
                """;
        try(
                Connection connection = super.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                )  {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, categoryId);
            int rowsUpdate = preparedStatement.executeUpdate();
            System.out.println("Rows update: " + rowsUpdate);
            if(rowsUpdate != 1) {
                System.err.println("Errrrrm...you just updated more than one category, buddy");
                throw new RuntimeException("Errrrrm...you just updated more than one category, buddy");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // uses "ON DELETE CASCADE"
    @Override
    public int delete(int categoryId) {
        String sql = """
                delete from categories
                where category_id = ?;
                """;
        try(
                Connection connection = super.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, categoryId);
            int rowsDeleted = preparedStatement.executeUpdate();
            if(rowsDeleted != 1) {
                System.err.println("Uh oh...that was more than one row deleted");
                throw new RuntimeException("Uh oh...that was more than one row deleted");
            }
            return rowsDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Category mapRow(ResultSet row) throws SQLException {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category() {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}

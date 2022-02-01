package spring.DAO;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDAO {

    public void addUser(String username) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO users(name, MAX_TOTAL_POINTS) values (?,?)");
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setObject(2, null);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUser(String username) {
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from users where name = ?");
        ) {
            preparedStatement.setString(1, username);

            ResultSet resultSet =  preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}

package spring.DAO;

import org.springframework.stereotype.Component;
import spring.models.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuestionsDAO {
    public Question getQuestion(int id) {
        Question question = null;
        try (Connection connection = JDBC.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from questions where id = ?");
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            question = new Question();
            question.setQuestion(resultSet.getString("question"));
            question.setAnswer(resultSet.getString("answer"));
            question.setPoints(resultSet.getInt("points"));
            question.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;

    }
}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataWorker {

    public void insertData(Connection connection, Student student) {

        String sqlQuery = "INSERT INTO personal_info (student_name, student_surname) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            // Установка значений параметров запроса
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());

            // Выполнение запроса
            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("Добавлено записей: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String SQL_QUERY_NAME_SURNAME = "SELECT * FROM personal_info WHERE student_name = ? AND student_surname = ?";
    private static final String SQL_QUERY_ONLY_NAME = "SELECT * FROM personal_info WHERE student_name = ?";
    private static final String SQL_QUERY_ONLY_SURNAME = "SELECT * FROM personal_info WHERE student_surname = ?";

    public void selectStudentByName(Connection connection, String anyName, String anySurname) {

        if (anyName.isEmpty() && anySurname.isEmpty()) {
            System.out.println("Не передано данных для поиска");
            return;
        }

        String sqlQuery = determineQuery(anyName, anySurname);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            setParameters(preparedStatement, anyName, anySurname);

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Найденные записи: ");

            while (resultSet.next()) {
                String name = resultSet.getString("student_name");
                String surname = resultSet.getString("student_surname");
                System.out.printf("%s %s%n", name, surname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String determineQuery(String anyName, String anySurname) {

        if (anyName.isEmpty()) {
            return SQL_QUERY_ONLY_SURNAME;
        } else if (anySurname.isEmpty()) {
            return SQL_QUERY_ONLY_NAME;
        } else return SQL_QUERY_NAME_SURNAME;
    }

    private void setParameters(PreparedStatement preparedStatement, String anyName, String anySurname) throws SQLException {

        if (anyName.isEmpty()) {
            preparedStatement.setString(1, anySurname);
        } else if (anySurname.isEmpty()) {
            preparedStatement.setString(1, anyName);
        } else {
            preparedStatement.setString(1, anyName);
            preparedStatement.setString(2, anySurname);
        }
    }
}
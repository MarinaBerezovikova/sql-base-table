import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/students";
        String username = "root";
        String password = "inmoodformysql";

//        Student student1 = new Student("Марина", "Березовикова");
//        Student student2 = new Student("Филипп", "Березовиков");
//        Student student3 = new Student("Полина", "Черникова");
//        Student student4 = new Student("Алена", "Веретенова");
        Student student5 = new Student("", "Черников");
        Student student6 = new Student("Марина", "Черникова");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            DataWorker dataWorker = new DataWorker();

            //insert
            dataWorker.insertData(connection, student6);

            //select
            dataWorker.selectStudentByName(connection, student5.getName(), student5.getSurname());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

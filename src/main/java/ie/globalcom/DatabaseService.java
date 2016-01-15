package ie.globalcom;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Класс для подключения к БД, выполнения запроса
 * и вызова соответствуюего Toggle-класса.
 */
public class DatabaseService {

    /**
     * Создание соединения на основе параметров из config.properties
     * @return created SQL connection.
     */
    private static Connection createConnection() {

        Properties properties = new Properties();

        try {
            InputStream propertiesInputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("config.properties");
            properties.load(propertiesInputStream);
        } catch (IOException e) {
            System.err.println("Properties file not found");
            return null;
        } catch (NullPointerException e) {
            System.err.println("Properties could not be loaded");
            return null;
        }

        String DRIVER = properties.getProperty("DRIVER");
        String DBURL = properties.getProperty("DBURL");
        String DBUSER = properties.getProperty("DBUSER");
        String DBPASS = properties.getProperty("DBPASS");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection c;
        try {
            c = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return c;
    }

    /**
     * Класс для обработки полученного ключевого слова - запрос в БД,
     * вызов соответсвующего Toggle класса
     * @param keyword введенное пользователем ключевое слово
     * @return true в случае успешного вызова Toggle класса, соответсвующему keyword
     *      false в остальных случаях
     * @throws SQLException
     */
    public static Boolean getToggleClass(String keyword) throws SQLException {

        Connection connection = createConnection();
        if (connection == null)
            return false;

        String sql = "SELECT c.classname FROM classes as c RIGHT OUTER JOIN keywords as k "
                + "ON c.id = k.binded_class WHERE keyword = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, keyword);
        ResultSet rs = statement.executeQuery();
        String classname = null;

        if (rs.next()) {
            classname = rs.getString("classname");
        }

        statement.close();
        connection.close();

        if (classname != null)
            return setToggle(classname);

        return false;
    }

    /**
     * Создание инстанса класса из описания в текстовой строке
     * @param classname - имя класса, инстанс которого необходимо создать
     * @return true, если инстанс создан, иначе false
     */
    private static Boolean setToggle(String classname){
        try {
            Class toggle = Class.forName(classname);
            toggle.newInstance();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

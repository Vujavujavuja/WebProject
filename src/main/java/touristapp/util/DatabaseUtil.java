package touristapp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseUtil {
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/tourist";

    public static void initDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, "sa", "");
             Statement stmt = connection.createStatement()) {

            InputStream schemaStream = DatabaseUtil.class.getResourceAsStream("/schema.sql");
            if (schemaStream == null) {
                throw new RuntimeException("schema.sql not found in classpath");
            }

            String schema = new BufferedReader(new InputStreamReader(schemaStream))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stmt.execute(schema);
            System.out.println("Database schema initialized successfully.");
        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, "sa", "");
    }
}


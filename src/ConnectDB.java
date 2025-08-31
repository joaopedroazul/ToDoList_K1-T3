import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
    private static Connection conectado;

    public static void initDB() throws SQLException {
        String url = System.getenv("JDBC_DATABASE_URL");
        if (url == null) {
            url = "jdbc:postgresql://localhost:5432/TodoList?user=postgres&password=postgres";
        }
        conectado = DriverManager.getConnection(url);
    }

    // Precisa reconnectar ao DB devido ao Try with resources, que fechar a conexao aṕos uma ação
    public static Connection getConnection() throws SQLException {
        if (conectado == null || conectado.isClosed()) {
            initDB();
        }
        return conectado;
    }
    public static void testConnection() throws SQLException {
        try (Connection conn = conectado) {
            if (conn != null) {
                System.out.println("✅ Conexão bem-sucedida!");
                System.out.println("Database: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("Versão: " + conn.getMetaData().getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro de conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

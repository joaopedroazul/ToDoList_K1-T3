import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {


        try {
            // Inicializa o banco de dados
            ConnectDB.initDB();

            // Inicia o servidor HTTP
            int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "5000"));
            HttpServer server = new HttpServer(port);
            server.start();

            System.out.println("Servidor rodando na porta: " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
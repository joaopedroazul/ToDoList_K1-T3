import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "5000"));
        HttpServer server = new HttpServer(port);
        server.start();
        server.stop();

    }
}
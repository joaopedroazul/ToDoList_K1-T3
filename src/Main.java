import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        //testes do controller de Tarefa
        Tarefa t1 = new Tarefa("caminhar" ,"Fazer exercicos fisicos pela manha",Date.valueOf("2025-01-08"),2,"exercicios","ToDo");
        Tarefa t2 = new Tarefa("correr" ,"pratica de futebol",Date.valueOf(LocalDate.now()),1,"exercicios","Doing");
        ConnectDB.initDB();
        TarefaController.createTableTarefa();
        TarefaController.createTarefa(t1);
        TarefaController.createTarefa(t1);
        TarefaController.listarTarefa();
        TarefaController.listarTarefa(1);
        TarefaController.listarTarefa(2);
        TarefaController.updateTarefa(t2,2);
        TarefaController.listarTarefa();
        TarefaController.removerTarefa(1);


    }
}
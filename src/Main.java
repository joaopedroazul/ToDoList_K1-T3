import java.sql.Date;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Testando a instancia de Objeto
        Tarefa t = new Tarefa("nome", "descricao",Date.valueOf(LocalDate.now()),1 ,"Teste", "ToDo");
        System.out.println(t.getNome());
        System.out.println(t.getDescricao());
        System.out.println(t.getData_end());
        System.out.println(t.getPrioridade());
        System.out.println(t.getCategoria());
        System.out.println(t.getStatus());

    }
}
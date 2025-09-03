import java.io.File;
import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TarefaController {
    public static void createTableTarefa() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tarefas ("+
                "   id SERIAL PRIMARY KEY,"+
                "    nome VARCHAR(100) NOT NULL,"+
                "    descricao VARCHAR(100) ,"+
                "    data_end DATE ,"+
                "    prioridade INTEGER ,"+
                "    categoria VARCHAR(100) ,"+
                "    status VARCHAR(10) not null,"+
                "    isDelete BOOLEAN "+
                ")";

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Tabela 'tarefas' criada/verificada!");
        }
    }



    public static boolean createTarefa(Tarefa t1)throws SQLException {
        String sql = "INSERT INTO tarefas (nome, descricao, data_end,prioridade,categoria,status,isDelete) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, t1.getNome());
            pstmt.setString(2, t1.getDescricao());
            pstmt.setDate(3, t1.getData_end());
            pstmt.setInt(4, t1.getPrioridade());
            pstmt.setString(5, t1.getCategoria());
            pstmt.setString(6, t1.getStatus());
            pstmt.setBoolean(7, false);
            int result = pstmt.executeUpdate();


            System.out.println("Dados inseridos com sucesso!");
            return result > 0;
        }catch (SQLException e){
            System.out.println(System.err);
            e.printStackTrace();
        }
        return false;

    }


    public static List<Tarefa> listarTarefa() throws SQLException {
        String sql = "SELECT * FROM tarefas where isDelete = false" ;
        List<Tarefa> tarefas = new ArrayList<>();

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tarefas.add(new Tarefa(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDate("data_end"),
                        rs.getInt("prioridade"),
                        rs.getString("categoria"),
                        rs.getString("status")
                ));

            }
        }
        return tarefas;
    }



    public static Tarefa listarTarefa(int index) throws SQLException {
        String sql = "SELECT * FROM tarefas where id = "+Integer.toString(index)+" and isDelete = false";

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                return new Tarefa(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDate("data_end"),
                        rs.getInt("prioridade"),
                        rs.getString("categoria"),
                        rs.getString("status")
                );
            }
        }
        return null;
    }


    public static  List<Tarefa> ordemTarefas() throws SQLException {
        List<Tarefa> ordem = new ArrayList<>();
        String sql = "SELECT * FROM tarefas ORDER BY prioridade DESC, id ASC";

        try(Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()) {
                ordem.add(new Tarefa(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDate("data_end"),
                        rs.getInt("prioridade"),
                        rs.getString("categoria"),
                        rs.getString("status")
                ));
            }
        }
        return ordem;
    }

    public static List<Tarefa> retornaLista(String key) throws SQLException {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql =  Character.isDigit(key.charAt(0)) ? "SELECT * FROM tarefas WHERE prioridade = "+key: "SELECT * FROM tarefas WHERE status LIKE '"+key+"'"+" or categoria LIKE '"+key+"'";

        try(Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()) {
                tarefas.add(new Tarefa(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDate("data_end"),
                        rs.getInt("prioridade"),
                        rs.getString("categoria"),
                        rs.getString("status")
                ));
            }
        }

        return tarefas;

    }

    public static void initTarefa() throws SQLException, FileNotFoundException {

        Tarefa tarefa = new Tarefa();
        File faker = new File("./src/TarefasTemplate.txt");
        if (!faker.exists()) {
            System.out.println("Arquivo não encontrado. Procurando em: " + faker.getAbsolutePath());
            return;
        }
        try(Scanner s = new Scanner(faker)){
            while (s.hasNextLine()) {
                String line = s.nextLine();
                System.out.println(line);
                List<String> data = new ArrayList<>();
                int i =0 ;
                while(line.lastIndexOf("|") != -1 || i >0){
                    data.add(line.substring(line.lastIndexOf("|")+1, line.length()));
                    line = line.substring(0, line.lastIndexOf("|"));

                    if(line.lastIndexOf("|") == -1){
                        data.add(line.substring(0, line.length()));
                    }

                }

                tarefa.setNome(data.get(5));
                tarefa.setDescricao(data.get(4));
                tarefa.setData_end(Date.valueOf(data.get(3)));
                tarefa.setPrioridade(Integer.parseInt(data.get(2)));
                tarefa.setCategoria(data.get(1));
                tarefa.setStatus(data.get(0));
                TarefaController.createTarefa(tarefa);
            }
        }
    }



    public static boolean updateTarefa(Tarefa t1,int index) throws SQLException {
        String sql = ""+
                "UPDATE tarefas "+
                "set nome = ?,"+
                "descricao = ? ,"+
                "data_end = ? ,"+
                "prioridade = ?,"+
                "categoria = ? ,"+
                "status = ?"+
                "where id = ?"+
                "";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, t1.getNome());
            pstmt.setString(2, t1.getDescricao());
            pstmt.setDate(3, t1.getData_end());
            pstmt.setInt(4, t1.getPrioridade());
            pstmt.setString(5, t1.getCategoria());
            pstmt.setString(6, t1.getStatus());
            pstmt.setInt(7, index);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("✅ tarefa id: "+ index+ " atualizado com sucesso!");
            } else {
                System.out.println("❌ Nenhuma tarefa encontrado com ID " + index);
            }
            return result > 0;

        }
    }

    //Soft Delete
    public static boolean removerTarefa(int id) throws SQLException {

        String sql =    "UPDATE tarefas "+
                        "set isDelete = true "+
                        "where id = ?"+
                        "";

        if (!tarefaExistente(id)) {
            System.out.println("❌ Tarefa com ID " + id + " não encontrado!");
            return false;
        }

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("Tarefa removido com sucesso!");
                return true;
            }
        }
        return  false;

    }

    public static boolean tarefaExistente(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tarefas WHERE id = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }



}

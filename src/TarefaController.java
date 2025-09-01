import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
             PreparedStatement pstmt = conn.prepareStatement(sql);
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

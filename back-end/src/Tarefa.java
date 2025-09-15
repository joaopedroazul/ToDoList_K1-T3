import java.sql.Date;
import java.time.LocalDate;

public class Tarefa {

    String nome;
    String descricao;
    Date data_end;
    int prioridade;
    String categoria;
    String status;
    Boolean isDelete;

    public Tarefa() {
        this.nome = "";
        this.descricao = "";
        this.data_end = Date.valueOf(LocalDate.now());
        this.prioridade = 0;
        this.categoria = "";
        this.status = "";
        this.isDelete = false;
    }
    public Tarefa(String nome, String descricao, Date data_end, int prioridade, String categoria, String status) {
        this.nome = nome;
        this.descricao = descricao;
        this.data_end = data_end;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = status;
        this.isDelete = false;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData_end() {
        return data_end;
    }

    public void setData_end(Date data_end) {
        this.data_end = data_end;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
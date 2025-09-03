import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Routes implements Runnable {
    private final Socket clientSocket;

    public Routes(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Ler a requisição
            String requestLine = in.readLine();
            if (requestLine == null) return;

            System.out.println("Requisição: " + requestLine);

            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];

            // Ler headers
            String line;
            int contentLength = 0;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                if (line.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(line.substring("Content-Length:".length()).trim());
                }
            }

            // Ler body se existir
            StringBuilder body = new StringBuilder();
            if (contentLength > 0) {
                for (int i = 0; i < contentLength; i++) {
                    body.append((char) in.read());
                }
            }

            // Processar a requisição
            String response = processRequest(method, path, body.toString());

            // Enviar resposta
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: application/json");
            out.println("Access-Control-Allow-Origin: *");
            out.println("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
            out.println("Access-Control-Allow-Headers: Content-Type");
            out.println();
            out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String processRequest(String method, String path, String body) {
        try {
            // Roteamento

            //GET READ ALL
            if (path.equals("/tarefas") && method.equals("GET")) {
                List<Tarefa> tarefas = TarefaController.listarTarefa();
                return toJsonArray(tarefas);
            }

            // Ordem
            if (path.equals("/tarefas/ordem") && method.equals("GET")) {
                List<Tarefa> tarefas = TarefaController.ordemTarefas();
                return toJsonArray(tarefas);
            }

            // POST CREATE
            if (path.equals("/tarefas") && method.equals("POST")) {
                Tarefa tarefas = fromJson(body, Tarefa.class);
                boolean create = TarefaController.createTarefa(tarefas);
                if (create) {
                    return "{Tarefa criada com sucesso!" ;
                }else {
                    return "{Ocorreu um erro am criar uma tarefa!" ;
                }


            }

            Pattern patternList = Pattern.compile("^/tarefas/lista/([^/]+)$");
            Matcher matcher1 = patternList.matcher(path);
            if (matcher1.matches()) {
                String att = matcher1.group(1);
                if (method.equals("GET")) {
                    List<Tarefa> tarefas = TarefaController.retornaLista(att);
                    return toJsonArray(tarefas);
                }
            }



            Pattern pattern = Pattern.compile("^/tarefas/(\\d+)$");
            Matcher matcher = pattern.matcher(path);

            if (matcher.matches()) {
                int id = Integer.parseInt(matcher.group(1));

                if (method.equals("GET")) {
                    Tarefa tarefa = TarefaController.listarTarefa(id);
                    if (tarefa != null) {
                        return toJson(tarefa);
                    } else {
                        return "{\"error\": \"Tarefa not found\"}";
                    }
                }

                if (method.equals("PUT")) {
                    Tarefa tarefa = fromJson(body, Tarefa.class);
                    boolean updated = TarefaController.updateTarefa(tarefa,id);
                    return "{\"success\": " + updated + "}";
                }

                if (method.equals("DELETE")) {
                    boolean deleted = TarefaController.removerTarefa(id);
                    return "{\"success\": " + deleted + "}";
                }
            }

            if (method.equals("OPTIONS")) {
                return ""; // Resposta vazia para preflight requests
            }

            return "{\"error\": \"Endpoint not found\"}";

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    private String toJson(Tarefa tarefa) {
        return String.format("{\"Nome\": \"%s\",\n"+
                              "\"Descricao\": \"%s\",\n"+
                              "\"Data\": \"%s\",\n"+
                              "\"Prioridade\": \"%s\",\n"+
                              "\"Categoria\": \"%s\",\n"+
                              "\"Status\": \"%s\"}",tarefa.getNome(), tarefa.getDescricao(), tarefa.getData_end(),tarefa.getPrioridade(), tarefa.getCategoria(), tarefa.getStatus());
    }

    private String toJsonArray(List<Tarefa> tarefas) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tarefas.size(); i++) {
            sb.append(toJson(tarefas.get(i)));
            if (i < tarefas.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private Tarefa fromJson(String json, Class<Tarefa> c) {
        json = json.replaceAll("[{}\"]", "");
        String[] pairs = json.split(",");
        Tarefa tarefa = new Tarefa();

        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length != 2) continue;

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            try {
                switch (key) {
                    case "nome":
                        tarefa.setNome(value);
                        break;
                    case "descricao":
                        tarefa.setDescricao(value);
                        break;
                    case "data_end":
                        // Converter string para java.sql.Date
                        tarefa.setData_end(java.sql.Date.valueOf(value.replace("T", " ").split(" ")[0]));
                        break;
                    case "prioridade":
                        tarefa.setPrioridade(Integer.parseInt(value));
                        break;
                    case "categoria":
                        tarefa.setCategoria(value);
                        break;
                    case "status":
                        tarefa.setStatus(value);
                        break;
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar campo " + key + ": " + e.getMessage());
            }
        }
        return tarefa;
    }
}


# ToDoList_K1-T3

## Descrição

Projeto desenvolvido durante o ACZG8 na trilha Java para demostrar os conhecimentos adquiridos
ao longo da trilha.

## Pre-requisitos

### PostgreSQL

É necessário tem o PostgreSQL instalado na maquina, crie um banco de dados e coloque a suas
credeciais no arquivo da Classe ConnectDB.


###	Executáveis Shell

Para fazer o build da aplicação, seu sistema operacional deve permitir rodar arquivo .sh

## Execução

Para rodar o servidor back-end do ToDoList copie e cole os seguinte comandos no terminal

    ./build.sh
    java -cp target/classes:lib/postgresql-42.7.4.jar Main

Após colar os comandos no terminal, basta executar e uma instancia do servidor será iniciada localmente
na porta 5000. Caso queria mudar a porta, basta alterar a variavel port no metodo main e executar novamente.

#!/bin/bash

# Criar diretório de destino
mkdir -p target/classes

# Baixar driver PostgreSQL se não existir
if [ ! -f "lib/postgresql-42.7.4.jar" ]; then
    mkdir -p lib
    wget -O lib/postgresql-42.7.4.jar https://jdbc.postgresql.org/download/postgresql-42.7.4.jar
fi

# Compilar código Java
javac -cp "lib/postgresql-42.7.4.jar" -d target/classes src/*.java

echo "Build completo!"
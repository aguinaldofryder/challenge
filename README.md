# Winners API

Este projeto é uma API RESTful para possibilitar a leitura da lista de indicados e vencedores da
categoria Pior Filme do Golden Raspberry Awards, desenvolvida em Java utilizando o framework Quarkus.

Para detalhes da implementação, consulte o arquivo [IMPLEMENTACAO.md](IMPLEMENTACAO.md).

## Requisitos

- Java 17
- Maven 3.8+
- Docker (opcional, para execução em container)

## Rodando os testes
Para rodar os testes, execute o seguinte comando:

```
mvn test
```

> [IMPORTANTE] Os testes unitários foram removidos do código final para atender o que pede no requisito, mas foram utilizados durante o desenvolvimento para orientar a implementação.
O único teste presente é o de integração que verifica se a API está respondendo corretamente.
O assert do teste é feito de forma estática, comparando com o arquivo JSON, localizado em `src/test/resources/expected-winners-response.json`

## Executando Localmente

1. Instale as dependências:

```
mvn clean install
```

2. Execute a aplicação:

```
java -jar ./target/quarkus-app/quarkus-run.jar
```

**Observação:** Para executar a aplicação em uma IDE, como IntelliJ ou Eclipse, 
verifique se você possui o plugin do Lombok instalado e configurado corretamente.

## Configuração

O serviço `ImportCsvService` utiliza um arquivo CSV para importar os dados dos filmes. 
Por padrão, o arquivo esperado é `./data/movielist.csv` na raiz do projeto. 
Caso deseje utilizar outro arquivo, basta passar a configuração na inicialização:

```
-Dcsv.file.path=nomedoarquivo.csv
```

Exemplo:

```
java -Dcsv.file.path=outroarquivo.csv -jar ./target/quarkus-app/quarkus-run.jar
```

A API estará disponível em `http://localhost:8080`.

O endpoint para obter a lista de com maior intervalo entre dois prêmios consecutivos, e o que obteve dois
prêmios mais rápido é:

```
GET /producers/winners
```

## Executando com Docker

1. Faça o build da imagem Docker:

```
docker build -f src/main/docker/Dockerfile.jvm -t winners-api .
```

2. Execute o container:

```
docker run -it --rm -p 8080:8080 -v ./data:/deployments/data winners-api

```

Se desejar utilizar outro arquivo CSV:

```
docker run -it --rm -p 8080:8080 -e CSV_FILE_PATH=./data/movielist_alternativo.csv -v ./data:/deployments/data winners-api

```

## Utilizando Docker Compose

Você pode utilizar o arquivo `docker-compose.yml` para facilitar a execução:

```
docker-compose up --build
```


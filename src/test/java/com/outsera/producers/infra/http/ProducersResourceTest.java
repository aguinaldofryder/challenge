package com.outsera.producers.infra.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ProducersResourceTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * ATENÇÃO: Este teste considera os dados do arquivo `movielist.csv` como fonte de dados.
     * Caso o arquivo seja alterado, os resultados esperados deste teste também devem ser atualizados.
     * O JSON esperado está localizado em `src/test/resources/expected-winners-response.json`.
     */
    @Test
    @DisplayName("Given a POST request to /winners, it should return a 200 OK response")
    void givenPostRequestToWinners_shouldReturn200() throws IOException {
        try (InputStream is = getClass().getClassLoader()
            .getResourceAsStream("expected-winners-response.json")) {

            if (is == null) {
                throw new IOException("Resource 'expected-winners-response.json' not found");
            }

            JsonNode expectedJson = OBJECT_MAPPER.readTree(is);

            Response response = given()
                .get("/producers/winners")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

            JsonNode actualJson = OBJECT_MAPPER.readTree(response.asString());

            assertEquals(expectedJson, actualJson, "O JSON retornado não corresponde ao esperado");
        }
    }
}
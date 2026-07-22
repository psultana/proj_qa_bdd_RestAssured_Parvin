package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiClient {
    public static final String BASE = System.getProperty("api.base", "http://192.168.1.47:8085");

    public static void init() {
        RestAssured.baseURI = BASE;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.defaultParser = io.restassured.parsing.Parser.HTML;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static RequestSpecification request() {
        init();
        return RestAssured.given()
                .contentType(ContentType.URLENC)
                .accept(ContentType.HTML);
    }

    public static io.restassured.response.Response get(String path) {
        init();
        return RestAssured.given().get(path);
    }
}

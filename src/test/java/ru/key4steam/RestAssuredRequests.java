package ru.key4steam;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class RestAssuredRequests {
    Map<String, String> cookies;
    public static final String URL = "http://keys4steam.ru";
    public static final String requestBody = "{\n" +
            "  \"item_id\": \"10\",\n" +
            "  \"quantity\": \"3\"}";

   // JsonObject value = new JsonObject();

    @Test
    public void openLinkTest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL)
                .then()
                .extract().response();
        //сохраняем ключ-значение sessionId
        cookies = response.cookies();
        System.out.println(cookies);
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void addToCartTest() {
        Response response = given()
                .header("Content-type", "application/json","charset=UTF-8", cookies)
                .and()
                .body((requestBody))
                .when()
                .post(URL + "/add_to_cart")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(true,response.jsonPath().getString("Success"));

    }
}

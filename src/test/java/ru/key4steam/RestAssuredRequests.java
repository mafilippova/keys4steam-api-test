package ru.key4steam;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;

import java.util.Map;

import static io.restassured.RestAssured.*;


public class RestAssuredRequests {
    private static Logger logger = LoggerFactory.getLogger(RestAssuredRequests.class);
    public static Map<String, String> session;
    public static final String URL = "http://keys4steam.ru";
    public static final JSONObject jsonObj = new JSONObject()
            .put("item_id", 10)
            .put("quantity", 3);


    @Test
    @Order(1)
    public void openLinkTest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL)
                .then()
                .extract().response();
        session = response.cookies(); //сохраняем ключ-значение sessionId
        logger.info(session.toString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    @Order(2)
    public void addToCartTest() {
        Response response = given()
                .contentType(ContentType.MULTIPART)
                .cookies(session)
                .and()
                .multiPart("item_id", 10)
                .multiPart("quantity", 10)
                .when()
                .post(URL + "/cart/add_to_cart")
                .then()
                .extract().response();
        logger.info(response.asPrettyString());
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(Boolean.parseBoolean(response.jsonPath().getString("Success")));

    }
}

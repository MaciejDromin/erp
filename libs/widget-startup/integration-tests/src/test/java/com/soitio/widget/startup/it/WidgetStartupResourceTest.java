package com.soitio.widgets.startup.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class WidgetStartupResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/widget-startup")
                .then()
                .statusCode(200)
                .body(is("Hello widget-startup"));
    }
}

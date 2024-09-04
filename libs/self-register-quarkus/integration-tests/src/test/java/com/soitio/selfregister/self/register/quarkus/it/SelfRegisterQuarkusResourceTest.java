package com.soitio.selfregister.self.register.quarkus.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SelfRegisterQuarkusResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/self-register-quarkus")
                .then()
                .statusCode(200)
                .body(is("Hello self-register-quarkus"));
    }
}

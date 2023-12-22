package org.wefox.testing;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.wefox.testing.TestUtil.randomInt;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StoreTest {
  @BeforeAll
  public static void initialize() {
    RestAssured.baseURI = "https://petstore3.swagger.io/";
    RestAssured.basePath = "/api/v3";
  }

  /** To get all the pet inventories by status */
  @Test
  public void ensurePetInventoryDetailsAreFoundByStatus() {
    ResponseBody inventoryDetails =
        given().get("/store/inventory").then().statusCode(200).extract().response().getBody();

    int beforeCreationOfOrder = inventoryDetails.jsonPath().get("approved");
    final int id = randomInt();
    createAPetOrder(id);
    inventoryDetails =
        given().get("/store/inventory").then().statusCode(200).extract().response().getBody();
    int afterCreationOfOrder = inventoryDetails.jsonPath().get("approved");
    assertEquals(afterCreationOfOrder, beforeCreationOfOrder + 7);
  }

  private void createAPetOrder(int id) {
    String requestBody =
        """
                                   {

                                   "id": %s,
                                   "petId": 198772%s,
                                   "quantity": 7,
                                   "shipDate": "2023-12-21T11:30:42.238Z",
                                   "status": "approved",
                                   "complete": true

                                 }
                                """;
    final String body = String.format(requestBody, id, id);
    ResponseBody responseAfterCreationOfOrder =
        given()
            .contentType("application/json")
            .body(body)
            .when()
            .post("/store/order")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getBody();
  }

  /**
   * This test case ensures to get purchase order by ID Create an order using the order ID getting
   * the purchase details
   */
  @Test
  public void toGetPurchaseOrderById() {
    final int id = randomInt();
    createAPetOrder(id);
    ResponseBody responseBody =
        given()
            .get("/store/order/{order id}", id)
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getBody();
    assertEquals(id, responseBody.jsonPath().getInt("id"));
  }

  /** Delete Purchase order By ID Create an order Delete the created order with by ID */
  @Test
  public void deletingExistingOrderId() {
    final int id = randomInt();
    createAPetOrder(id);
    ResponseBody responseBody =
        given()
            .delete("/store/order/{orderId}", id)
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getBody();
    assertEquals("", responseBody.print());
  }
}

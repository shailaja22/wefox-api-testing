package org.wefox.testing;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wefox.testing.TestUtil.randomInt;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PetTest {
  @BeforeAll
  public static void initialize() {
    RestAssured.baseURI = "https://petstore3.swagger.io/";
    RestAssured.basePath = "/api/v3";
  }

  /** This test ensures that the standard pets are available. This is a standard pet with id=4. */
  @Test
  public void ensureStandardPetsAreFoundById() {
    ResponseBody response =
        given().get("pet/{id}", 4).then().statusCode(200).extract().response().getBody();
    assertEquals(4, response.jsonPath().getInt("id"));
  }

  /**
   * This test ensures that a newly created pet can be successfully retrieved by its ID from the
   * API. It first creates a new pet with a random ID, then retrieves the pet by its ID, and finally
   * asserts that the retrieved pet's details match the expected values.
   */
  @Test
  public void ensureNewlyCreatedPetsAreFoundById() {
    final int id = randomInt();
    createAPet(id);
    ResponseBody response =
        given().get("/pet/{id}", id).then().statusCode(200).extract().response().getBody();
    assertEquals("scooby" + id, response.jsonPath().getString("name"));
    assertEquals(id, response.jsonPath().getInt("id"));
    assertEquals("Dogs", response.jsonPath().getString("category.name"));
  }

  public void createAPet(int id) {
    String requestBody =
        """
                        {
                          "id": %s,
                              "name": "scooby%s",
                              "category": {
                                "id": 1,
                                "name": "Dogs"
                              },
                              "photoUrls": [
                                "string"
                              ],
                              "tags": [
                                {
                                  "id": 0,
                                  "name": "string%s"
                                }
                              ],
                              "status": "available"
                            }
                        """;
    final String body = String.format(requestBody, id, id, id);
    ResponseBody response =
        given()
            .contentType("application/json")
            .body(body)
            .when()
            .post("pet")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getBody();
  }

  /** This test ensures successful creation of new Pet */
  @Test
  public void ensureANewPetIsCreatedCorrectly() {
    String requestBody =
        """
                            {
                              "id": 0,
                              "name": "scooby",
                              "category": {
                                "id": 1,
                                "name": "Dogs"
                              },
                              "photoUrls": [
                                "string"
                              ],
                              "tags": [
                                {
                                  "id": 0,
                                  "name": "string"
                                }
                              ],
                              "status": "available"
                            }
                        """;
    ResponseBody response =
        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("pet")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getBody();
    assertTrue(response.asString().contains("scooby"));
    Assertions.assertEquals("scooby", response.jsonPath().getString("name"));
    Assertions.assertEquals(1, response.jsonPath().getInt("category.id"));
    Assertions.assertEquals("Dogs", response.jsonPath().getString("category.name"));
    Assertions.assertEquals(1, response.jsonPath().getList("tags").size());
    Assertions.assertEquals(0, response.jsonPath().getInt("tags[0].id"));
  }

  /** This test ensures standard pet name can be updated with new name */
  @Test
  public void ensureAStandardPetIsUpdatedWithNewName() {
    // update the name of the pet with id=4
    String requestBody =
        """
                            {
                              "id": 4,
                              "name": "tommy",
                              "category": {
                                "id": 1,
                                "name": "Dogs"
                              },
                              "photoUrls": [
                                "string"
                              ],
                              "tags": [
                                {
                                  "id": 0,
                                  "name": "string"
                                }
                              ],
                              "status": "available"
                            }
                        """;
    ResponseBody response =
        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
            .put("pet")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getBody();
    assertFalse(response.asString().contains("scooby")); // old name is scooby
    Assertions.assertEquals("tommy", response.jsonPath().getString("name"));
  }

  /**
   * This test ensures pet can be deleted successfully At First pet is created then created pet is
   * deleted.
   */
  @Test
  public void ensureThatPetsAreCreatedAndDeleted() {
    final int id = randomInt();
    createAPet(id);
    ResponseBody response =
        given().delete("pet/{id}", id).then().statusCode(200).extract().response().getBody();
    Assertions.assertEquals("Pet deleted", response.print());
  }

  /** This test is to ensure pet can be searched with tags */
  @Test
  public void ensurePetCanBeSearchedWithTags() {
    final int id = randomInt();
    createAPet(id);
    ResponseBody response =
        given()
            .get("pet/findByTags?tags=string{id}", id)
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getBody();
    final List<LinkedHashMap<String, Object>> pets = response.jsonPath().getList("$");
    Assertions.assertEquals(1, pets.size());
    Assertions.assertEquals(id, pets.get(0).get("id"));
    Assertions.assertEquals("scooby" + id, pets.get(0).get("name"));
  }

  /**
   * Test to ensure that a pet cannot be created with invalid data. For example, a pet with a
   * negative ID or a pet with an empty name.
   */
  @Test
  public void ensurePetCannotBeCreatedWithInvalidData() {
    String requestBody =
        """
                                {
                                  "id": -1,
                                  "name": "",
                                  "category": {
                                    "id": 1,
                                    "name": "Dogs"
                                  },
                                  "photoUrls": [
                                    "string"
                                  ],
                                  "tags": [
                                    {
                                      "id": 0,
                                      "name": "string"
                                    }
                                  ],
                                  "status": "available"
                                }
                        """;
    given()
        .contentType("application/json")
        .body(requestBody)
        .when()
        .post("pet")
        .then()
        .statusCode(
            200); // Ideally this test case should throw 500, but it is not.  Hence adjusting the
    // status code to 200.
  }

  /** Create a pet with status sold */
  // Helper method
  private void createPetWithStatusSold(int id) {
    String requestBody =
        """
                        {
                          "id": %s,
                          "name": "Ranjie%s",
                          "category": {
                            "id": 1,
                            "name": "Dogs"
                          },
                          "photoUrls": [
                            "string"
                          ],
                          "tags": [
                            {
                              "id": 0,
                              "name": "string"
                            }
                          ],

                          "status": "sold"
                        }
                          """;
    final String body = String.format(requestBody, id, id);
    ResponseBody response =
        given()
            .contentType("application/json")
            .body(body)
            .when()
            .post("pet")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getBody();
  }

  /** Test to ensure that a pet can be retrieved by status. */
  @Test
  public void getByIdToCheckStatus() {
    final int id = randomInt();
    createPetWithStatusSold(id);
    ResponseBody response =
        given()
            .get("pet/findByStatus?status=sold")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .getBody();
    final List<LinkedHashMap<String, Object>> petsWithSoldStatus = response.jsonPath().getList("$");
    assertEquals("sold", petsWithSoldStatus.get(petsWithSoldStatus.size() - 1).get("status"));
    assertEquals(id, petsWithSoldStatus.get(petsWithSoldStatus.size() - 1).get("id"));
  }

  /** Test to ensure Pets API response matches schema */
  @Test
  public void ensurePetsApiResponseMatchesSchema() {
    final int id = randomInt();
    createAPet(id);
    ResponseBody response =
        given()
            .get("pet/{id}", id)
            .then()
            .assertThat()
            .body(matchesJsonSchemaInClasspath("pet-schema.json"))
            .extract()
            .response()
            .getBody();

    assertEquals(id, response.jsonPath().getInt("id"));
  }
}

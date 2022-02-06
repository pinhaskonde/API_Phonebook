import com.jayway.restassured.RestAssured;
import dto.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class RestAssuredTests {

    String authorization = "Authorization";
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBpbmhhc0BnbWFpbC5jb20ifQ.fPovUT2VY3QbeyAhoYp9jEYSJaNxMd1hNTYcPj-8O30";

    @BeforeMethod
    public void init() {
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginTestPositive() {
        AuthRequestDTO body = AuthRequestDTO.builder()
                .email("pinhas@gmail.com")
                .password("Pinhas123$").build();

        AuthResponseDTO responseDTO = given().contentType("application/json")
                .body(body)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDTO.class);

    }

    @Test
    public void loginTestNegative() {
        AuthRequestDTO body = AuthRequestDTO.builder()
                .email("pinhas@gmail.com")
                .password("Pinhas123").build();

        ErrorDTO errorDTO = given().contentType("application/json")
                .body(body)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDTO.class);

    }

    @Test
    public void getAllContactsTest() {

        GetAllContactsDTO contactList = given()
                .header(authorization, token)
                .get("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(GetAllContactsDTO.class);

        for (ContactDTO contactDTO : contactList.getContacts()) {
            System.out.println(contactDTO.getId() + " *** " + contactDTO.getEmail());
            System.out.println("-----------------------");
        }
    }

    @Test
    public void addNewContactTest() {
        ContactDTO contactDTO = new ContactDTO().builder()
                .name("Aharon")
                .lastName("Kohchen")
                .email("aharon@gmail.com")
                .phone("333000555")
                .address("Rotshild str.")
                .description("one more description")
                .build();

        int id = given().header(authorization, token)
                .contentType("application/json")
                .body(contactDTO)
                .when()
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println("Contact Id: " + id);

    }

    @Test
    public void deleteContactTest() {

        String status = given().header(authorization, token)
                .when().delete("contact/24041")
                .then().assertThat()
                .statusCode(200)
                .extract().path("status");
        System.out.println("Status: " + status);

    }



}

package rest;

import DTO.PersonDto;
import Exceptions.MissingInputException;
import Exceptions.PersonNotFoundException;
import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import static org.glassfish.grizzly.http.CookiesBuilder.server;
import static org.glassfish.grizzly.http.CookiesBuilder.server;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonEndpointsTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    //Read this line from a settings-file  since used several places
    private static final String TEST_DB = "jdbc:mysql://localhost:3307/person_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    Person p1;
    Person p2;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);

        //NOT Required if you use the version of EMF_Creator.createEntityManagerFactory used above        
        //System.setProperty("IS_TEST", TEST_DB);
        //We are using the database on the virtual Vagrant image, so username password are the same for all dev-databases
        httpServer = startServer();

        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;

        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Some txt", "More text", "45687066");
        p2 = new Person("aaa", "bbb", "456868696");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/persons").then().statusCode(200);
    }

    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/persons/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    @Test
    public void testId() throws Exception {
        given()
                .contentType("application/json")
                .get("/persons/" + p1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("phone", is("45687066"));
    }

    @Test
    public void testAll() throws Exception {
        given()
                .contentType("application/json")
                .get("/persons/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size", is(2))
                .body("[0].firstName", is("Some txt"));
    }

    @Test
    public void testAdd() throws Exception {
        given()
                .contentType("application/json")
                .body("{ \"firstName\" : \"New\", \"lastName\" : \"Person\", \"phone\" : \"45772727\"}")
                .when()
                .post("/persons")
                .then()
                .body("firstName", equalTo("New"))
                .body("lastName", equalTo("Person"))
                .body("phone", equalTo("45772727"))
                .body("id", notNullValue());
    }

    @Test
    public void testEdit() throws Exception {
        EntityManager em = emf.createEntityManager();
        Person person = new Person("Update", "Me", "45345645");
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        given()
                .contentType("application/json")
                .body("{ \"firstName\" : \"UpdatedYes\", \"lastName\" : \"Is updated\", \"phone\" : \"0000000\", \"id\" : \"" + person.getId() + "\"}")
                .when()
                .put("/persons")
                .then()
                .body("firstName", equalTo("UpdatedYes"))
                .body("lastName", equalTo("Is updated"))
                .body("phone", equalTo("0000000"))
                .body("id", notNullValue());
    }

    @Test
    public void testDelete() throws Exception {
        EntityManager em = emf.createEntityManager();
        Person person = new Person("Delete", "Me", "9999999");
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        given()
                .contentType("application/json")
                .put("/persons/" + person.getId())
                .then()
                .assertThat()
                .body("status", equalTo("removed " + person.getFirstName() + " " + person.getLastName()));
    }

    @Test
    public void testGetException() throws PersonNotFoundException {
        given()
                .contentType("application/json")
                .get("/persons/9999")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("message", is("No person with provided id found"));
    }

    @Test
    public void testEditException() throws PersonNotFoundException {
        given()
                .contentType("application/json")
                .body("{ \"firstName\" : \"UpdatedYes\", \"lastName\" : \"Is updated\", \"phone\" : \"0000000\", \"id\" : \"999999\"}")
                .when()
                .put("/persons")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("message", is("Could not edit, provided id does not exist"));
    }

    @Test
    public void testDeleteException() throws PersonNotFoundException {
        given()
                .contentType("application/json")
                .put("/persons/9999")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("message", is("Could not delete, provided id does not exist"));
    }

    @Test
    public void testRunTimeException() throws PersonNotFoundException {
        given()
                .contentType("application/json")
                .get("/persons/0")
                .then()
                .assertThat()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500.getStatusCode())
                .body("message", is("Internal Server Problem. We are sorry for the inconvenience"));
    }

//    @Test
//    public void testAddMissingInputException() throws MissingInputException {
//        given()
//                .contentType("application/json")
//                .body("{ \"firstName\" : \"\", \"lastName\" : \"\", \"phone\" : \"45772727\"}")
//                .when()
//                .post("/persons")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
//                .body("message", is("First Name and/or Last Name is missing"));
//    }
    
//    @Test
//    public void testEditMissingInputException() throws MissingInputException {
//        given()
//                .contentType("application/json")
//                .body("{ \"firstName\" : \"\", \"lastName\" : \"\", \"phone\" : \"0000000\", \"id\" : \"" + p1.getId() + "\"}")
//                .when()
//                .put("/persons")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
//                .body("message", is("First Name and/or Last Name is missing"));
//    }
    
}

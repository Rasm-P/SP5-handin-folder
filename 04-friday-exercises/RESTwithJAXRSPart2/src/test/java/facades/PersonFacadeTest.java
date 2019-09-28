package facades;

import DTO.PersonDto;
import Exceptions.MissingInputException;
import Exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    Person p1;
    Person p2;

    public PersonFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() throws MissingInputException {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/person_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = PersonFacade.getFacadeExample(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() throws MissingInputException {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = PersonFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
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
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testGetPersonByID() throws PersonNotFoundException {
        Person result = facade.getPerson(p1.getId());
        assertTrue(result != null);
        assertEquals(result.getPhone(), "45687066");
    }

    @Test
    public void testGetAllPerson() {
        List<Person> result = facade.getAllPersons();
        assertTrue(result != null);
        assertTrue(!result.isEmpty());
        assertEquals(result.size(), 2);
    }

    @Test
    public void testAddPerson() throws PersonNotFoundException, MissingInputException {
        Person c = facade.addPerson("Test", "Testerson", "45782828");
        Person result = facade.getPerson(c.getId());
        assertEquals(c.getPhone(), result.getPhone());
    }

    @Test
    public void testEditPerson() throws PersonNotFoundException, MissingInputException {
        p2.setFirstName("Kaj");
        PersonDto pDto = new PersonDto(p2);
        Person edit = facade.editPerson(pDto);
        Person result = facade.getPerson(p2.getId());
        assertEquals(edit.getFirstName(), result.getFirstName());
    }

    @Test
    public void testDeletePerson() throws PersonNotFoundException, MissingInputException {
        Person c = facade.addPerson("Test2", "Testerson2", "22222222");
        Person result = facade.deletePerson(c.getId());
        assertEquals(result.getFirstName(), "Test2");
    }

    @Test
    public void testGetException() throws PersonNotFoundException {
        try {
            Person result = facade.getPerson(999999);
            assertTrue(result == null);
        } catch (PersonNotFoundException e) {
            assertEquals(e.getMessage(), "No person with provided id found");
        }
    }

    @Test
    public void testEditException() throws PersonNotFoundException, MissingInputException {
        try {
            Person per = new Person("Some txt", "More text", "45687066");
            per.setId(9999999);
            PersonDto pDto = new PersonDto(per);
            Person edit = facade.editPerson(pDto);
        } catch (PersonNotFoundException e) {
            assertEquals(e.getMessage(), "Could not edit, provided id does not exist");
        }
    }

    @Test
    public void testDeleteException() throws PersonNotFoundException {
        try {
            Person result = facade.deletePerson(999999);
        } catch (PersonNotFoundException e) {
            assertEquals(e.getMessage(), "Could not delete, provided id does not exist");
        }
    }

    @Test
    public void testAddMissingInputException() throws MissingInputException {
        try {
            Person result = facade.addPerson("", "", "");
        } catch (MissingInputException e) {
            assertEquals(e.getMessage(), "First Name and/or Last Name is missing");
        }
    }

    @Test
    public void testEditMissingInputException() throws MissingInputException, PersonNotFoundException {
        try {
            Person person = new Person("", "", "");
            person.setId(1);
            Person result = facade.editPerson(new PersonDto(person));
        } catch (MissingInputException e) {
            assertEquals(e.getMessage(), "First Name and/or Last Name is missing");
        }
    }

}

package facades;

import dto.MovieDto;
import utils.EMF_Creator;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;

    public MovieFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/Movies_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = MovieFacade.getFacadeExample(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
       facade = MovieFacade.getFacadeExample(emf);
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
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(new Movie("The Fellowship of the Ring", "Peter Jackson", "Epic fantasy adventure", 2001, 3.78, 93000000l));
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(new Movie("The Two Towers", "Peter Jackson", "Epic fantasy adventure", 2002, 3.90, 94000000l));
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(new Movie("The Return of the King", "Peter Jackson", "Epic fantasy adventure", 2003, 3.82, 94000000l));
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
    /**
     * Test of getCustomerByID method, of class BankCustomerFacade.
     */
    @Test
    public void testGetMovieByID() {
        MovieDto result = facade.getMovieByID(1l);
        assertTrue(result != null);
        assertEquals(result.getName(), "The Fellowship of the Ring");
    }

    /**
     * Test of getCustomerByName method, of class BankCustomerFacade.
     */
    @Test
    public void testGetMovieByName() {
        List<MovieDto> result = facade.getMovieByName("The Two Towers");
        assertTrue(result != null);
        assertEquals(result.get(0).getName(), "The Two Towers");
    }

    /**
     * Test of addCustomer method, of class BankCustomerFacade.
     */
    @Test
    public void testAddMovie() {
        Movie result = new Movie("Test", "Testerson", "12ddfsdfsfs345", 34600230, 125.3324, 876543345678l);
        Movie c = facade.addMovie(result);
        List<MovieDto> movieList = facade.getMovieByName(c.getName());
        assertEquals(c.getName(), movieList.get(0).getName());
    }

    /**
     * Test of getAllBankCustomers method, of class BankCustomerFacade.
     */
    @Test
    public void testGetAllMovies() {
        List<Movie> result = facade.getAllMovies();
        assertTrue(result != null);
        assertTrue(!result.isEmpty());
        assertEquals(result.size(),3);
    }

}

package facades;

import dto.MovieDto;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public MovieDto getMovieByID(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Movie movie = em.find(Movie.class, id);
            MovieDto movieDto = new MovieDto(movie);
            return movieDto;
        } finally {
            em.close();
        }
    }

    public List<MovieDto> getMovieByName(String name) {
        EntityManager em = getEntityManager();
        List<MovieDto> movieDto = new ArrayList();
        try {
            TypedQuery<Movie> query
                    = em.createQuery("Select m from Movie m WHERE m.name LIKE ?1", Movie.class).setParameter(1, name);
            List<Movie> movies = query.getResultList();
            for (Movie mov : movies) {
                movieDto.add(new MovieDto(mov));
            }
            return movieDto;
        } finally {
            em.close();
        }
    }

    public Movie addMovie(Movie movie) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        } finally {
            em.close();
        }
    }

    public List<Movie> getAllMovies() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Movie> query
                    = em.createQuery("Select m from Movie m", Movie.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public long getCount() {
        EntityManager em = getEntityManager();
        try{
            long count = (long)em.createQuery("SELECT COUNT(m) FROM Movie m").getSingleResult();
            return count;
        }finally{  
            em.close();
        }
    }

}

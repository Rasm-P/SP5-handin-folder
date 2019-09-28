package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MovieDto;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movies")
public class MovieEndpoints {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/Movies",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final MovieFacade FACADE = MovieFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMovieByID(@PathParam("id") Long id) {
        MovieDto dto = FACADE.getMovieByID(id);
        return GSON.toJson(dto);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllMoviesList() {
        List<Movie> list = FACADE.getAllMovies();
        return GSON.toJson(list);
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMoviesByName(@PathParam("name") String name) {
        List<MovieDto> movDto = FACADE.getMovieByName(name);
        return GSON.toJson(movDto);
    }
        
    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieCount() {
        long count = FACADE.getCount();
        return "{\"count\":"+count+"}";
    }
}

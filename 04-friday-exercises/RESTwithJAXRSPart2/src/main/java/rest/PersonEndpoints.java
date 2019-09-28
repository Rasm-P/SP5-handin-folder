package rest;

import DTO.PersonDto;
import Exceptions.MissingInputException;
import Exceptions.PersonNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("persons")
public class PersonEndpoints {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/person",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonByID(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDto dto = new PersonDto(FACADE.getPerson(id));
        return GSON.toJson(dto);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPersonList() {
        List<PersonDto> personsDto = new ArrayList();
        List<Person> list = FACADE.getAllPersons();
        for (Person per : list) {
            personsDto.add(new PersonDto(per));
        }
        return GSON.toJson(personsDto);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(String per) throws MissingInputException {
        PersonDto person;
        person = GSON.fromJson(per, PersonDto.class);
        Person p = FACADE.addPerson(person.getFirstName(), person.getLastName(), person.getPhone());
        PersonDto newPerson = new PersonDto(p);
        return Response.ok(newPerson).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPerson(String per) throws PersonNotFoundException, MissingInputException {
        PersonDto person;
        person = GSON.fromJson(per, PersonDto.class);
        Person p = FACADE.editPerson(person);
        PersonDto newPerson = new PersonDto(p);
        return Response.ok(newPerson).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        Person p = FACADE.deletePerson(id);
        return "{\"status\": \"removed " + p.getFirstName() + " " + p.getLastName() + "\"}";
    }

}

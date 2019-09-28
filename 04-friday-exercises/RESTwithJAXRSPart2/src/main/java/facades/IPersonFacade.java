/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.PersonDto;
import Exceptions.MissingInputException;
import Exceptions.PersonNotFoundException;
import entities.Person;
import java.util.List;

/**
 *
 * @author Rasmus2
 */
public interface IPersonFacade {

    public Person addPerson(String fName, String lName, String phone) throws MissingInputException ; 

    public Person deletePerson(int id) throws PersonNotFoundException;

    public Person getPerson(int id) throws PersonNotFoundException;

    public List<Person> getAllPersons();

    public Person editPerson(PersonDto p) throws PersonNotFoundException, MissingInputException;

}

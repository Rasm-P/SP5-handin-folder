package facades;

import DTO.PersonDto;
import Exceptions.MissingInputException;
import Exceptions.PersonNotFoundException;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
//    private PersonFacade() throws MissingInputException {
//        populate();
//    }
    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    @Override
    public Person addPerson(String fName, String lName, String phone) throws MissingInputException {
        EntityManager em = emf.createEntityManager();
        Person person = new Person(fName, lName, phone);
        if (person.getFirstName().equals("") || person.getLastName().equals("")) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        }
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }

    @Override
    public Person deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("Could not delete, provided id does not exist");
            }
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }

    @Override
    public Person getPerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        if (id == 0) {
            int fail = 0 / 0;
        }
        try {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("No person with provided id found");
            }
            return person;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query
                    = em.createQuery("Select p from Person p", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Person editPerson(PersonDto p) throws PersonNotFoundException, MissingInputException {
        EntityManager em = emf.createEntityManager();
        try {
            if (p.getFirstName().equals("") || p.getLastName().equals("")) {
                throw new MissingInputException("First Name and/or Last Name is missing");
            }
            Person person = em.find(Person.class, p.getId());
            if (person == null) {
                throw new PersonNotFoundException("Could not edit, provided id does not exist");
            }
            em.getTransaction().begin();
            person.setFirstName(p.getFirstName());
            person.setLastName(p.getLastName());
            person.setPhone(p.getPhone());
            em.merge(person);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }

//    private void populate() throws MissingInputException {
//        List<Person> p = getAllPersons();
//        if (p == null || p.isEmpty()) {
//            addPerson("Some txt", "More text", "45687066");
//            addPerson("aaa", "bbb", "456868696");
//        }
//    }
}

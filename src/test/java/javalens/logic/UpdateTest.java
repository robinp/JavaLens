package javalens.logic;

import javalens.Lens;
import javalens.model.Address;
import javalens.model.Person;
import org.junit.Assert;
import org.junit.Test;

public class UpdateTest {

    private final Person person = new Person("Jane", 22, new Address("X1234", "Some str."));

    @Test
    public void testUpdateWithoutLens() {
        // WHEN updating the street of the person
        final Person updatedPerson = person.setAddress(person.address.setStreet("Other str."));
        // THEN the street changed
        Assert.assertEquals("Other str.", updatedPerson.address.street);
        // AND other data didn't change
        Assert.assertEquals("X1234", updatedPerson.address.zipCode);
    }

    @Test
    public void testUpdateUsingLens() {
        // GIVEN a lens we could declare only once statically somewhere
        final Lens<Person, String> personStreetLens = Person.ADDRESS_LENS.andThen(Address.STREET_LENS);
        // WHEN updating the street of the person
        final Person updatedPerson = personStreetLens.set(person, "Other str.");
        // THEN the street changed
        Assert.assertEquals("Other str.", updatedPerson.address.street);
        // AND other data didn't change
        Assert.assertEquals("X1234", updatedPerson.address.zipCode);
    }
}

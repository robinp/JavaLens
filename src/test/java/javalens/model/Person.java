package javalens.model;

import javalens.Lens;
import lombok.Data;

@Data
public class Person {
    private final String name;
    private final int age;
    private final Address address;

    // Copiers

    public Person setName(String name) {
        return new Person(name, age, address);
    }

    public Person setAge(int age) {
        return new Person(name, age, address);
    }

    public Person setAddress(Address address) {
        return new Person(name, age, address);
    }

    // Lens (only one sample, others homework)

    public static Lens<Person, Address> ADDRESS_LENS = new Lens<Person, Address>() {

        public Address get(Person person) {
            return person.address;
        }

        public Person set(Person person, Address address) {
            return person.setAddress(address);
        }
    };
}

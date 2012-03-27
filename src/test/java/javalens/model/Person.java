package javalens.model;

import javalens.Lens;

public class Person {
    public final String name;
    public final int age;
    public final Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

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

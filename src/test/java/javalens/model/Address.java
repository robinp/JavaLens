package javalens.model;

import javalens.Lens;

public class Address {
    public final String zipCode;
    public final String street;

    public Address(String zipCode, String street) {
        this.zipCode = zipCode;
        this.street = street;
    }

    // Copiers

    public Address setZipCode(String zipCode) {
        return new Address(zipCode, street);
    }

    public Address setStreet(String street) {
        return new Address(zipCode, street);
    }

    // Lens (only one sample, others homework)

    public static Lens<Address, String> STREET_LENS = new Lens<Address, String>() {

        public String get(Address address) {
            return address.street;
        }

        public Address set(Address address, String street) {
            return address.setStreet(street);
        }
    };
}

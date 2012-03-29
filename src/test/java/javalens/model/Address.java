package javalens.model;

import javalens.Lens;
import lombok.Data;

@Data
public class Address {
    private final String zipCode;
    private final String street;

    // Copiers

    public Address setZipCode(String zipCode) {
        return new Address(zipCode, street);
    }

    public Address setStreet(String street) {
        return new Address(zipCode, street);
    }

    // Lenses

    public static Lens<Address, String> STREET_LENS = new Lens<Address, String>() {

        public String get(Address address) {
            return address.street;
        }

        public Address set(Address address, String street) {
            return address.setStreet(street);
        }
    };

    public static Lens<Address, String> ZIP_CODE_LENS = new Lens<Address, String>() {
        @Override
        public String get(Address address) {
            return address.zipCode;
        }

        @Override
        public Address set(Address address, String zipCode) {
            return address.setZipCode(zipCode);
        }
    };
}

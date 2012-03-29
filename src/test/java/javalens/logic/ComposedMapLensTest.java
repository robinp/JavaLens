package javalens.logic;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import javalens.Lens;
import javalens.MapLens;
import javalens.OptionalLens;
import javalens.model.Address;
import javalens.model.Person;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Map;

public class ComposedMapLensTest {

    public static final String APPLE = "apple";
    public static final String PEAR = "pear";

    /**
     * Mapping from nursery school sign to child data.
     */
    final ImmutableMap<String, Person> mapWithPear = ImmutableMap.of(
            PEAR, new Person("Joe", 5, new Address("1234", "Some str.")));

    /**
     * Lens from the nursery school sign (if present) to the address.
     */
    private Lens<Map<String, Person>, Optional<Address>> signToAddressLens(String sign) {
        return MapLens.keyLens(sign, Person.class).andThen(OptionalLens.of(Person.ADDRESS_LENS));
    }

    @Test
    public void testNestedReadWhenKeyNotPresent() {
        Assert.assertEquals(Optional.absent(), signToAddressLens(APPLE).get(mapWithPear));
    }

    @Test
    public void testNestedReadWhenKeyPresent() {
        Assert.assertEquals(Optional.of(new Address("1234", "Some str.")), signToAddressLens(PEAR).get(mapWithPear));
    }

    @Test
    public void testUpdatingNestedData() {
        // GIVEN
        final ImmutableMap<String, Person> expectedMap = ImmutableMap.of(
                PEAR, new Person("Joe", 5, new Address("1234", "Other str.")));
        // WHEN
        final ImmutableMap<String, Person> updatedMap = ImmutableMap.copyOf(
                signToAddressLens(PEAR).set(mapWithPear, Optional.of(new Address("1234", "Other str."))));
        // THEN
        Assert.assertEquals(expectedMap, updatedMap);
    }

    @Test
    public void testDeletingNestedDataDeletesKey() {
        Assert.assertEquals(ImmutableMap.of(), signToAddressLens(PEAR).set(mapWithPear, Optional.<Address>absent()));
    }

    @Test
    public void testDeeperLens() {
        // GIVEN a pretty deep lens
        final Lens<Map<String, Person>, Optional<String>> zipCodeOfPearLens =
                signToAddressLens(PEAR).andThen(OptionalLens.of(Address.ZIP_CODE_LENS));
        // THEN reading the deep field succeeds
        Assert.assertEquals(Optional.of("1234"), zipCodeOfPearLens.get(mapWithPear));
        // AND WHEN updating the deep field
        final Map<String, Person> updatedMap = zipCodeOfPearLens.set(mapWithPear, Optional.of("ABCD"));
        final Map<String, Person> expectedMap = ImmutableMap.of(
                PEAR, new Person("Joe", 5, new Address("ABCD", "Some str.")));
        // THEN it succeeds
        Assert.assertEquals(expectedMap, ImmutableMap.copyOf(updatedMap));
    }
}

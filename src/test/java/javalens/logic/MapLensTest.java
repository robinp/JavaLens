package javalens.logic;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import javalens.Lens;
import javalens.MapLens;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Map;

public class MapLensTest {

    public static final String APPLE = "apple";
    public static final String PEAR = "pear";
    private static final String ORANGE = "orange";

    private final Lens<Map<String, Integer>, Optional<Integer>> appleKeyLens = MapLens.keyLens(APPLE, Integer.class);
    private final Lens<Map<String, Integer>, Optional<Integer>> orangeKeyLens = MapLens.keyLens(ORANGE, Integer.class);

    private final ImmutableMap<String, Integer> testMap = ImmutableMap.of(
            APPLE, 1,
            PEAR, 2);

    @Test
    public void testGetKeyWhenHasValue() {
        Assert.assertEquals(Optional.of(1), appleKeyLens.get(testMap));
    }

    @Test
    public void testGetKeyWhenNoValue() {
        Assert.assertEquals(Optional.absent(), orangeKeyLens.get(testMap));
    }

    @Test
    public void testUpdateKey() {
        // WHEN
        final Map<String, Integer> newMap = appleKeyLens.set(testMap, Optional.of(3));
        // THEN
        Assert.assertEquals(ImmutableMap.of(APPLE, 3, PEAR, 2), ImmutableMap.copyOf(newMap));
    }

    @Test
    public void testRemoveKey() {
        // WHEN
        final Map<String, Integer> smallerMap = appleKeyLens.set(testMap, Optional.<Integer>absent());
        // THEN
        Assert.assertEquals(ImmutableMap.of(PEAR, 2), ImmutableMap.copyOf(smallerMap));
    }

}

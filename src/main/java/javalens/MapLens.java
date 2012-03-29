package javalens;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public class MapLens {

    public static <K, V> Lens<Map<K, V>, Optional<V>> keyLens(final K key, Class<V> cls) {
        return keyLens(key);
    }

    public static <K, V> Lens<Map<K, V>, Optional<V>> keyLens(final K key) {
        return new Lens<Map<K, V>, Optional<V>>() {
            @Override
            public Optional<V> get(Map<K, V> map) {
                if (map.containsKey(key))
                    return Optional.of(map.get(key));
                else
                    return Optional.absent();
            }

            @Override
            public Map<K, V> set(Map<K, V> map, Optional<V> v) {
                return copyMapWith(map, key, v);
            }
        };
    }

    private static <K, V> Map<K, V> copyMapWith(Map<K, V> map, K key, Optional<V> value) {
        final Map<K, V> copied = Maps.newHashMap(map);
        if (!value.isPresent())
            copied.remove(key);
        else
            copied.put(key, value.get());
        return Collections.unmodifiableMap(copied);
    }
}

package javalens;

import com.google.common.base.Function;

/**
 * Lens zooming in to B in A
 *
 * @param <A> outer type
 * @param <B> inner type
 */
abstract public class Lens<A, B> {
    /**
     * Read the sub-structure.
     */
    abstract public B get(A a);

    /**
     * Return a copy of A with the substructure updated.
     */
    abstract public A set(A a, B b);

    /**
     * Return a copy of A with the substructure modified my the function.
     */
    public A modify(A a, Function<B, B> mod) {
        return set(a, mod.apply(get(a)));
    }

    /**
     * Compose two lens to get a Lens from A to C.
     */
    public <C> Lens<A, C> andThen(final Lens<B, C> lensBC) {
        return new Lens<A, C>() {
            @Override
            public C get(final A a) {
                return lensBC.get(Lens.this.get(a));
            }

            @Override
            public A set(final A a, final C c) {
                // longer version
                //return Lens.this.set(a, lensBC.set(Lens.this.get(a), c));

                // would nicer using modify
                // (if Java actually a had shorthand for anonymous functions)
                return Lens.this.modify(a, new Function<B, B>() {
                    public B apply(B input) {
                        return lensBC.set(input, c);
                    }
                });
            }
        };
    }

}

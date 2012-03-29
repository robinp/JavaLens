package javalens;

import com.google.common.base.Optional;

public class OptionalLens {

    public static <A, B> Lens<Optional<A>, Optional<B>> of(final Lens<A, B> lens) {

        return new Lens<Optional<A>, Optional<B>>() {
            @Override
            public Optional<B> get(Optional<A> aOptional) {
                if (aOptional.isPresent())
                    return Optional.of(lens.get(aOptional.get()));
                else
                    return Optional.absent();
            }

            @Override
            public Optional<A> set(Optional<A> aOptional, Optional<B> bOptional) {
                if (!aOptional.isPresent() || !bOptional.isPresent())
                    return Optional.absent();

                return Optional.of(lens.set(aOptional.get(), bOptional.get()));
            }
        };
    }
}

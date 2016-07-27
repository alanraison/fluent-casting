package io.github.alanraison.fluentcasting;

import java.util.function.Consumer;
import java.util.function.Function;

public class CastSubject<T> {
    private T t;
    private CastSubject(T t) {
        this.t = t;
    }
    public static <T> CastSubject<T> with(T t) {
        return new CastSubject<>(t);
    }
    private <U extends T> U castTo(Class<U> u) {
        return u.cast(this.t);
    }
    public <U extends T> CastedAction<U> asA(Class<U> u) {
        return new CastedAction<>(castTo(u));
    }
    public <U extends T> void asA(Class<U> u, Consumer<U> func) {
        func.accept(castTo(u));
    }
    public <U extends T, R> R asA(Class<U> u, Function<U,R> func) {
        return func.apply(castTo(u));
    }

    private static class CastedAction<T> {
        private T t;
        private CastedAction(T t) {
            this.t = t;
        }
        public void consume(Consumer<T> func) {
            func.accept(this.t);
        }

        public <R> R map(Function<T,R> func) {
            return func.apply(this.t);
        }
    }
}

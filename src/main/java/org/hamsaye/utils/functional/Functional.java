package org.hamsaye.utils.functional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Functional {

    private Map<String, Functionality> results;

    public Functional() {
        this.results = new HashMap<>();
    }

    public <Input, Result extends Functionality> Functional apply(String key, Function<Input, Result> function, Input input) {
        Result result = function.apply(input);
        results.put(key, result);
        return this;
    }

    public <Input, Result extends Functionality> Functional apply(Function<Input, Result> function, Input input) {
        function.apply(input);
        return this;
    }

    public Map<String, Functionality> getResults() {
        return results;
    }
}

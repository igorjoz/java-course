package com.igorjoz.lab2;

import java.util.ArrayList;
import java.util.List;

public class ResultList {
    private final List<Boolean> results = new ArrayList<>();

    public synchronized void addResult(Boolean isPrime) {
        results.add(isPrime);
    }

    public synchronized List<Boolean> getResults() {
        return new ArrayList<>(results);
    }
}

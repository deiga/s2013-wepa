package wad.parlezvousfrancais.service;

import org.springframework.stereotype.Service;

@Service
public class InMemoryCounter implements CounterService {

    private int counter;

    @Override
    public void increment() {
        counter++;
    }

    @Override
    public int value() {
        return counter;
    }
}
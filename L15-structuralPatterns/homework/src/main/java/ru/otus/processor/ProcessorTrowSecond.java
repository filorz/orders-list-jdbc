package ru.otus.processor;

import java.time.LocalTime;

public class ProcessorTrowSecond {

    public void process() throws Exception {
        if (LocalTime.now().getSecond() % 2 == 0) {
            throw new Exception();
        }
    }
}

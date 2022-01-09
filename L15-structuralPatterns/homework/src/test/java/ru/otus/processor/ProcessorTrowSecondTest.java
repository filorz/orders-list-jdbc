package ru.otus.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ProcessorTrowSecondTest {

    private ProcessorTrowSecond processorTrowSecond;

    @Test
    void process() {
        processorTrowSecond = mock(ProcessorTrowSecond.class);
        assertThrows(Exception.class, () -> processorTrowSecond.process());

//        var iter = 0;
//
//        while (iter < 5) {
//            if (LocalTime.now().getSecond() % 2 == 0) {
//
//            } else {
//                Thread.sleep(2000);
//            }
//            iter++;
//        }
    }

}
package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorReversFields implements Processor {

    @Override
    public Message process(Message message) {
        var newFieldValue = message.getField11();
        message.toBuilder().field11(message.getField12());
        message.toBuilder().field12(newFieldValue);

        return message;
    }
}

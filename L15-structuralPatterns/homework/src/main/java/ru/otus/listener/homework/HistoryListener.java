package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    private List<Message> messageList = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        this.messageList.add(msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return this.messageList.stream()
                .filter(o -> o.getId() == id)
                .findAny();
    }
}

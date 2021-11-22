package ru.core;

import org.junit.jupiter.api.Test;
import ru.core.dao.OrderingDao;
import ru.core.dao.OrderingItemDao;
import ru.core.dao.impl.OrderingDaoImpl;
import ru.core.dao.impl.OrderingItemDaoImpl;
import ru.core.models.Ordering;
import ru.core.models.OrderingItem;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    public void main() {
        assertTrue(Boolean.TRUE);
    }
}
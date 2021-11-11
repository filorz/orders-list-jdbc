package core.dao;

import core.models.Ordering;

public interface BaseDao<T> {

    int create(String userName);

    boolean update(T ordering);

    T get(String id);

    String delete();

    void deleteAll();

}
